package com.richard.poise.service;

import com.richard.poise.model.People;
import com.richard.poise.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

  @Mock private PersonRepository mockRepository;

  private PersonService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new PersonService(mockRepository);
  }

  @Test
  void updatePerson_whenPersonNotFound_returnsFailure() {
    // Arrange - set up the scenario
    int nonExistentId = 999;
    PersonUpdateRequest request = new PersonUpdateRequest();
    request.setPersonName("New Name");

    // Tell the mock: "when someone calls findByID(999), return empty"
    when(mockRepository.findByID(nonExistentId)).thenReturn(Optional.empty());

    // Act - run the method being tested
    PersonUpdateResult result = service.updatePerson(nonExistentId, request);

    // Assert - check it behaved as expected
    assertFalse(result.getSuccess());
    assertEquals("Person not found", result.getMessage());

    // Verify the repository was called correctly
    verify(mockRepository).findByID(nonExistentId);
    // Verify update was NEVER called (since person wasn't found)
    verify(mockRepository, never())
        .updatePersonData(
            anyInt(), // personID
            anyString(), // personName
            anyString(), // phone
            anyString(), // email
            anyString(), // address
            anyString() // role
            );
  }

  @Test
  void updatePerson_whenPersonExists_updatesSuccessfully() {
    // Arrange
    int existingPersonId = 1;

    // Create the existing person that will be "found" in the database
    People existingPerson =
        new People(1, "Old Name", "07700900000", "old@example.com", "Old Address", "architect");

    // Create the update request
    PersonUpdateRequest request = new PersonUpdateRequest();
    request.setPersonName("New Name");
    request.setEmail("new@example.com");
    // Note: we're not setting phone, address, or role - testing partial updates

    // Tell mock: "when findByID(1) is called, return the existing person"
    when(mockRepository.findByID(existingPersonId)).thenReturn(Optional.of(existingPerson));

    // Tell mock: "when updatePersonData is called, return true (success)"
    when(mockRepository.updatePersonData(
            anyInt(), anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(true);

    // Act
    PersonUpdateResult result = service.updatePerson(existingPersonId, request);

    // Assert - check the result
    assertTrue(result.getSuccess());
    assertEquals("Person updated successfully", result.getMessage());

    // Verify the repository methods were called
    verify(mockRepository).findByID(existingPersonId);
    verify(mockRepository)
        .updatePersonData(
            1, // personID should be 1
            "New Name", // name was updated
            "07700900000", // phone unchanged (from original person)
            "new@example.com", // email was updated
            "Old Address", // address unchanged
            "architect" // role unchanged
            );
  }

  @Test
  void deletePerson_whenPersonNotFound_returnsFailure() {
    // Arrange
    int nonExistentId = 999;

    when(mockRepository.findByID(999)).thenReturn(Optional.empty());

    // Act
    PersonUpdateResult result = service.deletePerson(nonExistentId);

    // Assert
    assertFalse(result.getSuccess());
    assertEquals("Person not found", result.getMessage());

    // Verify
    verify(mockRepository).findByID(nonExistentId);
    verify(mockRepository, never()).deletePersonData(nonExistentId);
  }

  @Test
  void deletePerson_whenPersonExists_deletesSuccessfully() {
    // Arrange
    int existingPersonId = 1;

    // Create the person object that will be "found"
    People existingPerson =
        new People(1, "John Smith", "07700900000", "john@example.com", "123 Main St", "customer");

    // Mock findByID to return the person
    when(mockRepository.findByID(existingPersonId))
        .thenReturn(Optional.of(existingPerson)); // ← Return the People object

    // Mock deletePersonData to return true (successful deletion)
    when(mockRepository.deletePersonData(existingPersonId)).thenReturn(true);

    // Act
    PersonUpdateResult result = service.deletePerson(existingPersonId);

    // Assert
    assertTrue(result.getSuccess());
    assertEquals("Person deleted successfully", result.getMessage());

    // Verify
    verify(mockRepository).findByID(existingPersonId);
    verify(mockRepository).deletePersonData(existingPersonId);
  }

  @Test
  void createPerson_whenValidData_createsSuccessfully() {
    // Arrange - create the request with the data to create
    PeopleCreateRequest request = new PeopleCreateRequest();
    request.setPersonName("John Smith");
    request.setPhone("07700900000");
    request.setEmail("john@example.com");
    request.setAddress("123 Main St");
    request.setRole("customer");

    // Mock the repository to return a new ID (e.g., 5) when create is called
    when(mockRepository.createPersonData(
            "John Smith", "07700900000", "john@example.com", "123 Main St", "customer"))
        .thenReturn(5); // ← Returns the new person's ID

    // Act - call the service method
    PersonUpdateResult result = service.createPerson(request); // Takes PeopleCreateRequest

    // Assert - check it succeeded
    assertTrue(result.getSuccess());
    assertEquals("Person created successfully with ID: 5", result.getMessage());

    // Verify - check the repository was called with the right data
    verify(mockRepository)
        .createPersonData(
            "John Smith", "07700900000", "john@example.com", "123 Main St", "customer");
  }

  @Test
  void createPerson_whenRepositoryFails_returnsFailure() {
    PeopleCreateRequest request = new PeopleCreateRequest();
    request.setPersonName("John Smith");
    request.setPhone("07700900000");
    request.setEmail("john@example.com");
    request.setAddress("123 Main St");
    request.setRole("customer");
    when(mockRepository.createPersonData(
            "John Smith", "07700900000", "john@example.com", "123 Main St", "customer"))
        .thenReturn(-1); // returns impossible ID
    PersonUpdateResult result = service.createPerson(request);
    assertFalse(result.getSuccess());
    assertEquals(
        "Failed to create person",
        result.getMessage()); // what the PersonService.createPerson() method should return
    verify(mockRepository)
        .createPersonData(
            "John Smith", "07700900000", "john@example.com", "123 Main St", "customer");
  }

}
