package com.banking.Dao;

import java.sql.*;

import com.banking.Model.Account;
import com.banking.util.DBConnection;

public class AccountDAO {
    public void save(Account account) throws SQLException{
        String sql = """
                INSERT INTO account(account_number, customer_id, balance) VALUES(?, ?, ?)
                """;

            try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

                pstmt.setInt(1, account.getAccountNumber());
                pstmt.setInt(2, account.getCustomer().getCustomerId());
                pstmt.setDouble(3, account.getBalance());

                pstmt.executeUpdate();
        }
    }
}
