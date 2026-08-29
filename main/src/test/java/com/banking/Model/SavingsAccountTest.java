package com.banking.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;

public class SavingsAccountTest {
    @Test
    void testGetInterestRate() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        SavingsAccount acc = new SavingsAccount(54872, obj, 100000, 6);
        
        assertEquals(6, acc.getInterestRate());
    }
    
    @Test
    void testSetInterestRate() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        SavingsAccount acc = new SavingsAccount(54872, obj, 100000, 6);

        acc.setInterestRate(7);
        assertEquals(7, acc.getInterestRate());
    }

    @Test
    void testInheritedAccountMethods() throws InvalidAmountException, InsufficientBalanceException{


        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        SavingsAccount acc = new SavingsAccount(54872, obj, 100000, 6);
        
        assertEquals(54872, acc.getAccountNumber());
        assertEquals(obj, acc.getCustomer());
        assertEquals(100000, acc.getBalance());
        
        acc.deposit(5000);        
        assertEquals(105000, acc.getBalance());

        acc.withdraw(5000);
        assertEquals(100000, acc.getBalance());
    }
}
