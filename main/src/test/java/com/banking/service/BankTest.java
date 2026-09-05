package com.banking.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.banking.Enums.TransactionType;

import com.banking.Exceptions.AccountNotFoundException;
import com.banking.Exceptions.DuplicateAccountException;
import com.banking.Exceptions.DuplicateCustomerException;
import com.banking.Exceptions.InsufficientBalanceException;
import com.banking.Exceptions.InvalidAmountException;
import com.banking.Model.Account;
import com.banking.Model.Customer;

public class BankTest {
    @Test
    void testCreateCustomer() throws DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        bank.createCustomer(obj);
        assertEquals(obj, bank.findCustomer(101));

    }

    @Test
    void testFindCustomer() throws DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");

        bank.createCustomer(obj);
        Customer result = bank.findCustomer(101);

        assertEquals(101, result.getCustomerId());
        assertEquals("Rahul", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());
    }

    @Test
    void testCreateAccount() throws DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);
        bank.createCustomer(obj);
        bank.createAccount(acc);

        assertEquals(acc, bank.findAccount(54875));
    }

    @Test
    void testFindAccountNumber() throws DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        bank.createCustomer(obj);
        bank.createAccount(acc);
        Account result = bank.findAccount(54875);

        assertEquals(54875, result.getAccountNumber());
        assertEquals(obj, result.getCustomer());
        assertEquals(100000, result.getBalance());
    }

    @Test
    void testDeposit() throws DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        bank.createCustomer(obj);
        bank.createAccount(acc);
        assertThrows(InvalidAmountException.class, () -> {bank.deposit(54875, -5000);});
        assertEquals(100000, acc.getBalance());
    }

    @Test
    void testInsufficientBalance() throws InvalidAmountException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException {
        Bank bank = new Bank();

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 105000);

        bank.createCustomer(obj);
        bank.createAccount(acc);
        assertThrows(InsufficientBalanceException.class, () -> {bank.withdraw(54875, 1000000);});
        assertEquals(105000, acc.getBalance());
    }

    @Test
    void testWithdraw() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 105000);

        bank.createCustomer(obj);
        bank.createAccount(acc);
        assertThrows(InvalidAmountException.class, () -> {bank.withdraw(54875, -5000);});
        assertEquals(105000, acc.getBalance());
    }

    @Test
    void testTransfer() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
      Bank bank = new Bank();
      
      Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
      Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");

      Account acc1 = new Account(54875, obj1, 100000);
      Account acc2 = new Account(54876, obj2, 50000);

      bank.createCustomer(obj1);
      bank.createCustomer(obj2);

      bank.createAccount(acc1);
      bank.createAccount(acc2);

      bank.transfer(54875, 54876, 20000);

      assertEquals(80000, acc1.getBalance());
      assertEquals(70000, acc2.getBalance());

    }

    @Test
    void testTransactionTransfer() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();

        Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc1 = new Account(54875, obj1, 100000);

        Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");
        Account acc2 = new Account(54876, obj2, 50000);

        bank.createCustomer(obj1);
        bank.createAccount(acc1);

        bank.createCustomer(obj2);
        bank.createAccount(acc2);


        bank.transfer(54875, 54876, 10000);

        assertEquals(90000, acc1.getBalance());
        assertEquals(60000, acc2.getBalance());

        assertEquals(1, acc1.getTransaction().size());
        assertEquals(1, acc2.getTransaction().size());

        assertEquals(TransactionType.WITHDRAW, acc1.getTransaction().get(0).getType());
        assertEquals(TransactionType.DEPOSIT, acc2.getTransaction().get(0).getType());

        assertEquals(10000, acc1.getTransaction().get(0).getAmount());
        assertEquals(10000, acc2.getTransaction().get(0).getAmount());
        
    }

    @Test
    void testDepositAccountNotFound(){
        Bank bank = new Bank();

        assertThrows(AccountNotFoundException.class, () -> {bank.deposit(54875, 5000);});
    }

    @Test
    void testWithdrawAccountNotFound(){
        Bank bank = new Bank();
        assertThrows(AccountNotFoundException.class, () -> {bank.withdraw(54875, 5000);});
    }

    @Test
    void testTransferSourceAccountNotFfound() throws InvalidAmountException, InsufficientBalanceException{
        Bank bank = new Bank();
        assertThrows(AccountNotFoundException.class, () -> {bank.transfer(54875, 54876, 5000);});
    }

    @Test
    void testTransferDestinationAccountNotFound() throws InvalidAmountException, InsufficientBalanceException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);
        bank.createCustomer(obj);
        bank.createAccount(acc);
        assertThrows(AccountNotFoundException.class, () -> {bank.transfer(54875, 54876, 5000);});
    }

    @Test
    void testDuplicateCustomer() throws DuplicateCustomerException {

        Bank bank = new Bank();

        Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
        Customer obj2 = new Customer(101, "Priya", "priya@gmail.com");

        bank.createCustomer(obj1);

        assertThrows(DuplicateCustomerException.class, () -> {bank.createCustomer(obj2);});
    }

    @Test
    void testDuplicateAccount() throws DuplicateAccountException, DuplicateCustomerException{

        Bank bank = new Bank();
        Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
        Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");

        Account acc1 = new Account(54875, obj1, 100000);
        Account acc2 = new Account(54875, obj2, 50000);

        bank.createCustomer(obj1);
        bank.createCustomer(obj2);

        bank.createAccount(acc1);

        assertThrows(DuplicateAccountException.class, () -> {bank.createAccount(acc2);});
    }

    @Test
    void testTransferInsufficientBalance() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();

        Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
        Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");

        Account acc1 = new Account(54875, obj1, 100000);
        Account acc2 = new Account(54876, obj2, 50000);

        bank.createCustomer(obj1);
        bank.createCustomer(obj2);

        bank.createAccount(acc1);
        bank.createAccount(acc2);

        assertThrows(InsufficientBalanceException.class, () -> {bank.transfer(54875, 54876, 200000);});

        assertEquals(100000, acc1.getBalance());
        assertEquals(50000, acc2.getBalance());
    }

    @Test
    void testTransferInvalidAmount() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();

        Customer obj1 = new Customer(101, "Rahul", "rahul@gmail.com");
        Customer obj2 = new Customer(102, "Priya", "priya@gmail.com");

        Account acc1 = new Account(54875, obj1, 100000);
        Account acc2 = new Account(54876, obj2, 50000);

        bank.createCustomer(obj1);
        bank.createCustomer(obj2);

        bank.createAccount(acc1);
        bank.createAccount(acc2);

        assertThrows(InvalidAmountException.class, () -> {bank.transfer(54875, 54876, -5000);});

        assertEquals(100000, acc1.getBalance());
        assertEquals(50000, acc2.getBalance());
    }

    @Test
    void testTransferToSameAccount() throws InvalidAmountException, InsufficientBalanceException, AccountNotFoundException, DuplicateAccountException, DuplicateCustomerException{
        Bank bank = new Bank();

        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        bank.createCustomer(obj);
        bank.createAccount(acc);

        assertThrows(IllegalArgumentException.class, () -> {bank.transfer(54875, 54875, 50000);});
        assertEquals(100000, acc.getBalance());
        assertEquals(0, acc.getTransaction().size());
    }

    @Test
    void testCreateAccountForNonExistenceCustomer() throws DuplicateAccountException{

        Bank bank = new Bank();
        
        Customer obj = new Customer(101, "Rahul", "rahul@gmail.com");
        Account acc = new Account(54875, obj, 100000);

        assertThrows(IllegalArgumentException.class, () -> {bank.createAccount(acc);});
        assertNull(bank.findAccount(54875));
    }
}
