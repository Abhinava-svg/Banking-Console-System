package com.banking.Dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.banking.Enums.TransactionType;
import com.banking.Model.Account;
import com.banking.Model.Customer;
import com.banking.Model.Transaction;

public class TransactionDAOTest {
    @Test
    void testSave() {

        Customer customer = new Customer(104, "Virat", "kohlivirat18@gmail.com");
        Account account = new Account(54876, customer, 100000);

        Transaction transaction = new Transaction(2, TransactionType.DEPOSIT,50000);
        TransactionDAO transactionDAO = new TransactionDAO();

        assertDoesNotThrow(() -> {transactionDAO.save(account,transaction);});
    }
}
