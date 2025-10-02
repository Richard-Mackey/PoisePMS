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
      System.getenv()
          .getOrDefault(
              "DATABASE_URL",
              "jdbc:postgresql://localhost:5432/poisepms?user=richard&password=richard");

  // A method to be called when the database needs to be connected to
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL);
  }
}
