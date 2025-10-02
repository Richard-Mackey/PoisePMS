package com.richard.poise.service;

import com.richard.poise.model.People;
import com.richard.poise.repository.PersonRepository;

import java.util.Optional;

/**
 * Service layer for person-related business logic. Coordinates between controllers and repository
 * layer. Handles data validation and transformation for person operations.
 */
public class PersonService {

  private PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {

    this.personRepository = personRepository;
  }

  /**
   * Updates an existing person's details. Only updates fields that are provided (non-null) in the
   * request.
   *
   * @param personID the ID of the person to update
   * @param request contains the fields to update
   * @return PersonUpdateResult indicating success/failure with message
   */
  public PersonUpdateResult updatePerson(int personID, PersonUpdateRequest request) {
    Optional<People> personOptional = personRepository.findByID(personID);
    if (personOptional.isEmpty()) {
      return new PersonUpdateResult(false, "Person not found");
    }
    // Only update non-null fields
    People foundPerson = personOptional.get();
    if (request.getPersonName() != null) {
      foundPerson.setPersonName(request.getPersonName());
    }
    if (request.getPhone() != null) {
      foundPerson.setPhone(request.getPhone());
    }
    if (request.getEmail() != null) {
      foundPerson.setEmail(request.getEmail());
    }
    if (request.getAddress() != null) {
      foundPerson.setAddress(request.getAddress());
    }
    if (request.getRole() != null) {
      foundPerson.setRole(request.getRole());
    }
    boolean success =
        personRepository.updatePersonData(
            foundPerson.getPersonID(),
            foundPerson.getPersonName(),
            foundPerson.getPhone(),
            foundPerson.getEmail(),
            foundPerson.getAddress(),
            foundPerson.getRole());

    if (success) {
      return new PersonUpdateResult(true, "Person updated successfully");
    } else {
      return new PersonUpdateResult(false, "Failed to update person");
    }
  }

  /**
   * Deletes a person from the database.
   *
   * @param personID the ID of the person to delete
   * @return PersonUpdateResult indicating success/failure with message
   */
  public PersonUpdateResult deletePerson(int personID) {
    Optional<People> personOptional = personRepository.findByID(personID);
    if (personOptional.isEmpty()) {
      return new PersonUpdateResult(false, "Person not found");
    }
    boolean success = personRepository.deletePersonData(personID);
    if (success) {
      return new PersonUpdateResult(true, "Person deleted successfully");
    } else {
      return new PersonUpdateResult(false, "Failed to delete person");
    }
  }

  /**
   * Creates a new person in the database.
   *
   * @param request contains all required person information
   * @return PersonUpdateResult with success status and generated ID or error message
   */
  public PersonUpdateResult createPerson(PeopleCreateRequest request) {
    int newPersonID =
        personRepository.createPersonData(
            request.getPersonName(),
            request.getPhone(),
            request.getEmail(),
            request.getAddress(),
            request.getRole());

    if (newPersonID > 0) {
      return new PersonUpdateResult(true, "Person created successfully with ID: " + newPersonID);
    } else {
      return new PersonUpdateResult(false, "Failed to create person");
    }
  }
}
