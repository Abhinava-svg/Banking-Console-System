package com.banking.Dao;

import com.banking.Model.Customer;
import com.banking.util.DBConnection;
import java.sql.*;

public class CustomerDAO {
    public void save(Customer customer) throws SQLException {

        String sql = """
                INSERT INTO customer(Customer_id, name, email) VALUES (?, ?, ?)
                """;

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setInt(1, customer.getCustomerId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());

            pstmt.executeUpdate();
        }
    }
}
