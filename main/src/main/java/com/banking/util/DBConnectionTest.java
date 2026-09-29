package com.banking.util;

import java.sql.*;

public class DBConnectionTest {
    public static void main(String[] args){
        try(Connection connection = DBConnection.getConnection()){
            System.out.println("===============================");
            System.out.println("Database connection succesfully");
            System.out.println("===============================");
        }
        catch(Exception e){
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }
}
