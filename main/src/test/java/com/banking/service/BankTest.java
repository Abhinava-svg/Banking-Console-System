package com.banking.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;
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

    @Test
    void testDeposit() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        bank.createAccount(acc);
        assertThrows(InvalidAmountException.class, () -> {bank.deposit(54875, -5000);});
        assertEquals(100000, acc.getBalance());
    }

    @Test
    void testInsufficientBalance() throws InvalidAmountException {
        Bank bank = new Bank();

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 105000);
        bank.createAccount(acc);
        assertThrows(InsufficientBalanceException.class, () -> {bank.withdraw(54875, 1000000);});
        assertEquals(105000, acc.getBalance());
    }

    @Test
    void testWithdraw() {
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 105000);

        bank.createAccount(acc);
        assertThrows(InvalidAmountException.class, () -> {bank.withdraw(54875, -5000);});
        assertEquals(105000, acc.getBalance());
    }

    @Test
    void testTransfer() throws InvalidAmountException, InsufficientBalanceException{
      Bank bank = new Bank();
      
      Customer obj1 = new Customer(101, "Rahul", "rahuk@gmail.com");
      Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");

      Account acc1 = new Account(54875, obj1, 100000);
      Account acc2 = new Account(54876, obj2, 50000);

      bank.createAccount(acc1);
      bank.createAccount(acc2);

      bank.transfer(54875, 54876, 20000);

      assertEquals(80000, acc1.getBalance());
      assertEquals(70000, acc2.getBalance());

    }
}
