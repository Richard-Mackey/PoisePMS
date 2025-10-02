package com.richard.poise.console;

import com.richard.poise.model.People;
import com.richard.poise.repository.DatabasePersonRepository;
import com.richard.poise.repository.PersonRepository;
import com.richard.poise.service.PeopleCreateRequest;
import com.richard.poise.service.PersonService;
import com.richard.poise.service.PersonUpdateRequest;
import com.richard.poise.service.PersonUpdateResult;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PeopleManager {

  // simple helper method to help with display of people details in searchPeople method
  public static void displayPeopleDetails(People person) {
    System.out.println("Found person:");
    //  Print out the project with details
    System.out.println("=== Personal Details ===");
    System.out.println("Person ID: " + person.getPersonID());
    System.out.println("Person Name: " + person.getPersonName());
    System.out.println("Phone: " + person.getPhone());
    System.out.println("email Address: " + person.getEmail());
    System.out.println("Address: " + person.getAddress());
    System.out.println("Role: " + person.getRole());

    System.out.println(); // Empty line for spacing between people
  }

  /**
   * Provides an interactive search interface for finding people Allows users to search for a person
   * either by ID or name
   *
   * @param scanner used for reading user input
   * @return a person object (if found). Null if not found or user cancels and returns to main menu.
   */
  public static Optional<People> searchPeople(Scanner scanner) {

    int option = 0;
    boolean validOption = false;
    int ID = 0;
    String person_name = null;

    while (!validOption) { // while loop to go through searchPeople menu options
      System.out.println("How would you like to search?");
      // options on how to search for an individual
      System.out.println("1: Search by ID");
      System.out.println("2: Search by person name");
      System.out.println("0: Return to main menu");

      option = scanner.nextInt();
      scanner.nextLine();
      if (option >= 0 && option <= 2) {
        validOption = true;
        if (option == 0) {
          return Optional.empty();
        } else if (option == 1) {
          System.out.print("Enter the ID of the person: ");
          ID = scanner.nextInt();
          scanner.nextLine();

          PersonRepository personRepository = new DatabasePersonRepository();
          Optional<People> foundPerson = personRepository.findByID(ID);

          if (foundPerson.isPresent()) {
            displayPeopleDetails(foundPerson.get());
          } else {
            System.out.println("Person could not be found...");
          }
          return foundPerson;

        } else if (option == 2) {
          System.out.print("Enter the name of the person: ");
          person_name = scanner.nextLine();
          //  Call DatabaseManager with the persons name
          PersonRepository personRepository = new DatabasePersonRepository();
          Optional<People> foundPerson = personRepository.findByName(person_name);
          if (foundPerson.isPresent()) {
            displayPeopleDetails(foundPerson.get());
          } else {
            System.out.println("Person could not be found...");
          }
          return foundPerson;
        }
      } else {
        System.out.println("Invalid person search option. Please try again.");
      }
    }
    return Optional.empty();
  }

  /**
   * Provides a way to update information related to a person User can choose any field to update.
   * Only one field is updated per method call.
   *
   * @param scanner used for reading user input
   */
  public static void updatePeople(Scanner scanner) {

    // Call search method and capture the result
    People personToUpdate = searchPeople(scanner).orElse(null);
    PersonUpdateRequest request = new PersonUpdateRequest();
    int option = 0;

    if (personToUpdate != null) {

      boolean validOption = false;
      while (!validOption) {
        // Person was found. Display update menu.
        System.out.println("Which field would you like to update?");
        System.out.println("1: Person name");
        System.out.println("2: Phone number");
        System.out.println("3: Email");
        System.out.println("4: Person's address");
        System.out.println("5: Role");
        System.out.println("6: Save changes");
        System.out.println("0: Return to main menu");
        option = scanner.nextInt();
        scanner.nextLine();

        if (option >= 0 && option <= 6) {

          if (option == 0) {
            return;
          } else if (option == 1) {
            System.out.println("Current person's name: " + personToUpdate.getPersonName());
            System.out.println("Enter the new person's name (or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) { // Only update if they entered something
              request.setPersonName(input);

              // updatePersonName already has the original value if they didn't change it
            }
          } else if (option == 2) {
            System.out.println("Current phone number: " + personToUpdate.getPhone());
            System.out.println("Enter the new phone number (or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setPhone(input);
            }

          } else if (option == 3) {
            System.out.println("Current email address: " + personToUpdate.getEmail());
            boolean acceptedInput = false;
            while (!acceptedInput) {
              System.out.print(
                  "Please enter the person's email address (or press enter to skip): ");
              String updateEmail = scanner.nextLine();

              // Basic email validation
              if (updateEmail.contains("@")
                  && updateEmail.indexOf("@") == updateEmail.lastIndexOf("@")
                  && updateEmail.indexOf("@") > 0
                  && updateEmail.indexOf("@") < updateEmail.length() - 1
                  && updateEmail.substring(updateEmail.indexOf("@")).contains(".")
                  && updateEmail.length() >= 5) {
                request.setEmail(updateEmail);
                acceptedInput = true;

              } else if (updateEmail == null || updateEmail.trim().isEmpty()) {
                acceptedInput = true;

                System.out.println(
                    "Invalid email format. Please enter a valid email (e.g., user@example.com)");
              }
            }
          } else if (option == 4) {
            System.out.println("Current person's address: " + personToUpdate.getAddress());
            System.out.println("Enter the new person's address (0 to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setAddress(input);
            }
          } else if (option == 5) {
            System.out.println("Current role: " + personToUpdate.getRole());
            System.out.println("Enter the new role (0 to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setRole(input);
            }
          } else if (option == 6) {
            PersonService service = new PersonService(new DatabasePersonRepository());
            PersonUpdateResult result = service.updatePerson(personToUpdate.getPersonID(), request);

            if (result.getSuccess()) {
              System.out.println(result.getMessage());
            } else {
              System.out.println("Error: " + result.getMessage());
            }
            validOption = true;
          }
        }
      }
    }
  }

  /**
   * Helper method to display the information of a person according to their role. Used when
   * projects are being created ProjectsManager.createProject().
   *
   * @param scanner used for reading user input
   * @return a person object (if found). Null if not found or user cancels and returns to main menu.
   */
  public static Optional<People> displayPeopleByRole(Scanner scanner, String role) {
    // Call DatabaseManager to get the people list
    PersonRepository personRepository = new DatabasePersonRepository();
    List<People> peopleList = personRepository.findByRole(role);

    // Check if the list is empty
    if (peopleList.isEmpty()) {
      System.out.println(
          "There are no " + role + "s currently in the database. Press enter to skip:");
      scanner.nextLine(); // Wait for user to press enter
      return Optional.empty();
    }
    // Display the list to the user
    System.out.println("Available " + role + "s:");
    System.out.println();

    for (People person : peopleList) {
      System.out.println(
          "=== " + role.substring(0, 1).toUpperCase() + role.substring(1) + " Details ===");
      System.out.println("Person ID: " + person.getPersonID());
      System.out.println("Name: " + person.getPersonName());
      System.out.println("Role: " + person.getRole());
      System.out.println(); // Empty line for spacing
    }

    String optionNumber;
    boolean validOption = false;
    while (!validOption) {
      System.out.println(
          "Please enter the ID number of the person you are searching for, or press enter to skip: ");
      optionNumber = scanner.nextLine();
      String cleanOption = optionNumber.replaceAll("[\\s-()]", "");
      if (cleanOption.matches("0") || cleanOption.isEmpty()) {
        return Optional.empty();
      }
      if (cleanOption.matches("\\d+")) {
        validOption = true;
        int selectedID = Integer.parseInt(cleanOption);

        for (People person : peopleList) {
          if (selectedID == person.getPersonID()) {
            return Optional.of(person);
          }
        }
        System.out.println("Person not found");
        validOption = false;
      }
    }
    return Optional.empty();
  }

  /**
   * Provides a way to add a new person to the database User inputs details through a looped menu.
   * Some details are mandatory, some optional.
   *
   * @param scanner used for reading user input
   */
  public static void createPerson(Scanner scanner) {
    PeopleCreateRequest request = new PeopleCreateRequest();

    boolean validPerson = false;
    while (!validPerson) {
      System.out.println("To add a new person to the database, we need to collect a few details");
      System.out.println("Please enter their name (required):");
      String personName = scanner.nextLine();
      // stops user pressing enter without entering persons name
      if (personName == null || personName.trim().isEmpty()) {
        System.out.println("No name inputted. Please enter the person's name");
      } else {
        request.setPersonName(personName);
        validPerson = true;
      }
    }

    boolean validPhone = false;
    while (!validPhone) {
      System.out.println("Please enter their contact number (required):");
      String phone = scanner.nextLine();
      if (phone == null || phone.trim().isEmpty()) {
        System.out.println("No contact number inputted. Please enter the person's contact number");
      } else {
        request.setPhone(phone);
        validPhone = true;
      }
    }

    boolean acceptedInput = false;
    while (!acceptedInput) {
      System.out.print("Please enter the person's email address (or press enter to skip): ");
      String email = scanner.nextLine();

      // Basic email validation
      if (email.contains("@")
          && email.indexOf("@") == email.lastIndexOf("@")
          && email.indexOf("@") > 0
          && email.indexOf("@") < email.length() - 1
          && email.substring(email.indexOf("@")).contains(".")
          && email.length() >= 5) {
        request.setEmail(email);
        acceptedInput = true;
      } else if (email == null || email.trim().isEmpty()) {
        acceptedInput = true;
      } else {
        System.out.println(
            "Invalid email format. Please enter a valid email (e.g., user@example.com)");
      }
    }

    System.out.println("Please enter their address (or press enter to skip):");
    String personAddress = scanner.nextLine();
    request.setAddress(personAddress);

    boolean validRole = false;
    while (!validRole) {
      System.out.println("Please enter their role (required):");
      String role = scanner.nextLine();
      if (role == null || role.trim().isEmpty()) {
        System.out.println("No role inputted. Please enter the person's role");
      } else {
        request.setRole(role);
        validRole = true;
      }

      PersonService service = new PersonService(new DatabasePersonRepository());
      PersonUpdateResult result = service.createPerson(request);

      if (result.getSuccess()) {
        System.out.println(result.getMessage());
      } else {
        System.out.println("Error: " + result.getMessage());
      }
    }
  }

  /**
   * Provides a way to remove a person from the database User find person by name or ID. User is
   * prompted to confirm before delete operation is executed. User is prompted before deleting
   * people that are associated with projects User cannot delete customer associated with projects
   * as customer is a required field
   *
   * @param scanner used for reading user input
   */
  public static void deletePerson(Scanner scanner) {
    People personToDelete = searchPeople(scanner).orElse(null);

    if (personToDelete != null) {
      PersonRepository personRepository = new DatabasePersonRepository();

      // Check if they're a customer in projects
      if (personToDelete.getRole().equalsIgnoreCase("customer")
          && personRepository.isCustomerInProjects(personToDelete.getPersonID())) {
        System.out.println("Cannot delete this customer: They are assigned to active projects.");
        System.out.println("Please reassign or complete their projects before deletion.");
        return;
      }

      // Check for linked projects
      List<String> linkedProjects =
          personRepository.getProjectsLinkedToPerson(personToDelete.getPersonID());
      if (!linkedProjects.isEmpty()) {
        System.out.println(
            "Warning: This "
                + personToDelete.getRole()
                + " is associated with the following projects:");
        linkedProjects.forEach(projectName -> System.out.println("- " + projectName));
        System.out.println("Deleting them will remove their assignment from these projects.");
      }
      // Confirm deletion
      boolean validInput = false;
      while (!validInput) {
        System.out.println(
            "Please indicate whether you want to remove this person from the database (yes/no):");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("yes") || input.equals("y")) {
          validInput = true;
          PersonService service = new PersonService(new DatabasePersonRepository());
          PersonUpdateResult result = service.deletePerson(personToDelete.getPersonID());

          if (result.getSuccess()) {
            System.out.println(result.getMessage());
          } else {
            System.out.println("Error: " + result.getMessage());
          }
        } else if (input.equals("no") || input.equals("n")) {
          validInput = true;
        }
      }
    }
  }
}
