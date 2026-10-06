package com.banking.Dao;

import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
import com.banking.Model.Customer;

import org.junit.jupiter.api.Test;
import com.banking.util.DBConnection;

public class CustomerDAOTest {
    @Test
    void testSave() throws SQLException {
        int customerId = newId();
        Customer customer = new Customer(
                customerId,
                "DAO Test Customer",
                "dao-test-" + customerId + "@example.test");
        CustomerDAO customerDAO = new CustomerDAO();

        boolean customerSaved = false;
        try {
            customerDAO.save(customer);
            customerSaved = true;
        } finally {
            if (customerSaved) {
                deleteCustomer(customerId);
            }
        }
    }

    private int newId() {
        return ThreadLocalRandom.current().nextInt(1_000_000, Integer.MAX_VALUE);
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
