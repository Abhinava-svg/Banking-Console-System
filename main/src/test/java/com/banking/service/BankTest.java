package com.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.banking.Model.Customer;

public class BankTest {
    @Test
    void testCreateCustomer() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        bank.createCustomer(obj);
        assertEquals(obj, bank.findCustomer(101));

    }

    @Test
    void testFindCustomer() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        bank.createCustomer(obj);
        Customer result = bank.findCustomer(101);

        assertEquals(101, result.getCustomerId());
        assertEquals("Rahul", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
    }
}
