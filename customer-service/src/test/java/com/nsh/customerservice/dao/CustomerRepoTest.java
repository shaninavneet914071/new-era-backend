package com.nsh.customerservice.dao;

import com.nsh.customerservice.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRepoTest {
    @InjectMocks
    private Customer customer = new Customer();
    @Mock
    private CustomerRepo customerRepo;


    @Test
    void findByEmail() {// Assuming there is a setId method
        customer.setEmail("test@example.com");  // Assuming there is a setEmail method

        // Define the behavior of the mocked repository
        when(customerRepo.save(customer)).thenReturn(customer);
        when(customerRepo.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        Customer customer1 = customerRepo.save(customer);
        Optional<Customer> customer2 = customerRepo.findByEmail(customer1.getEmail());
        Assertions.assertTrue(customer2.isPresent());
        Assertions.assertEquals(customer, customer2.get());
    }
}