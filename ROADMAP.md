# Feature Roadmap — EFT Payment System

Suggested features, grounded in the current codebase. Ordered by priority. Each item notes *why* (the gap today) and *where* it lands.

---

## P0 — Finish the core domain (the flow is incomplete without these)

### 1. Transaction history query API
**Gap:** The README advertises "query transaction history for a customer," but `transaction-service` has **no REST controller** — it's a Kafka consumer only. The data is recorded and unreachable.
**Add:** `TransactionController` with `GET /transactions?customerId=` and `GET /transactions/{id}`, backed by the existing `TransactionRepository` (add a `findByCustomerId`). Return paginated results.

### 2. Actual "blocked amount" fund hold
**Gap:** The project is modelled on the "blocked amount" pattern, but there is **no balance, account, hold, or ledger** anywhere — a payment just flips status enums. Nothing is ever reserved or debited.
**Add:** An account/balance concept (either extend `customer-service` or a new `account-service`): place a *hold* when a payment goes `PENDING_2FA`, *capture* it on `COMPLETED`, and *release* it on `FAILED`/`REJECTED`.

### 3. Payment status endpoint on the gateway
**Gap:** The third party gets a `PaymentResponseDTO` at initiation but has no way to poll the outcome. `consent-service` exposes `/consents/status`, but the gateway (the public entry point) does not.
**Add:** `GET /payments/{id}` on `gateway-service` returning current `PaymentStatus`.

### 4. Compensation / rollback on rejection
**Gap:** On 2FA rejection the gateway sets `PaymentStatus.FAILED`, but nothing reverses upstream work (no fund release, no consent cleanup). No saga compensation exists.
**Add:** Wire rejection to release any hold (depends on #2) and emit a `PaymentFailedEvent` so downstream services can react.

---

## P1 — Reliability & correctness

### 5. Idempotent Kafka consumers
**Gap:** No consumer is idempotent. A redelivered `payment-initiated`, `twofa-*`, or `payment-completed-event` will create duplicate consents / transactions / holds.
**Add:** Dedup by event/message id (processed-event table or a natural unique key such as `paymentRequestId` with an upsert).

### 6. Dead-letter queues + retry
**Gap:** Consumers use `ErrorHandlingDeserializer` but there's no DLQ or retry policy — a poison message is silently dropped.
**Add:** Spring Kafka `DefaultErrorHandler` with backoff + `DeadLetterPublishingRecoverer` per consumer.

### 7. Resilience on Feign calls
**Gap:** Synchronous hops (gateway→customer, consent→2FA, 2FA→consent) have no timeout, retry, or circuit breaker. One slow service stalls the chain. `ConsentServiceImpl` even swallows 2FA failures with a bare `catch (Exception)` + `System.err`.
**Add:** Resilience4j circuit breakers / timeouts on the Feign clients; replace swallowed exceptions with proper handling.

### 8. Schema migrations (Flyway/Liquibase)
**Gap:** Most services use `ddl-auto: create` (data wiped every restart); `customer-service` uses `validate` but its `customer-data.sql` is not auto-loaded, so it fails against an empty DB. No migration tool is present.
**Add:** Flyway with versioned migrations per service; drop `ddl-auto`. Load the customer seed as a migration.

### 9. 2FA / consent expiry
**Gap:** `TwoFaRequest` and `Consent` are created `PENDING` with no timeout — an unanswered challenge stays open forever.
**Add:** An `expiresAt` + a scheduled sweep (or Kafka-delayed check) that auto-rejects stale requests.

---

## P2 — Security

### 10. Authentication & authorization
**Gap:** Every endpoint is open. No API keys, JWT, or client identity for the third-party callers.
**Add:** Spring Security — API-key/JWT auth for external callers on `gateway-service`, and service-to-service auth (mTLS or signed tokens) for internal Feign hops.

### 11. Real OTP / push delivery
**Gap:** `NotificationClient.send2FaNotification` just `System.out.printf`s. There is no real OTP generation, delivery, or verification.
**Add:** OTP generation + a pluggable notification provider (SMS/push/email), with verification on approve.

### 12. PII protection
**Gap:** `customer-data.sql` holds RSA ID numbers, phones, addresses in plaintext, and services log full DTOs (`log.info("...: {}", customer)`).
**Add:** Encrypt/tokenize sensitive columns; scrub PII from logs.

---

## P3 — Observability & operations

### 13. Distributed tracing + correlation IDs
**Gap:** A payment spans 5 services over HTTP and Kafka with no way to follow one request end to end.
**Add:** Micrometer Tracing + OpenTelemetry; propagate a correlation id through Feign headers and Kafka headers.

### 14. Health checks & metrics
**Gap:** Only `gateway-service` has `spring-boot-starter-actuator`; the others don't. No metrics or readiness probes.
**Add:** Actuator everywhere + Prometheus metrics; expose Kafka consumer lag.

### 15. Dockerize the services + one-command up
**Gap:** `docker-compose.yaml` runs only Kafka/Zookeeper/Postgres. The five services must be started by hand, each needing `JAVA_HOME` and the right port.
**Add:** A Dockerfile per service (or Spring Boot buildpacks) and compose entries so `docker-compose up` runs the whole system.

### 16. Centralized / externalized config
**Gap:** URLs and ports are hardcoded per service (`localhost:8082`, etc.); a stray "Eureka" comment references discovery that doesn't exist.
**Add:** Spring Cloud Config or env-var-driven config; optionally real service discovery + an edge gateway (Spring Cloud Gateway).

---

## P4 — Quality

### 17. Real tests
**Gap:** All five `*ApplicationTests` are empty context-load stubs. No unit, slice, or integration tests cover the payment flow.
**Add:** Unit tests for the service layer; `@DataJpaTest` for repositories; Testcontainers (Kafka + Postgres) for the end-to-end flow; contract tests for the event schemas in `common-events`.

### 18. OpenAPI polish
**Gap:** springdoc is present but endpoints lack documented request/response schemas and examples.
**Add:** Annotate controllers/DTOs; publish the aggregated API docs.

---

### Quick wins (small, high value)
- Transaction history endpoint (#1) — data already exists.
- Gateway payment-status endpoint (#3).
- Actuator on all services (#14).
- Idempotency key on the `payment-initiated` consumer (#5).
