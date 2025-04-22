package dev.dini.customerservice.repository;

import dev.dini.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM Customer c WHERE c.email = :identifier OR c.phoneNumber = :identifier OR c.idNumber = :identifier")
    Optional<Customer> findByIdentifier(@Param("identifier") String identifier);


}
