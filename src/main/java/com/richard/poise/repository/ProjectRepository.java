package com.richard.poise.repository;

import com.richard.poise.model.Projects;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Project data access operations. Defines the contract for all
 * project-related database operations. Implemented by DatabaseProjectRepository for PostgreSQL.
 */
public interface ProjectRepository {
  /**
   * Retrieves a summary of all projects (ID, name, and completion status). Optimized for list views
   * where full details aren't needed.
   *
   * @return List of all projects with basic info
   */
  List<Projects> getAllProjectsSummary();

  /**
   * Finds a project by its unique ID. Returns complete project details including all relationships.
   *
   * @param projectID the project's database ID
   * @return Optional containing the project if found, empty otherwise
   */
  Optional<Projects> findByID(int projectID);

  /**
   * Finds a project by its exact name.
   *
   * @param projectName the project's full name
   * @return Optional containing the project if found, empty otherwise
   */
  Optional<Projects> findByName(String projectName);

  /**
   * Updates all fields of an existing project.
   *
   * @return true if update successful, false otherwise
   */
  boolean updateProjectData(
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
      java.sql.Date completionDate);

  /**
   * Creates a new project in the database.
   *
   * @return the generated project ID if successful, 0 if failed
   */
  int createProjectData(
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
      java.sql.Date completionDate);

  /**
   * Deletes a project from the database.
   *
   * @param project_id the ID of the project to delete
   * @return true if deletion successful, false otherwise
   */
  boolean deleteProjectData(int project_id);

  /**
   * Marks a project as finalised and sets its completion date.
   *
   * @param projectID the project to finalise
   * @param finalisedDate the date the project was completed
   * @return true if finalisation successful, false otherwise
   */
  boolean finaliseProjectData(int projectID, Date finalisedDate);

  /**
   * Retrieves all projects that are not yet finalised.
   *
   * @return List of incomplete projects
   */
  List<Projects> getIncompleteProjects();

  /**
   * Retrieves all incomplete projects with deadlines in the past.
   *
   * @return List of overdue projects
   */
  List<Projects> getOverdueProjects();
}
