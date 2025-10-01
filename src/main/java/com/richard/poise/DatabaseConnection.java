package com.richard.poise;

import java.sql.*;

//database connection class to manage connection to database
public class DatabaseConnection {
    // private as these values can only be seen in this class
// static as they belong to the class
// final: values cannot be changed
    private static final String URL = System.getenv().getOrDefault(
            "DATABASE_URL",
            "jdbc:postgresql://localhost:5432/poisepms"
    );
    private static final String USERNAME = System.getenv().getOrDefault("DB_USERNAME", "richard");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "richard");
    // A method to be called when the database needs to be connected to
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
