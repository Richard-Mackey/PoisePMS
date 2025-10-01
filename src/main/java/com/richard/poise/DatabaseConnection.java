package com.richard.poise;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = System.getenv().getOrDefault(
            "DATABASE_URL",
            "jdbc:postgresql://localhost:5432/poisepms?user=richard&password=richard"
    );

    // A method to be called when the database needs to be connected to
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}