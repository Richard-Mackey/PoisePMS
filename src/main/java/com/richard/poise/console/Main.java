package com.richard.poise.console;


import com.richard.poise.repository.DatabaseConnection;
import com.richard.poise.model.People;
import com.richard.poise.model.Projects;

import java.sql.*;

public class Main {
  public static void main(String[] args) {
    try {
      Connection conn = DatabaseConnection.getConnection();
      System.out.println("Connection successful to PoisePMS database!");
      conn.close();
      displayAllPeople();
      displayAllProjects();
    } catch (SQLException e) {
      System.out.println("Connection failed: " + e.getMessage());
    }
  }

  public static void displayAllPeople() {
    try {
      Connection connection =
          DatabaseConnection.getConnection(); // calls connection method from DataBaseConnection
      Statement statement = connection.createStatement();
      System.out.println("Here is a list of all people:");
      System.out.println();
      printAllPeopleFromTable(statement);
      System.out.println();
      statement.close();
      connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Helper method to print all people from the SQL table
  public static void printAllPeopleFromTable(Statement statement) throws SQLException {
    ResultSet results =
        statement.executeQuery(
            "SELECT person_id, person_name, phone, email, address, role FROM people");
    while (results.next()) {
      // Create a person object from the database results
      People person =
          new People(
              results.getInt("person_id"),
              results.getString("person_name"),
              results.getString("phone"),
              results.getString("email"),
              results.getString("address"),
              results.getString("role"));

      // Then print out a person object
      System.out.println(person);
    }
  }

  public static void printAllProjectsFromTable(Statement statement) throws SQLException {
    ResultSet results =
        statement.executeQuery(
            "SELECT "
                + "project_id, "
                + "project_name, "
                + "building_type, "
                + "project_address, "
                + "ERF_number, "
                + "total_fee, "
                + "amount_paid_to_date, "
                + "project_deadline, "
                + "architect_id, "
                + "contractor_id, "
                + "customer_id, "
                + "engineer_id, "
                + "manager_id, "
                + "project_finalised, "
                + "completion_date "
                + "FROM projects");
    while (results.next()) {
      // Create a person object from the database results
      Projects project =
          new Projects(
              results.getInt("project_id"),
              results.getString("project_name"),
              results.getString("building_type"),
              results.getString("project_address"),
              results.getInt("ERF_number"),
              results.getDouble("total_fee"),
              results.getDouble("amount_paid_to_date"),
              results.getDate("project_deadline"),
              results.getInt("architect_id"),
              results.getInt("contractor_id"),
              results.getInt("customer_id"),
              results.getInt("engineer_id"),
              results.getInt("manager_id"),
              results.getBoolean("project_finalised"),
              results.getDate("completion_date"));
      // Then print out a project object
      System.out.println(project);
    }
  }

  public static void displayAllProjects() {
    try {
      Connection connection =
          DatabaseConnection.getConnection(); // calls connection method from DataBaseConnection
      Statement statement = connection.createStatement();
      System.out.println("Here is a list of all projects:");
      System.out.println();
      printAllProjectsFromTable(statement);
      System.out.println();
      statement.close();
      connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
