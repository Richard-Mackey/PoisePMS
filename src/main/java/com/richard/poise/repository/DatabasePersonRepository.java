package com.richard.poise.repository;

import com.richard.poise.model.People;

import java.sql.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class DatabasePersonRepository implements PersonRepository {
  @Override
  public Optional<People> findByID(int personID) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseConnection.getConnection();
      String sql = "SELECT * FROM people WHERE person_id = ?";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, personID);

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        People foundPerson =
            new People(
                results.getInt("person_id"),
                results.getString("person_name"),
                results.getString("phone"),
                results.getString("email"),
                results.getString("address"),
                results.getString("role"));
        return Optional.of(foundPerson);
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
    public List<People> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM people";
            preparedStatement = connection.prepareStatement(sql);

            ResultSet results = preparedStatement.executeQuery();
            List<People> peopleList = new ArrayList<>();

            while (results.next()) {
                People person = new People(
                        results.getInt("person_id"),
                        results.getString("person_name"),
                        results.getString("phone"),
                        results.getString("email"),
                        results.getString("address"),
                        results.getString("role")
                );
                peopleList.add(person);
            }
            return peopleList;

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
  public Optional<People> findByName(String personName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM people WHERE LOWER(role) = LOWER(?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, personName);

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        People foundPerson =
            new People(
                results.getInt("person_id"),
                results.getString("person_name"),
                results.getString("phone"),
                results.getString("email"),
                results.getString("address"),
                results.getString("role"));
        return Optional.of(foundPerson);
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
  public List<People> findByRole(String role) {
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM people WHERE LOWER(role) = LOWER(?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, role);

      ResultSet results = preparedStatement.executeQuery();

      ArrayList<People> peopleList = new ArrayList<>();
      while (results.next()) {
        People person =
            new People(
                results.getInt("person_id"),
                results.getString("person_name"),
                results.getString("phone"),
                results.getString("email"),
                results.getString("address"),
                results.getString("role"));
        // add to the list
        peopleList.add(person);
      }

      return peopleList; // Return the completed list
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
  public boolean updatePersonData(
      int personID,
      String personName,
      String phone,
      String email,
      String personAddress,
      String role) {
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String sql =
          "UPDATE people SET person_name = ?, phone = ?, email = ?, address = ?, role = ? WHERE person_id = ?";

      preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, personName);
      preparedStatement.setString(2, phone);
      preparedStatement.setString(3, email);
      preparedStatement.setString(4, personAddress);
      preparedStatement.setString(5, role);
      preparedStatement.setInt(6, personID);

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
  public int createPersonData(
      String personName, String phone, String email, String personAddress, String role) {
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String insertSQL =
          "INSERT INTO people (person_name, phone, email, address, role) VALUES (?, ?, ?, ?, ?)";
      // generated keys returned from SQL auto increment.
      PreparedStatement insertStatement =
          connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

      insertStatement.setString(1, personName);
      insertStatement.setString(2, phone);
      insertStatement.setString(3, email);
      insertStatement.setString(4, personAddress);
      insertStatement.setString(5, role);

      int rowsAffected = insertStatement.executeUpdate();
      if (rowsAffected > 0) {
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
          // generated ID number linked to newly added person
          int personId = generatedKeys.getInt(1);
          generatedKeys.close();
          insertStatement.close();
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
  public boolean deletePersonData(int person_id) {
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String deleteSQL = "DELETE FROM people WHERE (person_id = ?)";
      preparedStatement = connection.prepareStatement(deleteSQL);
      preparedStatement.setInt(1, person_id);

      int rowsAffected = preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
      if (rowsAffected == 1) {
        return true;
      }
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
    return false;
  }

  @Override
  public List<String> getProjectsLinkedToPerson(int personID) {
    ArrayList<String> linkedProjects = new ArrayList<>();
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String checkSQL =
          "SELECT project_name FROM projects WHERE architect_id = ? OR contractor_id = ? OR engineer_id = ? OR manager_id = ?";

      preparedStatement = connection.prepareStatement(checkSQL);
      preparedStatement.setInt(1, personID);
      preparedStatement.setInt(2, personID);
      preparedStatement.setInt(3, personID);
      preparedStatement.setInt(4, personID);

      ResultSet results = preparedStatement.executeQuery();
      while (results.next()) {
        linkedProjects.add(results.getString("project_name"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return linkedProjects;
    } finally {
      try {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return linkedProjects;
  }

  @Override
  public boolean isCustomerInProjects(int personID) {
    Connection connection = null; // Declare outside try
    PreparedStatement preparedStatement = null;
    try {
      connection = DatabaseConnection.getConnection();

      String checkSQL = "SELECT COUNT(*) FROM projects WHERE customer_id = ?";
      preparedStatement = connection.prepareStatement(checkSQL);
      preparedStatement.setInt(1, personID);

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) {
        int count = results.getInt(1);
        preparedStatement.close();
        connection.close();
        return count > 0;
      }

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
    return false;
  }
}
