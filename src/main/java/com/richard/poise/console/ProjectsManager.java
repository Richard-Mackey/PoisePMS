package com.richard.poise.console;



import com.richard.poise.model.People;
import com.richard.poise.model.Projects;
import com.richard.poise.repository.DatabasePersonRepository;
import com.richard.poise.repository.DatabaseProjectRepository;
import com.richard.poise.repository.ProjectRepository;
import com.richard.poise.service.ProjectCreateRequest;
import com.richard.poise.service.ProjectService;
import com.richard.poise.service.ProjectUpdateRequest;
import com.richard.poise.service.ProjectUpdateResult;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// class to manage user inputs, and send information to DatabaseManager for processing requests
public class ProjectsManager {

  // simple helper method to help with display of project details in searchProjects method
  public static void displayProjectDetails(Projects Project) {
    System.out.println("Found project:");
    System.out.println("=== Project Details ===");
    System.out.println("Project ID: " + Project.getProjectID());
    System.out.println("Project Name: " + Project.getProjectName());
    System.out.println("Building Type: " + Project.getBuildingType());
    System.out.println("Project Address: " + Project.getProjectAddress());
    System.out.println("Total fee: " + Project.getFormattedTotalFee());
    System.out.println("Amount paid to date: " + Project.getFormattedAmountPaidToDate());
    System.out.println("Project deadline: " + Project.getProjectDeadline());
    System.out.println("Architect ID: " + Project.getArchitectID());
    System.out.println("Contractor ID: " + Project.getContractorID());
    System.out.println("Customer ID: " + Project.getCustomerID());
    System.out.println("Engineer ID: " + Project.getEngineerID());
    System.out.println("Manager ID: " + Project.getManagerID());
    System.out.println("Project Finalised: " + (Project.isProjectFinalised() ? "Yes" : "No"));
    System.out.println("Completion date: " + Project.getCompletionDate());
    System.out.println(); // Empty line for spacing between projects
  }

  // method providing a summary of all projects in the database, used in MainMenu, option 1

  public static void displayAllProjectsSummary() {
    DatabaseProjectRepository databaseProjectRepository = new DatabaseProjectRepository();
    // returns an ArrayList from the SQL query
    List<Projects> projectsList = databaseProjectRepository.getAllProjectsSummary();

    System.out.println("Here is a summary list of all projects:");
    System.out.println();

    for (Projects project : projectsList) {
      // prints out a summary of each project as a string
      System.out.println("=== Project Details ===");
      System.out.println("Project ID: " + project.getProjectID());
      System.out.println("Project Name: " + project.getProjectName());
      System.out.println("Project Finalised: " + (project.isProjectFinalised() ? "Yes" : "No"));
      System.out.println(); // Empty line for spacing between projects
    }
  }

  /**
   * Provides an interactive search interface for finding projects Allows users to search for a
   * project either by project ID or name
   *
   * @param scanner used for reading user input
   * @return a projects object (if found). Null if not found or user cancels and returns to main
   *     menu.
   */
  public static Optional<Projects> searchProjects(Scanner scanner) {
    int option = 0;
    boolean validOption = false;
    int projectID = 0;
    String projectName = null;

    while (!validOption) {
      System.out.println("How would you like to search?");
      System.out.println("1: Search by ID");
      System.out.println("2: Search by project name");
      System.out.println("0: Return to main menu");

      option = scanner.nextInt();
      scanner.nextLine();

      if (option >= 0 && option <= 2) {
        validOption = true;
        if (option == 0) {
          return Optional.empty(); // Fixed!
        } else if (option == 1) {
          System.out.print("Enter the ID of the project: ");
          projectID = scanner.nextInt();
          scanner.nextLine();

          ProjectRepository projectRepository = new DatabaseProjectRepository();
          Optional<Projects> foundProject = projectRepository.findByID(projectID);

          if (foundProject.isPresent()) {
            displayProjectDetails(foundProject.get());
          } else {
            System.out.println("Project could not be found...");
          }
          return foundProject;

        } else if (option == 2) {
          System.out.print("Enter the name of the project: ");
          projectName = scanner.nextLine();

          ProjectRepository projectRepository = new DatabaseProjectRepository();
          Optional<Projects> foundProject = projectRepository.findByName(projectName);

          if (foundProject.isPresent()) {
            displayProjectDetails(foundProject.get());
          } else {
            System.out.println("Project could not be found...");
          }
          return foundProject;
        }
      } else {
        System.out.println("Invalid project search option. Please try again.");
      }
    }

    return Optional.empty();
  }

