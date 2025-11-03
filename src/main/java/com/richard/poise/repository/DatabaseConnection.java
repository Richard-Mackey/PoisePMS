package com.richard.poise.repository;

import java.sql.*;

/**
 * Manages database connections for the application.
 *
 * <p>Uses environment variable DATABASE_URL for Render deployment and falls back to local
 * PostgreSQL for development.
 *
 * <p>Production: Render provides DATABASE_URL automatically Development: Connects to localhost
 * PostgreSQL with default credentials
 */
public class DatabaseConnection {
    private static final String URL =
            System.getenv("SPRING_DATASOURCE_URL");
    private static final String USERNAME =
            System.getenv("SPRING_DATASOURCE_USERNAME");
    private static final String PASSWORD =
            System.getenv("SPRING_DATASOURCE_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
