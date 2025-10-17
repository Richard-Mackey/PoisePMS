package com.richard.poise.service;

import com.richard.poise.model.People;
import com.richard.poise.model.Projects;
import com.richard.poise.repository.PersonRepository;
import com.richard.poise.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

  @Mock private PersonRepository personMockRepository;
  @Mock private ProjectRepository projectMockRepository;

  private ProjectService projectService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    projectService = new ProjectService(projectMockRepository, personMockRepository);
  }

  @Test
  void deleteProject_whenProjectNotFound_returnsFailure() {
    // Arrange
    int nonExistentId = 999;

    when(projectMockRepository.findByID(999)).thenReturn(Optional.empty());

    // Act
    ProjectUpdateResult result = projectService.deleteProject(nonExistentId);

    // Assert
    assertFalse(result.getSuccess());
    assertEquals("Project not found", result.getMessage());

    // Verify
    verify(projectMockRepository).findByID(nonExistentId);
    verify(projectMockRepository, never()).deleteProjectData(nonExistentId);
  }

  @Test
  void deleteProject_whenProjectExists_deletesSuccessfully() {
    int existingProjectID = 1;
    Projects existingProject =
        new Projects(
            1,
            "New House",
            "House",
            "1 Main Street",
            2,
            123,
            12,
            java.sql.Date.valueOf("2025-10-16"),
            1,
            2,
            3,
            4,
            5,
            false,
            null);

    when(projectMockRepository.findByID(existingProjectID))
        .thenReturn(Optional.of(existingProject));
    when(projectMockRepository.deleteProjectData(existingProjectID)).thenReturn(true);
    ProjectUpdateResult result = projectService.deleteProject(existingProjectID);
    assertTrue(result.getSuccess());
    assertEquals("Project deleted successfully", result.getMessage());

    // Verify
    verify(projectMockRepository).findByID(existingProjectID);
    verify(projectMockRepository).deleteProjectData(existingProjectID);
  }

  @Test
  void updateProject_whenProjectNotFound_returnsFailure() {
    int nonExistentID = 999;

    ProjectUpdateRequest request = new ProjectUpdateRequest();
    request.setProjectName("New Name");

    when(projectMockRepository.findByID(nonExistentID)).thenReturn(Optional.empty());
    ProjectUpdateResult result = projectService.updateProject(nonExistentID, request);

    assertFalse(result.getSuccess());
    assertEquals("Project not found", result.getMessage());
    verify(projectMockRepository).findByID(nonExistentID);
    // Verify update was NEVER called (since person wasn't found)
    verify(projectMockRepository, never())
        .updateProjectData(
            anyInt(), // projectID
            anyString(), // projectName
            anyString(), // buildingType
            anyString(), // projectAddress
            anyInt(), // ERF number
            anyDouble(), // totalFee
            anyDouble(), // amount paid to date
            any(), // no anyDate method in Mockito. any() accepts any object
            anyInt(), // architect ID
            anyInt(), // contractor ID
            anyInt(), // customer ID
            anyInt(), // engineer ID
            anyInt(), // manager ID
            anyBoolean(), // project finalised
            any());
  }

  @Test
  void updateProject_whenProjectExists_updatesSuccessfully() {
    // Arrange
    int existingProjectId = 1;

    Projects existingProject =
        new Projects(
            1,
            "Old Name",
            "House",
            "Old Address",
            100,
            50000.0,
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1, // architectID
            2, // contractorID
            3, // customerID
            4, // engineerID
            5, // managerID
            false,
            null);

    ProjectUpdateRequest request = new ProjectUpdateRequest();
    request.setProjectName("New Name"); // Change this
    request.setTotalFee(60000.0); // Change this
    // Note: NOT setting buildingType, projectAddress, etc. - testing partial update!

    when(projectMockRepository.findByID(existingProjectId))
        .thenReturn(Optional.of(existingProject));

    // Tell mock: "when updatePersonData is called, return true (success)"
    when(projectMockRepository.updateProjectData(
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            anyInt(),
            anyDouble(),
            anyDouble(),
            any(),
            anyInt(),
            anyInt(),
            anyInt(),
            anyInt(),
            anyInt(),
            anyBoolean(),
            any()))
        .thenReturn(true);

    ProjectUpdateResult result = projectService.updateProject(existingProjectId, request);

    assertTrue(result.getSuccess());
    assertEquals("Project updated successfully", result.getMessage());

    // Verify the repository methods were called
    verify(projectMockRepository).findByID(existingProjectId);
    verify(projectMockRepository)
        .updateProjectData(
            1,
            "New Name", // updated name
            "House",
            "Old Address",
            100,
            60000.0, // updated total fee
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1, // architectID
            2, // contractorID
            3, // customerID
            4, // engineerID
            5, // managerID
            false,
            null);
  }

  @Test
  void createProject_whenValidData_createsSuccessfully() {
    // Arrange - create the request with the data to create
    ProjectCreateRequest request = new ProjectCreateRequest();
    request.setProjectName("New House");
    request.setBuildingType("House");
    request.setProjectAddress("1 Main Street");
    request.setERFNumber(3);
    request.setTotalFee(50000.0);
    request.setAmountPaidToDate(10000.0);
    request.setProjectDeadline(java.sql.Date.valueOf("2025-12-31"));
    request.setArchitectID(1);
    request.setContractorID(2);
    request.setCustomerID(3);
    request.setEngineerID(4);
    request.setManagerID(5);
    request.setProjectFinalised(false);
    request.setCompletionDate(null);

    // Mock the repository to return a new ID (e.g., 5) when create is called
    when(projectMockRepository.createProjectData(
            "New House",
            "House",
            "1 Main Street",
            3,
            50000.0,
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            false,
            null))
        .thenReturn(5); // ← Returns the new project's ID

    // Act - call the service method
    ProjectUpdateResult result =
        projectService.createProject(request); // Takes ProjectCreateRequest

    // Assert - check it succeeded
    assertTrue(result.getSuccess());
    assertEquals("Project created successfully with ID: 5", result.getMessage());

    // Verify - check the repository was called with the right data
    verify(projectMockRepository)
        .createProjectData(
            "New House",
            "House",
            "1 Main Street",
            3,
            50000.0,
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            false,
            null);
  }

  @Test
  void createProject_whenRepositoryFails_returnsFailure() {
    ProjectCreateRequest request = new ProjectCreateRequest();
    request.setProjectName("New House");
    request.setBuildingType("House");
    request.setProjectAddress("1 Main Street");
    request.setERFNumber(3);
    request.setTotalFee(50000.0);
    request.setAmountPaidToDate(10000.0);
    request.setProjectDeadline(java.sql.Date.valueOf("2025-12-31"));
    request.setArchitectID(1);
    request.setContractorID(2);
    request.setCustomerID(3);
    request.setEngineerID(4);
    request.setManagerID(5);
    request.setProjectFinalised(false);
    request.setCompletionDate(null);

    // Mock the repository to return a new ID (e.g., 5) when create is called
    when(projectMockRepository.createProjectData(
            "New House",
            "House",
            "1 Main Street",
            3,
            50000.0,
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            false,
            null))
        .thenReturn(-1);
    ProjectUpdateResult result = projectService.createProject(request);
    assertFalse(result.getSuccess());
    assertEquals("Failed to create project", result.getMessage());
    verify(projectMockRepository)
        .createProjectData(
            "New House",
            "House",
            "1 Main Street",
            3,
            50000.0,
            10000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            false,
            null);
  }

  @Test
  void finaliseProject_whenProjectNotFound_returnsFailure() {
    // Arrange
    int nonExistentId = 999;
    java.sql.Date finalisedDate = java.sql.Date.valueOf("2025-10-16");

    // Mock: project doesn't exist
    when(projectMockRepository.findByID(nonExistentId)).thenReturn(Optional.empty());

    // Act
    ProjectUpdateResult result = projectService.finaliseProject(nonExistentId, finalisedDate);

    // Assert
    assertFalse(result.getSuccess());
    assertEquals("Project not found", result.getMessage());

    // Verify
    verify(projectMockRepository).findByID(nonExistentId);
    verify(projectMockRepository, never()).finaliseProjectData(anyInt(), any());
  }

  @Test
  void finaliseProject_whenAlreadyFinalised_returnsFailure() {
    // Arrange
    int existingProjectId = 1;
    java.sql.Date finalisedDate = java.sql.Date.valueOf("2025-10-16");

    // Create a project that's finalised
    Projects alreadyFinalisedProject =
        new Projects(
            1,
            "Finished Project",
            "House",
            "123 Main St",
            100,
            50000.0,
            50000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            true,
            java.sql.Date.valueOf("2025-10-01"));

    // Mock: findByID returns the already-finalised project
    when(projectMockRepository.findByID(existingProjectId))
        .thenReturn(Optional.of(alreadyFinalisedProject));

    // Act
    ProjectUpdateResult result = projectService.finaliseProject(existingProjectId, finalisedDate);

    // Assert - should be rejected - can't finalise a project that is already finalised!
    assertFalse(result.getSuccess());
    assertEquals("This project is already finalised", result.getMessage());

    // Verify
    verify(projectMockRepository).findByID(existingProjectId);
    verify(projectMockRepository, never()).finaliseProjectData(anyInt(), any());
  }

  @Test
  void finaliseProject_whenNotFinalised_finalisesSuccessfully() {
    // Arrange
    int existingProjectId = 1;
    java.sql.Date finalisedDate = java.sql.Date.valueOf("2025-10-01");

    // Create a project that's not finalised
    Projects notFinalisedProject =
        new Projects(
            1,
            "In Progress Project",
            "House",
            "123 Main St",
            100,
            50000.0,
            50000.0,
            java.sql.Date.valueOf("2025-12-31"),
            1,
            2,
            3,
            4,
            5,
            false,
            null
            );

    // Mock: findByID returns the not yet finalised project
    when(projectMockRepository.findByID(existingProjectId))
        .thenReturn(Optional.of(notFinalisedProject));

    // Mock: finaliseProjectData succeeds
    when(projectMockRepository.finaliseProjectData(existingProjectId, finalisedDate))
        .thenReturn(true);

    // Act
    ProjectUpdateResult result = projectService.finaliseProject(existingProjectId, finalisedDate);

    // Assert
    assertTrue(result.getSuccess());
    assertEquals("Project finalised successfully", result.getMessage());

    // Verify
    verify(projectMockRepository).findByID(existingProjectId);
    verify(projectMockRepository).finaliseProjectData(existingProjectId, finalisedDate);
  }
}
