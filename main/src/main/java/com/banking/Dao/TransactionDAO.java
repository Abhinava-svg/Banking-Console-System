package com.banking.Dao;

import java.sql.*;
import com.banking.Model.Transaction;
import com.banking.Model.Account;
import com.banking.util.DBConnection;

public class TransactionDAO {
    public void save(Account account, Transaction transaction)throws SQLException {
        String sql ="""
                INSERT INTO bank_transaction(account_number, transaction_type, amount) VALUES (?, ?, ?)
                """;

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setInt(1, account.getAccountNumber());
            pstmt.setString(2, transaction.getType().name());
            pstmt.setDouble(3, transaction.getAmount());

            pstmt.executeUpdate();
        }
    }
}
