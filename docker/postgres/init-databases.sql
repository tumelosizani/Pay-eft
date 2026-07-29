-- Creates one database per microservice on first container startup.
-- Runs automatically because it is mounted into /docker-entrypoint-initdb.d/.
CREATE DATABASE "gateway-service";
CREATE DATABASE "customer-service";
CREATE DATABASE "consent-service";
CREATE DATABASE "twofa-service";
CREATE DATABASE "transaction-service";
