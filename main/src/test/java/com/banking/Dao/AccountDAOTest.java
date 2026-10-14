package com.banking.Dao;

import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import com.banking.Model.Account;
import com.banking.Model.Customer;
import com.banking.util.DBConnection;

public class AccountDAOTest {
    @Test
    void testSave() throws SQLException {
        int customerId = newId();
        int accountNumber = newId();
        Customer customer = new Customer(
                customerId,
                "DAO Test Customer",
                "dao-test-" + customerId + "@example.test");

        Account account = new Account(accountNumber, customer, 50000);
        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        boolean customerSaved = false;
        boolean accountSaved = false;
        try {
            customerDAO.save(customer);
            customerSaved = true;
            accountDAO.save(account);
            accountSaved = true;
        } finally {
            if (accountSaved) {
                deleteAccount(accountNumber);
            }
            if (customerSaved) {
                deleteCustomer(customerId);
            }
        }
    }

    @Test 
    void testUpdateBalance() throws SQLException{
        int customerId = newId();
        int accountNumber = newId();
        Customer customer = new Customer(customerId, "Balance Test Customer", "balance-test" + customerId + "@example.test");

        Account account = new Account(accountNumber, customer, 50000);
        AccountDAO accountDAO = new AccountDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        boolean customerSaved = false;
        boolean accountSaved = false;
        try{
            customerDAO.save(customer);
            customerSaved = true;
            accountDAO.save(account);
            accountSaved = true;

            try(Connection connection = DBConnection.getConnection()){

                accountDAO.updateBalance(connection, accountNumber, 15000);
            }
            String sql = """
                        SELECT balance from account WHERE account_number = ?
                        """;

            try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

                pstmt.setInt(1, accountNumber);

                try(ResultSet rs = pstmt.executeQuery()){
                    org.junit.jupiter.api.Assertions.assertTrue(rs.next());
                    org.junit.jupiter.api.Assertions.assertEquals(15000, rs.getDouble("balance"), 0.001);
                }
            }
        }

        finally{
            if(accountSaved){
                deleteAccount(accountNumber);
            }
            if(customerSaved){
                deleteAccount(accountNumber);
            }
        }
    }

    private int newId() {
        return ThreadLocalRandom.current().nextInt(1_000_000, Integer.MAX_VALUE);
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
        String sql = "DELETE FROM customer WHERE Customer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }
}
