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

    public void updateBalance(Connection connection, int accountNumber, double newBalance) throws SQLException{
        String sql = """
                        UPDATE account 
                        SET balance = ? 
                        WHERE account_number = ?
                        """;

        
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setDouble(1, newBalance);
            pstmt.setInt(2, accountNumber);

            int rowsUpdated = pstmt.executeUpdate();

            if(rowsUpdated == 0){
                throw new SQLException("Account not found in database");
            }
        }
    }
}
