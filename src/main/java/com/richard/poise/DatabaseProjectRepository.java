package com.richard.poise;

import java.sql.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class DatabaseProjectRepository implements ProjectRepository {
  @Override
  public List<Projects> getAllProjectsSummary() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseConnection.getConnection();
      String sql = "SELECT project_id, project_name, project_finalised FROM projects";
      preparedStatement = connection.prepareStatement(sql);

      ResultSet results = preparedStatement.executeQuery();
      List<Projects> projectsList = new ArrayList<>();

      while (results.next()) {
        Projects project =
            new Projects(
                results.getInt("project_id"),
                results.getString("project_name"),
                results.getBoolean("project_finalised"));
        projectsList.add(project);
      }
      return projectsList;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ArrayList<>();
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Optional<Projects> findByID(int projectID) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseConnection.getConnection();
      String sql = "SELECT * FROM projects WHERE project_id = ?";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, projectID); // Use setInt for ID

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        Projects foundProject =
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
        return Optional.of(foundProject);
      }
      return Optional.empty();

    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Optional<Projects> findByName(String projectName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM projects WHERE LOWER(project_name) = LOWER(?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, projectName);

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        Projects foundProject =
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
        return Optional.of(foundProject);
      }
      return Optional.empty();

    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean updateProjectData(
      int projectID,
      String projectName,
      String buildingType,
      String projectAddress,
      int ERFNumber,
      double totalFee,
      double amountPaidToDate,
      java.sql.Date projectDeadline,
      int architectID,
      int contractorID,
      int customerID,
      int engineerID,
      int managerID,
      boolean projectFinalised,
      java.sql.Date completionDate) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String sql =
          "UPDATE projects SET project_name = ?, building_type = ?, project_address = ?, ERF_number = ?, total_fee = ?, amount_paid_to_date = ?, project_deadline = ?, architect_id = ?, contractor_id = ?, customer_id = ?, engineer_id = ?, manager_id = ?, project_finalised = ?, completion_date = ? WHERE project_id = ?";

      preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, projectName);
      preparedStatement.setString(2, buildingType);
      preparedStatement.setString(3, projectAddress);
      preparedStatement.setInt(4, ERFNumber);
      preparedStatement.setDouble(5, totalFee);
      preparedStatement.setDouble(6, amountPaidToDate);
      preparedStatement.setDate(7, projectDeadline);
      preparedStatement.setInt(8, architectID);
      preparedStatement.setInt(9, contractorID);
      preparedStatement.setInt(10, customerID);
      preparedStatement.setInt(11, engineerID);
      preparedStatement.setInt(12, managerID);
      preparedStatement.setBoolean(13, projectFinalised);
      preparedStatement.setDate(14, completionDate);
      preparedStatement.setInt(15, projectID);

      int rowsAffected = preparedStatement.executeUpdate();

      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public int createProjectData(
      String projectName,
      String buildingType,
      String projectAddress,
      int ERFNumber,
      double totalFee,
      double amountPaidToDate,
      java.sql.Date projectDeadline,
      int architectID,
      int contractorID,
      int customerID,
      int engineerID,
      int managerID,
      boolean projectFinalised,
      java.sql.Date completionDate) {
    Connection connection = null;
    PreparedStatement preparedStatement = null; // Use this consistently

    try {
      connection = DatabaseConnection.getConnection();

      String insertSQL =
          "INSERT INTO projects (project_name, building_type, project_address, ERF_number, total_fee, amount_paid_to_date, project_deadline, architect_id, contractor_id, customer_id, engineer_id, manager_id, project_finalised, completion_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      preparedStatement =
          connection.prepareStatement(
              insertSQL, Statement.RETURN_GENERATED_KEYS); // Use same variable

      preparedStatement.setString(1, projectName);
      preparedStatement.setString(2, buildingType);
      preparedStatement.setString(3, projectAddress);
      preparedStatement.setInt(4, ERFNumber);
      preparedStatement.setDouble(5, totalFee);
      preparedStatement.setDouble(6, amountPaidToDate);
      preparedStatement.setDate(7, projectDeadline);
      preparedStatement.setInt(8, architectID);
      preparedStatement.setInt(9, contractorID);
      preparedStatement.setInt(10, customerID);
      preparedStatement.setInt(11, engineerID);
      preparedStatement.setInt(12, managerID);
      preparedStatement.setBoolean(13, projectFinalised);
      preparedStatement.setDate(14, completionDate);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
          // generated ID number linked to newly added person
          int personId = generatedKeys.getInt(1);
          generatedKeys.close();
          preparedStatement.close();
          connection.close();
          return personId;
        }
      }

      return 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return 0;
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean deleteProjectData(int project_id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String deleteSQL = "DELETE FROM projects WHERE project_id = ?";
      preparedStatement = connection.prepareStatement(deleteSQL);
      preparedStatement.setInt(1, project_id);

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0; // Simplified - returns true if any rows deleted

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean finaliseProjectData(int projectID, Date finalisedDate) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String updateSQL =
          "UPDATE projects SET project_finalised = ?, completion_date = ? WHERE project_id = ?";
      preparedStatement = connection.prepareStatement(updateSQL);
      preparedStatement.setBoolean(1, true);
      preparedStatement.setDate(2, finalisedDate);
      preparedStatement.setInt(3, projectID);

      int rowsAffected = preparedStatement.executeUpdate();
      return rowsAffected > 0; // Simplified

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<Projects> getIncompleteProjects() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet results = null; // Declare outside try block

    try {
      connection = DatabaseConnection.getConnection();

      String searchSQL =
          "SELECT project_id, project_name, project_finalised FROM projects WHERE project_finalised = ?";
      preparedStatement = connection.prepareStatement(searchSQL);
      preparedStatement.setBoolean(1, false);

      results = preparedStatement.executeQuery();
      List<Projects> projectsList = new ArrayList<>(); // Assign to declared variable

      while (results.next()) {
        Projects project =
            new Projects(
                results.getInt("project_id"),
                results.getString("project_name"),
                results.getBoolean("project_finalised"));
        // Add to list, don't print
        projectsList.add(project);
      }
      return projectsList;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ArrayList<>();
    } finally {
      try {
        if (results != null) results.close(); // Added!
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<Projects> getOverdueProjects() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet results = null;
    try {
      connection = DatabaseConnection.getConnection();

      String searchSQL =
          "SELECT project_id, project_name, project_finalised FROM projects WHERE project_deadline < CURDATE() AND project_finalised = ?";
      preparedStatement = connection.prepareStatement(searchSQL);
      preparedStatement.setBoolean(1, false);
      results = preparedStatement.executeQuery();
      List<Projects> projectsList = new ArrayList<>();

      while (results.next()) {
        Projects project =
            new Projects(
                results.getInt("project_id"),
                results.getString("project_name"),
                results.getBoolean("project_finalised"));
        // Add to list, don't print
        projectsList.add(project);
      }
      return projectsList;

    } catch (SQLException e) {
      e.printStackTrace();
      return new ArrayList<>();
    } finally {
      try {
        if (results != null) results.close(); // Added!
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
  }