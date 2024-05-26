package com.nsh.customerservice.dao;

import com.nsh.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepo extends JpaRepository<Customer,UUID> {
    Optional<Customer> findByEmail(String mail);
}
