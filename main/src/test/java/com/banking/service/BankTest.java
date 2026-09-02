package com.banking.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banking.Model.Account;
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

    @Test
    void testCreateAccount() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);
        bank.createAccount(acc);

        assertEquals(acc, bank.findAccount(54875));
    }

    @Test
    void testFindAccountNumber() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        bank.createAccount(acc);
        bank.findAccount(54875);

        assertEquals(54875, acc.getAccountNumber());
        assertEquals(obj, acc.getCustomer());
        assertEquals(100000, acc.getBalance());
    }
}
