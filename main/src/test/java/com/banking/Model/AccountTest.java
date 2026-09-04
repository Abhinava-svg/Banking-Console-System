package com.banking.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banking.Exceptions.InvalidAmountException;
import com.banking.Enums.TransactionType;
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

    @Test
    void testGetTransaction() throws InvalidAmountException {
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        acc.deposit(50000);
        assertEquals(1, acc.getTransaction().size());

        Transaction trans = acc.getTransaction().get(0);

        assertEquals(TransactionType.DEPOSIT, trans.getType());
        assertEquals(50000, trans.getAmount());
        assertEquals(1, trans.getTransactionId());
    }

    @Test
    void testDepositTransaction() throws InvalidAmountException{
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        acc.deposit(50000);
        assertEquals(150000, acc.getBalance());
        
        assertEquals(1, acc.getTransaction().size());
        Transaction trans = acc.getTransaction().get(0);

        assertEquals(1, trans.getTransactionId());
        assertEquals(TransactionType.DEPOSIT, trans.getType());
        assertEquals(50000, trans.getAmount());
    }

    @Test
    void testWithdrawTransaction() throws InvalidAmountException, InsufficientBalanceException{
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        acc.withdraw(5000);

        assertEquals(95000, acc.getBalance());
        assertEquals(1, acc.getTransaction().size());

        Transaction trans = acc.getTransaction().get(0);
        assertEquals(1, trans.getTransactionId());
        assertEquals(TransactionType.WITHDRAW, trans.getType());
        assertEquals(5000, trans.getAmount());
        
    }

    @Test
    void testTransactionIdIncrement() throws InvalidAmountException, InsufficientBalanceException{

        Account acc = new Account(54875, new Customer(101, "Rahul", "rahul@gmail.com"), 100000);
        acc.deposit(5000);
        acc.withdraw(3000);
        acc.deposit(2000);

        assertEquals(3, acc.getTransaction().size());

        assertEquals(1, acc.getTransaction().get(0).getTransactionId());
        assertEquals(2, acc.getTransaction().get(1).getTransactionId());
        assertEquals(3, acc.getTransaction().get(2).getTransactionId());
    }

    @Test
    void testMultipleTransaction() throws InvalidAmountException, InsufficientBalanceException{
        Account acc = new Account(54875, new Customer(101, "Rahul", "rahul@gmail.com"), 100000);

        acc.deposit(5000);
        acc.withdraw(2000);
        acc.deposit(3000);

        assertEquals(106000, acc.getBalance());
        assertEquals(3, acc.getTransaction().size());
        assertEquals(TransactionType.DEPOSIT, acc.getTransaction().get(0).getType());
        assertEquals(TransactionType.WITHDRAW, acc.getTransaction().get(1).getType());
        assertEquals(TransactionType.DEPOSIT, acc.getTransaction().get(2).getType());

        assertEquals(5000, acc.getTransaction().get(0).getAmount());
        assertEquals(2000, acc.getTransaction().get(1).getAmount());
        assertEquals(3000, acc.getTransaction().get(2).getAmount());
    }

    @Test
    void testTransferListCannotBeModifiedDirectly() throws InvalidAmountException{
        Account acc = new Account(54875, new Customer(101, "Rahul", "rahul@gmail.com"), 100000);
        acc.deposit(5000);

        assertEquals(1, acc.getTransaction().size());

        assertThrows(UnsupportedOperationException.class, () -> {acc.getTransaction().clear();});
        assertEquals(1, acc.getTransaction().size());
    }

    @Test
    void testBalanceCannotBeChangedDirectly() throws InvalidAmountException, InsufficientBalanceException {
        Account acc = new Account(54875, new Customer(101, "Rahul", "rahul@gmail.com"), 100000);
        acc.deposit(5000);

        assertEquals(105000, acc.getBalance());
        assertEquals(1, acc.getTransaction().size());

        acc.withdraw(50000);

        assertEquals(55000, acc.getBalance());
        assertEquals(2, acc.getTransaction().size());
    }
}
