package com.banking.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banking.Exceptions.InvalidAmountException;
import com.banking.Exceptions.InsufficientBalanceException;


public class AccountTest {
    @Test
    void testGetAccountNumber() {

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        assertEquals(5459972, acc.getAccountNumber());
    }
    
    @Test
    void testGetBalance() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        assertEquals(100000, acc.getBalance());
    }
    
    @Test
    void testGetCustomer() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        assertEquals(obj, acc.getCustomer());
        
    }
    
    @Test
    void testSetBalance() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);

        acc.setBalance(200000);
        assertEquals(200000, acc.getBalance());
        
    }
    
    @Test
    void testSetCustomer() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        Customer obj2 = new Customer(102, "Priya", "priya52@gmail.com");
        acc.setCustomer(obj2);
        assertEquals(obj2, acc.getCustomer());
    }
    
    @Test
    void testToString() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        String result = acc.toString();
        assertEquals("accountNumber:5459972Customer:customerId :101name :Rahulemail :rahul@gmail.combalance:100000.0", result);
    }
    
    @Test
    void testInvalidDeposit(){
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        assertThrows(InvalidAmountException.class, () -> acc.deposit(-5000));
        
        
    }
    
    @Test
    void testWithdraw() throws InvalidAmountException, InsufficientBalanceException {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);

        acc.withdraw(5000);

        assertEquals(95000, acc.getBalance());
    }

    @Test
    void testInvalidWithdraw() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
        
        assertThrows(InvalidAmountException.class, () -> acc.withdraw(-5000));
    }
    
    @Test
    void testWithdrawMoreThanBalance() {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(5459972, obj, 100000);
    
        assertThrows(InsufficientBalanceException.class, () -> acc.withdraw(150000));
    }
}