  /**
   * Provides a way to update individual project information User can choose any field to update.
   * Only one field is updated per method call.
   *
   * @param scanner used for reading user input
   */
  public static void updateProject(Scanner scanner) {

    // Call search method and capture the result
    Projects projectToUpdate = searchProjects(scanner).orElse(null);

    int option = 0;

    ProjectUpdateRequest request = new ProjectUpdateRequest();

    if (projectToUpdate != null) {
      boolean validOption = false;
      while (!validOption) {
        // Project was found, display update menu
        System.out.println("Which field would you like to update?");
        System.out.println("1: Project name");
        System.out.println("2: Building type");
        System.out.println("3: Project address");
        System.out.println("4: ERF number");
        System.out.println("5: Total fee");
        System.out.println("6: Amount paid to date");
        System.out.println("7: Project deadline");
        System.out.println("8: Architect ID");
        System.out.println("9: Contractor ID");
        System.out.println("10: Customer ID");
        System.out.println("11: Engineer ID");
        System.out.println("12: Manager ID");
        System.out.println("13: Project finalised");
        System.out.println("14: Completion date");
        System.out.println("15: Save changes");
        System.out.println("0: Return to main menu");
        option = scanner.nextInt();
        scanner.nextLine();

        if (option >= 0 && option <= 15) {
          validOption = true;
          if (option == 0) {
            return;
          } else if (option == 1) {
            System.out.println("Current project name: " + projectToUpdate.getProjectName());
            System.out.println("Enter the new project name (or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setProjectName(input);
            }
          } else if (option == 2) {
            System.out.println("Current building type: " + projectToUpdate.getBuildingType());
            System.out.println("Enter the new building type (or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setBuildingType(input);
            }

          } else if (option == 3) {
            System.out.println("Current project address: " + projectToUpdate.getProjectAddress());
            System.out.println("Enter the new project address (or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              request.setProjectAddress(input);
            }

          } else if (option == 4) {
            System.out.println("Current ERF number: " + projectToUpdate.getERFNumber());
            System.out.println("Enter the new ERF number (0 to keep current):");
            int input = scanner.nextInt();
            // consume newline after entering int
            scanner.nextLine();
            // Use 0 to mean "no change"
            if (input != 0) {
              request.setERFNumber(input);
            }
          } else if (option == 5) {
            System.out.println("Current total fee: " + projectToUpdate.getTotalFee());
            System.out.println("Enter the new total fee (0 to keep current):");
            double input = scanner.nextDouble();
            scanner.nextLine();
            if (input != 0) {
              request.setTotalFee(input);
            }
          } else if (option == 6) {
            System.out.println(
                "Current amount paid to date: " + projectToUpdate.getAmountPaidToDate());
            System.out.println("Enter the new amount paid to date (0 to keep current):");
            double input = scanner.nextDouble();
            scanner.nextLine();
            if (input != 0) {
              request.setAmountPaidToDate(input);
            }
          } else if (option == 7) {
            System.out.println(
                "Enter new deadline (YYYY-MM-DD format, or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              try {
                java.sql.Date date = java.sql.Date.valueOf(input);
                request.setProjectDeadline(date);
                System.out.println("Deadline updated successfully!");
              } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Keeping original deadline.");
              }
            }

          } else if (option == 8) {
            System.out.println("Current architect ID: " + projectToUpdate.getArchitectID());
            System.out.println("Enter the new architect ID (or press Enter to keep current):");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input != 0) {
              request.setArchitectID(input);
            }
          } else if (option == 9) {
            System.out.println("Current contractor ID: " + projectToUpdate.getContractorID());
            System.out.println("Enter the new contractor ID (or press Enter to keep current):");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input != 0) {
              request.setContractorID(input);
            }
          } else if (option == 10) {
            System.out.println("Current customer ID: " + projectToUpdate.getCustomerID());
            System.out.println("Enter the new customer ID (or press Enter to keep current):");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input != 0) {
              request.setCustomerID(input);
            }
          } else if (option == 11) {
            System.out.println("Current engineer ID: " + projectToUpdate.getEngineerID());
            System.out.println("Enter the new engineer ID (or press Enter to keep current):");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input != 0) {
              request.setEngineerID(input);
            }
          } else if (option == 12) {
            System.out.println("Current manager ID: " + projectToUpdate.getManagerID());
            System.out.println("Enter the new manager ID (or press Enter to keep current):");
            int input = scanner.nextInt();
            scanner.nextLine();
            if (input != 0) {
              request.setManagerID(input);
            }
          } else if (option == 13) {
            System.out.println(
                "Current status: "
                    + (projectToUpdate.isProjectFinalised() ? "Finalised" : "In Progress"));
            System.out.println("Choose new status:");
            System.out.println("1. Mark as finalised");
            System.out.println("2. Mark as in progress");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
              request.setProjectFinalised(true);
            } else if (choice == 2) {
              request.setProjectFinalised(false);
            } else {
              System.out.println("Invalid choice.");
            }
          } else if (option == 14) {
            System.out.println(
                "Enter new completion date (YYYY-MM-DD format, or press Enter to keep current):");
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) {
              try {
                java.sql.Date date = java.sql.Date.valueOf(input);
                request.setCompletionDate(date);
                System.out.println("Completion date updated successfully!");
              } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Keeping original deadline.");
              }
            }
          } else if (option == 15) {
            ProjectService service =
                new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());
            ProjectUpdateResult result =
                service.updateProject(projectToUpdate.getProjectID(), request);

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
   * Provides a way to create a new project User inputs details through a looped menu. Some details
   * are mandatory, some optional.
   *
   * @param scanner used for reading user input
   */
  public static void createProject(Scanner scanner) {

      ProjectCreateRequest request = new ProjectCreateRequest();
String buildingType = "";
    boolean validOption = false;
    while (!validOption) {
      // sub menu options
      System.out.println("To create a new project we need to collect a few details");
      System.out.println(
          "Please enter a name for your project (press enter if you don't have one):");
     String projectName = scanner.nextLine();
        request.setProjectName(projectName);

      boolean validBuilding = false;
      while (!validBuilding) {
        System.out.println("Please enter a building type (required):");
         buildingType = scanner.nextLine();
        request.setBuildingType(buildingType);
        // stops user pressing enter without entering building type (needed for building project
        // name if one not provided)
        if (buildingType == null || buildingType.trim().isEmpty()) {
          System.out.println("No building type inputted. Please enter a building type");
        } else {
          validBuilding = true;
        }
      }

      boolean validCustomer = false;
      while (!validCustomer) {
        System.out.println("Please enter a customer (required):");
        People selectedCustomer =
            PeopleManager.displayPeopleByRole(scanner, "customer").orElse(null);

        if (selectedCustomer == null) {
          System.out.println("No customer selected. Please select a customer");

        } else {
          // This only executes when selectedCustomer is not null
            int customerID = 0;
            customerID = selectedCustomer.getPersonID();
            request.setCustomerID(customerID);

          if (projectName == null || projectName.trim().isEmpty()) {
            projectName = buildingType + " " + selectedCustomer.getPersonName();
          } else {
            projectName = projectName;
          }
            request.setProjectName(projectName);
          validCustomer = true; // Only set to true when a valid customer is entered
        }
      }
      System.out.println("Please enter the address for your project (press enter to skip):");
      String projectAddress = scanner.nextLine();
request.setProjectAddress(projectAddress);
      String tempERFNumber;
      boolean validERF = false;
      while (!validERF) {
        System.out.println("Please enter the ERF number of your project (press enter to skip):");
        tempERFNumber = scanner.nextLine();

        if (tempERFNumber.trim().isEmpty()) {
          // User chose to skip
 request.setERFNumber(0);
          validERF = true;
        } else {
          try {
            String cleanOption = tempERFNumber.replaceAll("[\\s-()]", "");
          int  ERFNumber = Integer.parseInt(cleanOption);
            validERF = true;
              request.setERFNumber(ERFNumber);
          } catch (NumberFormatException e) {
            System.out.println(
                "Invalid number format. Please enter a valid ERF number or press Enter to skip.");
          }
        }
      }

      String tempTotalFee;
      boolean validTotalFee = false;
      while (!validTotalFee) {
        System.out.println("Please enter the total fee for your project (press enter to skip):");
        tempTotalFee = scanner.nextLine();

        if (tempTotalFee.trim().isEmpty()) {
          // User chose to skip
          double totalFee = 0;
          validTotalFee = true;
        } else {
          try {
            String cleanOption = tempTotalFee.replaceAll("[\\s-()]", "");
           double totalFee = Double.parseDouble(cleanOption);
              request.setTotalFee(totalFee);
            validTotalFee = true; // Success!
          } catch (NumberFormatException e) {
            System.out.println(
                "Invalid number format. Please enter a valid total fee or press Enter to skip.");
            // Loop continues, asks again
          }
        }
      }

      String tempAmountPaidToDate;
      boolean validAmountPaidToDate = false;

      while (!validAmountPaidToDate) {
        System.out.println(
            "Please enter the total amount paid to date for your project (press enter to skip):");
        tempAmountPaidToDate = scanner.nextLine();

        if (tempAmountPaidToDate.trim().isEmpty()) {
          // User chose to skip
         double amountPaidToDate = 0;
          validAmountPaidToDate = true;

        } else {
          try {
            String cleanOption = tempAmountPaidToDate.replaceAll("[\\s-()]", "");
            double amountPaidToDate = Double.parseDouble(cleanOption);
              request.setAmountPaidToDate(amountPaidToDate);
            validAmountPaidToDate = true;
          } catch (NumberFormatException e) {
            System.out.println(
                "Invalid number format. Please enter a valid amount paid to date or press Enter to skip.");
            // Loop continues, asks again
          }
        }
      }

      boolean validDeadline = false;
      while (!validDeadline) {
        System.out.println("Enter project deadline (YYYY-MM-DD format, or press Enter to skip):");
        String deadlineInput = scanner.nextLine().trim();

        if (deadlineInput.isEmpty()) {
          // User chose to skip
          validDeadline = true;
        } else {
          try {
            Date projectDeadline = java.sql.Date.valueOf(deadlineInput);
            validDeadline = true;
            System.out.println("Deadline set successfully!");
              request.setProjectDeadline(projectDeadline);
          } catch (IllegalArgumentException e) { // date checker
            System.out.println(
                "Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-12-25) or press Enter to skip.");
          }
        }
      }
      System.out.println("Please enter an architect:");
      People selectedArchitect =
          PeopleManager.displayPeopleByRole(scanner, "architect").orElse(null);
      int architectID = (selectedArchitect != null) ? selectedArchitect.getPersonID() : 0;
        request.setArchitectID(architectID);

      System.out.println("Please enter a contractor:");
      People selectedContractor =
          PeopleManager.displayPeopleByRole(scanner, "contractor").orElse(null);
      int contractorID = (selectedContractor != null) ? selectedContractor.getPersonID() : 0;
        request.setContractorID(contractorID);

      System.out.println("Please enter a engineer:");
      People selectedEngineer = PeopleManager.displayPeopleByRole(scanner, "engineer").orElse(null);
      int engineerID = (selectedEngineer != null) ? selectedEngineer.getPersonID() : 0;
        request.setEngineerID(engineerID);

      System.out.println("Please enter a manager:");
      People selectedManager = PeopleManager.displayPeopleByRole(scanner, "manager").orElse(null);
      int managerID = (selectedManager != null) ? selectedManager.getPersonID() : 0;
        request.setManagerID(managerID);

      boolean validFinalised = false;
      while (!validFinalised) {
        System.out.println("Please indicate whether the project is finalised (yes/no):");
        // handles case sensitivity
        String finalisedInput = scanner.nextLine().trim().toLowerCase();
        // accepts 'yes' or 'y'
        if (finalisedInput.equals("yes")
            || finalisedInput.equals("y")
            || finalisedInput.equals("true")) {
          boolean projectFinalised = true;
            request.setProjectFinalised(projectFinalised);
          validFinalised = true;
        } else if (finalisedInput.equals("no")
            || finalisedInput.equals("n")
            || finalisedInput.equals("false")) {
          boolean projectFinalised = false;
            request.setProjectFinalised(projectFinalised);
          validFinalised = true;
        } else {
          System.out.println("Invalid input. Please enter 'yes' or 'no'");
        }
      }

      boolean validCompletionDate = false;
      while (!validCompletionDate) {
        System.out.println(
            "Enter project completion date (YYYY-MM-DD format, or press Enter to skip):");
        String completionDateInput = scanner.nextLine().trim();

        if (completionDateInput.isEmpty()) {
          // User chose to skip
          validCompletionDate = true;
        } else {
          try {
           Date completionDate = java.sql.Date.valueOf(completionDateInput);
            validCompletionDate = true;
              request.setCompletionDate(completionDate);
            System.out.println("Completion date set successfully!");
          } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid date format. Please use YYYY-MM-DD format (e.g., 2024-12-25) or press Enter to skip.");
          }
        }
      }

      validOption = true;
        ProjectService service = new ProjectService(
                new DatabaseProjectRepository(),
                new DatabasePersonRepository()
        );
        ProjectUpdateResult result = service.createProject(request);

        if (result.getSuccess()) {
            System.out.println(result.getMessage());
        } else {
            System.out.println("Error: " + result.getMessage());
        }
    }
  }

  /**
   * Provides a way to delete an existing project User find project by name or ID. User is prompted
   * to confirm before delete operation is executed.
   *
   * @param scanner used for reading user input
   */
  public static void deleteProject(Scanner scanner) {
    // Call search method and capture the result
    Projects projectToDelete = searchProjects(scanner).orElse(null);
    boolean validFinalised = false;
    if (projectToDelete != null) {
      while (!validFinalised) {
        System.out.println("Please indicate whether you want to delete this project (yes/no):");
        // handles case sensitivity
        String deletedProjectInput = scanner.nextLine().trim().toLowerCase();
        // accepts 'yes' or 'y'
        if (deletedProjectInput.equals("yes")
            || deletedProjectInput.equals("y")
            || deletedProjectInput.equals("true")) {
          ProjectService service =
              new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());
          ProjectUpdateResult result = service.deleteProject(projectToDelete.getProjectID());
          if (result.getSuccess()) {
            System.out.println(result.getMessage());
          } else {
            System.out.println("Error: " + result.getMessage());
          }
          validFinalised = true; // Exit the loop after delete attempt

        } else if (deletedProjectInput.equals("no")
            || deletedProjectInput.equals("n")
            || deletedProjectInput.equals("false")) {
          validFinalised = true;
        } else {
          System.out.println("Invalid input. Please enter 'yes' or 'no'");
        }
      }
    }
  }

  /**
   * Provides a way to finalise an existing project User find project by name or ID. Search is
   * performed to check whether the project is already finalised. User is prompted for a finalised
   * date, with the option of pressing enter for today's date.
   *
   * @param scanner used for reading user input
   */
  public static void finaliseProject(Scanner scanner) {
    // Use searchProjects to search for project
    Projects projectToFinalise = searchProjects(scanner).orElse(null);
    // if searchProjects returns a project

    if (projectToFinalise != null) {
      java.sql.Date finalisedDate = null;
      boolean validFinalised = false;
      while (!validFinalised) {

        System.out.println("Do you want to finalise this project? (Enter yes/no):");
        // handles case sensitivity
        String finalisedProjectInput = scanner.nextLine().trim().toLowerCase();
        // accepts 'yes' or 'y'
        if (finalisedProjectInput.equals("yes")
            || finalisedProjectInput.equals("y")
            || finalisedProjectInput.equals("true")) {
          boolean validFinalisedDate = false;
          while (!validFinalisedDate) {
            System.out.println(
                "Enter date the date the project was finalised (YYYY-MM-DD format, or press enter for today's date):");
            String completionDateInput = scanner.nextLine().trim();

            if (completionDateInput.isEmpty()) {
              // User chose to skip
              finalisedDate = Date.valueOf(LocalDate.now());

              validFinalisedDate = true;
            } else {
              try {
                finalisedDate = java.sql.Date.valueOf(completionDateInput);
                validFinalisedDate = true;
                System.out.println("Completion date set successfully!");
              } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format...");
              }
            }
          }
          validFinalised = true;
          ProjectService service =
              new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());
          ProjectUpdateResult result =
              service.finaliseProject(projectToFinalise.getProjectID(), finalisedDate);

          if (result.getSuccess()) {
            System.out.println(result.getMessage());
          } else {
            System.out.println("Error: " + result.getMessage());
          }

        } else if (finalisedProjectInput.equals("no")
            || finalisedProjectInput.equals("n")
            || finalisedProjectInput.equals("false")) {

          validFinalised = true;
        } else {
          System.out.println("Invalid input. Please enter 'yes' or 'no'");
        }
      }
    }
  }

  // simple method calling getIncompleteProjects from DatabaseManager for display
  public static void showIncompleteProjects() {

    ProjectRepository projectRepository = new DatabaseProjectRepository();
    projectRepository.getIncompleteProjects();
  }

  // simple method calling getOverdueProjects from DatabaseManager for display
  public static void showOverdueProjects() {
    ProjectRepository projectRepository = new DatabaseProjectRepository();
    projectRepository.getOverdueProjects();
  }
}
