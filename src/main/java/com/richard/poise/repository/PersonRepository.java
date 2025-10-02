package com.richard.poise.repository;

import com.richard.poise.model.People;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Person data access operations. Defines the contract for all
 * person-related database operations. Implemented by DatabasePersonRepository for PostgreSQL.
 */
public interface PersonRepository {
  /**
   * Retrieves all people from the database.
   *
   * @return List of all people
   */
  List<People> findAll();

  /**
   * Finds a person by their unique ID.
   *
   * @param personID the person's database ID
   * @return Optional containing the person if found, empty otherwise
   */
  Optional<People> findByID(int personID); // Changed

  /**
   * Finds a person by their exact name.
   *
   * @param personName the person's full name
   * @return Optional containing the person if found, empty otherwise
   */
  Optional<People> findByName(String personName); // Changed

  /**
   * Retrieves all people with a specific role.
   *
   * @param role the role to filter by (e.g., "customer", "architect")
   * @return List of people matching the role
   */
  List<People> findByRole(String role);

  /**
   * Updates an existing person's details.
   *
   * @return true if update successful, false otherwise
   */
  boolean updatePersonData(
      int personID,
      String personName,
      String phone,
      String email,
      String personAddress,
      String role);

  /**
   * Creates a new person in the database.
   *
   * @return the generated person ID if successful, 0 if failed
   */
  int createPersonData(
      String personName, String phone, String email, String personAddress, String role);

  /**
   * Deletes a person from the database.
   *
   * @param person_id the ID of the person to delete
   * @return true if deletion successful, false otherwise
   */
  boolean deletePersonData(int person_id);

  /**
   * Finds all projects where this person is assigned.
   *
   * @param personID the person's ID
   * @return List of project names they're assigned to
   */
  List<String> getProjectsLinkedToPerson(int personID);

  /**
   * Checks if a person is assigned as a customer to any project.
   *
   * @param personID the person's ID
   * @return true if they're a customer on any project
   */
  boolean isCustomerInProjects(int personID);
}
