package com.banking.service;

import com.banking.Model.Customer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class BankDAOIntegrationTest {
    @Test

    void testSaveCustomer(){
        Bank bank = new Bank();

        Customer customer = new Customer(
            105, "Test Customer", "test105@gmail.com"
        );

        assertDoesNotThrow(() -> {bank.saveCustomer(customer);});
    }
}
