package com.banking.util;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USERNAME = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
