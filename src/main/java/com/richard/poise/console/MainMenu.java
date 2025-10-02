package com.richard.poise.console;

import java.util.Scanner;

public class MainMenu {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) { // while loop to handle options input
      String userChoice = getUserOption(scanner);

      switch (userChoice) { // handles options presented to user
        case "1":
          ProjectsManager.displayAllProjectsSummary();
          break;
        case "2":
          handleProjectManagement(scanner);
          break;
        case "3":
          handleProjectStatus(scanner);
          break;
        case "4":
          ProjectsManager.searchProjects(scanner);
          break;
        case "5":
          PeopleManager.searchPeople(scanner);
          break;
        case "6":
          handlePeopleManagement(scanner);
          break;

        case "0":
          // breaks out of loop if user selects option 0, exit
          running = false;
          System.out.println("Goodbye!");
          break;
        default:
          System.out.println("Please enter a valid option (0-4)");
      }
    }
    scanner.close();
  }

  // console menu to allow user to make requests
  private static String getUserOption(Scanner scanner) {
    String optionNumber;
    boolean validOption = false;
    while (!validOption) {
      // main options menu
      System.out.println("Main menu: Please enter an option from the following: ");
      System.out.println("Option 1: Summary overview: View summary details of all projects");
      System.out.println("Option 2: Project Management: Create, update and delete projects");
      System.out.println(
          "Option 3: Project Status Update: Finalise projects, search projects by status");
      System.out.println(
          "Option 4: Search and filter projects by ID number or name for detailed project information");
      System.out.println("Option 5: Look up people by ID number or name");
      System.out.println("Option 6: Add, update or delete people from the database");
      System.out.println("Option 0: Exit");
      optionNumber = scanner.nextLine();
      // Remove spaces and dashes for option input validation
      String cleanOption = optionNumber.replaceAll("[\\s-()]", "");
      if (cleanOption.matches("[0-6]")) {
        validOption = true;
        return optionNumber; // Return the original format
      } else {
        // message if option number not valid
        System.out.println("Invalid option number. Please enter a number between 0-6");
      }
    }
    return "";
  }

  // secondary menu for projects
  private static void handleProjectManagement(Scanner scanner) {
    String optionNumber;
    boolean validOption = false;
    while (!validOption) {
      // sub menu options
      System.out.println("Project management menu: Please enter an option from the following: ");
      System.out.println("Option 1: Create a new project");
      System.out.println("Option 2: Update a project");
      System.out.println("Option 3: Delete a project");
      System.out.println("Option 0: Return to main menu");
      optionNumber = scanner.nextLine();
      // Remove spaces and dashes for option input validation
      String cleanOption = optionNumber.replaceAll("[\\s-()]", "");
      if (cleanOption.matches("[0-3]")) {
        validOption = true;
        switch (cleanOption) {
          case "1":
            ProjectsManager.createProject(scanner);
            break;
          case "2":
            ProjectsManager.updateProject(scanner);
            break;
          case "3":
            ProjectsManager.deleteProject(scanner);
            break;
          case "0":
            // Break returns to main menu
            break;
        }
      } else {
        // message if option number not valid
        System.out.println("Invalid option number. Please enter a number between 0-3");
      }
    }
  }

  // secondary menu for handling project status
  private static void handleProjectStatus(Scanner scanner) {
    String optionNumber;
    boolean validOption = false;
    while (!validOption) {
      // sub menu options
      System.out.println("Project status menu: Please enter an option from the following: ");
      System.out.println("Option 1: Finalise a project");
      System.out.println("Option 2: Search projects that are incomplete");
      System.out.println("Option 3: Search projects that are overdue");
      System.out.println("Option 0: Return to main menu");
      optionNumber = scanner.nextLine();
      // Remove spaces and dashes for option input validation
      String cleanOption = optionNumber.replaceAll("[\\s-()]", "");
      if (cleanOption.matches("[0-3]")) {
        validOption = true;
        switch (cleanOption) {
          case "1":
            ProjectsManager.finaliseProject(scanner);
            break;
          case "2":
            ProjectsManager.showIncompleteProjects();
            break;
          case "3":
            ProjectsManager.showOverdueProjects();
            break;
          case "0":
            // Break returns to main menu
            break;
        }
      } else {
        // message if option number not valid
        System.out.println("Invalid option number. Please enter a number between 0-3");
      }
    }
  }

  // secondary menu for handling people
  private static void handlePeopleManagement(Scanner scanner) {
    String optionNumber;
    boolean validOption = false;
    while (!validOption) {
      // sub menu options
      System.out.println("Project management menu: Please enter an option from the following: ");
      System.out.println("Option 1: Add a new person to the database");
      System.out.println("Option 2: Update a person's details");
      System.out.println("Option 3: Delete a person from the database");
      System.out.println("Option 0: Return to main menu");
      optionNumber = scanner.nextLine();
      // Remove spaces and dashes for option input validation
      String cleanOption = optionNumber.replaceAll("[\\s-()]", "");
      if (cleanOption.matches("[0-3]")) {
        validOption = true;
        switch (cleanOption) {
          case "1":
            PeopleManager.createPerson(scanner);
            break;
          case "2":
            PeopleManager.updatePeople(scanner);
            break;
          case "3":
            PeopleManager.deletePerson(scanner);
            break;
          case "0":
            // Break returns to main menu
            break;
        }
      } else {
        // message if option number not valid
        System.out.println("Invalid option number. Please enter a number between 0-3");
      }
    }
  }
}
