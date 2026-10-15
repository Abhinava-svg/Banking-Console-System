package com.banking.service;

import com.banking.util.DBConnection;
import com.banking.Model.Account;
import com.banking.Model.Customer;
import com.banking.Model.Transaction;
import com.banking.Enums.TransactionType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankDAOIntegrationTest {

    private final Bank bank = new Bank();

    @BeforeEach
    void cleanTestDataBefore() throws SQLException {
        cleanTestData();
    }

    @AfterEach
    void cleanTestDataAfter() throws SQLException {
        cleanTestData();
    }

    private void cleanTestData() throws SQLException {

        try (Connection connection = DBConnection.getConnection()) {

            // Delete dependent transaction records first.
            String deleteTransactions =
                    "DELETE FROM bank_transaction WHERE account_number = ?";

            try (PreparedStatement pstmt =
                         connection.prepareStatement(deleteTransactions)) {
                pstmt.setInt(1, 54878);
                pstmt.executeUpdate();
            }

            // Delete the test account.
            String deleteAccount =
                    "DELETE FROM account WHERE account_number = ?";

            try (PreparedStatement pstmt =
                         connection.prepareStatement(deleteAccount)) {
                pstmt.setInt(1, 54878);
                pstmt.executeUpdate();
            }

            // Delete only the dedicated test customers.
            String deleteCustomer =
                    "DELETE FROM customer WHERE customer_id IN (?, ?)";

            try (PreparedStatement pstmt =
                         connection.prepareStatement(deleteCustomer)) {
                pstmt.setInt(1, 105);
                pstmt.setInt(2, 106);
                pstmt.executeUpdate();
            }
        }
    }

    @Test
    void testSaveCustomer() {

        Customer customer = new Customer(
                105, "Test Customer", "test105@gmail.com"
        );

        assertDoesNotThrow(() -> bank.saveCustomer(customer));
    }

    @Test
    void testSaveAccount() throws SQLException {

        Customer customer = new Customer(
                106, "Sameer", "sameer19@gmail.com"
        );

        Account account = new Account(
                54878, customer, 10000
        );

        // The account needs an existing customer in MySQL.
        assertDoesNotThrow(() -> bank.saveCustomer(customer));

        assertDoesNotThrow(() -> bank.saveAccount(account));
    }

    @Test 
    void testSaveTransaction() throws SQLException{

        Bank bank = new Bank();

        Customer customer = new Customer(106, "Sameer", "sameer19@gmail.com");
        Account account = new Account(54878, customer, 10000);
        Transaction transaction = new Transaction(1, TransactionType.DEPOSIT, 500);

        assertDoesNotThrow(() -> {bank.saveCustomer(customer);});
        assertDoesNotThrow(() -> {bank.saveAccount(account);});
        assertDoesNotThrow(() -> {bank.saveTransaction(account, transaction);});
    }

    @Test 
    void testDepositToDatabase() throws Exception {

        Bank bank = new Bank();
        double openingBalance = 10_000;
        double depositAmount = 5_000;

        int customerId = newId();
        int accountNumber = newId();

        Customer customer = new Customer(customerId, "Deposit Test Customer", "deposit-" + customerId + "@example.test");
        Account account = new Account(accountNumber, customer, openingBalance);

        boolean customerSaved = false;
        boolean accountSaved = false;

        try {
            bank.saveCustomer(customer);
            customerSaved = true;

            bank.saveAccount(account);
            accountSaved = true;

            bank.createCustomer(customer);
            bank.createAccount(account);

            Account savedAccount = bank.findAccount(accountNumber);

            assertEquals(openingBalance, savedAccount.getBalance(), 0.001);

            bank.depositToDatabase(accountNumber, depositAmount);

            assertEquals(openingBalance + depositAmount, savedAccount.getBalance(), 0.001);
            assertPersistedDeposit(
                    accountNumber, openingBalance + depositAmount, depositAmount);
        } finally {
            if (accountSaved) {
                deleteTransactions(accountNumber);
                deleteAccount(accountNumber);
            }

            if (customerSaved) {
                deleteCustomer(customerId);
            }
        }
    }

    private void assertPersistedDeposit(
            int accountNumber, double expectedBalance, double expectedDeposit)
            throws SQLException {
        String balanceSql = "SELECT balance FROM account WHERE account_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(balanceSql)) {
            pstmt.setInt(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "The account should be stored in the database");
                assertEquals(expectedBalance, rs.getDouble("balance"), 0.001);
            }
        }

        String transactionSql = """
                SELECT transaction_type, amount
                FROM bank_transaction
                WHERE account_number = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(transactionSql)) {
            pstmt.setInt(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "The deposit transaction should be stored in the database");
                assertEquals(TransactionType.DEPOSIT.name(), rs.getString("transaction_type"));
                assertEquals(expectedDeposit, rs.getDouble("amount"), 0.001);
                assertFalse(rs.next(), "Exactly one transaction should be created for the deposit");
            }
        }
    }

    private void deleteAccount(int accountNumber) throws SQLException {
        String sql = "DELETE FROM account WHERE account_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, accountNumber);
            pstmt.executeUpdate();
        }
    }

    private void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }

    private int newId(){
        return (int) (Math.random()  * 900000) + 100000;
    }

    private void deleteTransactions(int accountNumber) throws SQLException {
        String sql = "DELETE FROM bank_transaction WHERE account_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, accountNumber);
            pstmt.executeUpdate();
        }
    }
}
