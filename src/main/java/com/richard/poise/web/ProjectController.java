package com.richard.poise.web;

import com.richard.poise.model.People;
import com.richard.poise.model.Projects;
import com.richard.poise.repository.DatabasePersonRepository;
import com.richard.poise.repository.DatabaseProjectRepository;
import com.richard.poise.repository.PersonRepository;
import com.richard.poise.repository.ProjectRepository;
import com.richard.poise.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Web controller for project-related operations. Handles HTTP requests for viewing, creating,
 * updating, deleting, and finalising projects. Maps URLs to appropriate service methods and returns
 * Thymeleaf views.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectRepository projectRepository;

  private final SimpMessagingTemplate messagingTemplate;

  public ProjectController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
    this.projectRepository = new DatabaseProjectRepository();
  }

  /**
   * Displays list of all projects with summary information.
   *
   * @param model Spring Model for passing data to view
   * @return list.html template
   */
  @GetMapping
  public String listProjects(Model model) {
    List<Projects> projects = projectRepository.getAllProjectsSummary();
    model.addAttribute("projects", projects);
    model.addAttribute("pageTitle", "All Projects");
    return "projects/list";
  }

  /**
   * Displays details for a specific project including team members. Fetches related person data for
   * architect, contractor, customer, engineer, and manager.
   *
   * @param id the project's ID
   * @param model Spring Model for passing data to view
   * @return detail.html template or redirects if not found
   */
  @GetMapping("/{id}")
  public String projectDetail(@PathVariable int id, Model model) {
    Optional<Projects> projectOptional = projectRepository.findByID(id);

    if (projectOptional.isEmpty()) {
      return "redirect:/projects";
    }

    Projects project = projectOptional.get();

    model.addAttribute("project", project);

    // Fetch people objects by their IDs
    PersonRepository personRepository = new DatabasePersonRepository();

    if (project.getArchitectID() > 0) {
      personRepository
          .findByID(project.getArchitectID())
          .ifPresent(architect -> model.addAttribute("architect", architect));
    }

    if (project.getContractorID() > 0) {
      personRepository
          .findByID(project.getContractorID())
          .ifPresent(contractor -> model.addAttribute("contractor", contractor));
    }

    if (project.getCustomerID() > 0) {
      Optional<People> customerOptional = personRepository.findByID(project.getCustomerID());
      System.out.println("Customer found: " + customerOptional.isPresent()); // Debug line
      customerOptional.ifPresent(
          customer -> {
            System.out.println("Customer name: " + customer.getPersonName()); // Debug line
            model.addAttribute("customer", customer);
          });
    }

    if (project.getEngineerID() > 0) {
      personRepository
          .findByID(project.getEngineerID())
          .ifPresent(engineer -> model.addAttribute("engineer", engineer));
    }

    if (project.getManagerID() > 0) {
      personRepository
          .findByID(project.getManagerID())
          .ifPresent(manager -> model.addAttribute("manager", manager));
    }

    return "projects/detail";
  }

  /**
   * Displays the project creation form. Loads people by role for dropdown selections (customer,
   * architect, contractor, etc.).
   *
   * @param model Spring Model for form binding
   * @return create.html template
   */
  @GetMapping("/create")
  public String showCreateForm(Model model) {
    model.addAttribute("project", new ProjectFormData());

    // Load people by role for dropdowns
    PersonRepository personRepository = new DatabasePersonRepository();
    model.addAttribute("architects", personRepository.findByRole("architect"));
    model.addAttribute("contractors", personRepository.findByRole("contractor"));
    model.addAttribute("engineers", personRepository.findByRole("engineer"));
    model.addAttribute("managers", personRepository.findByRole("manager"));
    model.addAttribute("customers", personRepository.findByRole("customer"));

    return "projects/create";
  }

  /**
   * Processes project creation form submission. Validates that required customer field is provided.
   *
   * @param formData form data from create page
   * @param model Spring Model for error handling
   * @return redirects to projects list on success, or back to form on error
   */
  @PostMapping("/create")
  public String createProject(@ModelAttribute ProjectFormData formData, Model model) {

    ProjectCreateRequest request = new ProjectCreateRequest();
    request.setProjectName(formData.getProjectName());
    request.setBuildingType(formData.getBuildingType());
    request.setProjectAddress(formData.getProjectAddress());
    request.setERFNumber(formData.getERFNumber());
    request.setTotalFee(formData.getTotalFee());

    // Fix: Check for null or 0
    Integer customerID = formData.getCustomerID();
    if (customerID == null || customerID == 0) {
      model.addAttribute("error", "Customer is required");
      return showCreateForm(model);
    }

    request.setCustomerID(customerID);
    request.setArchitectID(formData.getArchitectID());
    request.setContractorID(formData.getContractorID());
    request.setEngineerID(formData.getEngineerID());
    request.setManagerID(formData.getManagerID());

    request.setAmountPaidToDate(0.0);
    request.setProjectFinalised(false);

    ProjectService projectService =
        new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());

    ProjectUpdateResult result = projectService.createProject(request);

    return "redirect:/projects";
  }

  /**
   * Displays the project edit form. Loads current project data and people by role for dropdown
   * selections.
   *
   * @param id the project's ID
   * @param model Spring Model for form binding
   * @return edit.html template or redirects if not found
   */
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable int id, Model model) {
    Optional<Projects> projectOptional = projectRepository.findByID(id);

    if (projectOptional.isEmpty()) {
      return "redirect:/projects";
    }

    Projects project = projectOptional.get();

    // Convert Projects to ProjectFormData for the form
    ProjectFormData formData = new ProjectFormData();
    formData.setProjectName(project.getProjectName());
    formData.setBuildingType(project.getBuildingType());
    formData.setProjectAddress(project.getProjectAddress());
    formData.setERFNumber(project.getERFNumber());
    formData.setTotalFee(project.getTotalFee());

    // Add people IDs
    formData.setCustomerID(project.getCustomerID());
    formData.setArchitectID(project.getArchitectID());
    formData.setContractorID(project.getContractorID());
    formData.setEngineerID(project.getEngineerID());
    formData.setManagerID(project.getManagerID());

    // Load people by role for dropdowns (same as create form)
    PersonRepository personRepository = new DatabasePersonRepository();
    model.addAttribute("architects", personRepository.findByRole("architect"));
    model.addAttribute("contractors", personRepository.findByRole("contractor"));
    model.addAttribute("engineers", personRepository.findByRole("engineer"));
    model.addAttribute("managers", personRepository.findByRole("manager"));
    model.addAttribute("customers", personRepository.findByRole("customer"));

    model.addAttribute("project", formData);
    model.addAttribute("projectId", id);
    return "projects/edit";
  }

  /**
   * Processes project edit form submission.
   *
   * @param id the project's ID
   * @param formData updated form data
   * @return redirects to project detail on success
   */
  @PostMapping("/{id}/edit")
  public String updateProject(@PathVariable int id, @ModelAttribute ProjectFormData formData) {
    ProjectUpdateRequest request = new ProjectUpdateRequest();
    request.setProjectName(formData.getProjectName());
    request.setBuildingType(formData.getBuildingType());
    request.setProjectAddress(formData.getProjectAddress());
    request.setERFNumber(formData.getERFNumber());
    request.setTotalFee(formData.getTotalFee());

    // Add people IDs to request
    request.setCustomerID(formData.getCustomerID());
    request.setArchitectID(formData.getArchitectID());
    request.setContractorID(formData.getContractorID());
    request.setEngineerID(formData.getEngineerID());
    request.setManagerID(formData.getManagerID());

    ProjectService projectService =
        new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());

    ProjectUpdateResult result = projectService.updateProject(id, request);

    return "redirect:/projects/" + id;
  }

  /**
   * Handles project deletion.
   *
   * @param id the project's ID
   * @return redirects to projects list after deletion
   */
  @PostMapping("/{id}/delete")
  public String deleteProject(@PathVariable int id) {
    ProjectService projectService =
        new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());

    ProjectUpdateResult result = projectService.deleteProject(id);

    return "redirect:/projects";
  }

  /**
   * Displays list of incomplete projects.
   *
   * @param model Spring Model for passing data to view
   * @return list.html template with filtered results
   */
  @GetMapping("/incomplete")
  public String incompleteProjects(Model model) {
    List<Projects> projects = projectRepository.getIncompleteProjects();
    model.addAttribute("projects", projects);
    model.addAttribute("pageTitle", "Incomplete Projects");
    return "projects/list"; // Reuses existing template
  }

  /**
   * Displays list of overdue projects.
   *
   * @param model Spring Model for passing data to view
   * @return list.html template with filtered results
   */
  @GetMapping("/overdue")
  public String overdueProjects(Model model) {
    List<Projects> projects = projectRepository.getOverdueProjects();
    model.addAttribute("projects", projects);
    model.addAttribute("pageTitle", "Overdue Projects");
    return "projects/list"; // Reuses existing template
  }

  /**
   * Displays the project search page.
   *
   * @return search.html template
   */
  @GetMapping("/search")
  public String searchPage() {
    return "projects/search"; // We'll create this template
  }

  /**
   * Searches for a project by ID.
   *
   * @param id the project's ID to search for
   * @param model Spring Model for error messages
   * @return redirects to project detail if found, or back to search with error
   */
  @GetMapping("/search/by-id")
  public String searchById(@RequestParam int id, Model model) {
    Optional<Projects> projectOptional = projectRepository.findByID(id);

    if (projectOptional.isEmpty()) {
      model.addAttribute("errorMessage", "Project not found with ID: " + id);
      return "projects/search";
    }

    // Found it - redirect to detail page
    return "redirect:/projects/" + id;
  }

  /**
   * Searches for a project by name.
   *
   * @param name the project's name to search for
   * @param model Spring Model for error messages
   * @return redirects to project detail if found, or back to search with error
   */
  @GetMapping("/search/by-name")
  public String searchByName(@RequestParam String name, Model model) {
    Optional<Projects> projectOptional = projectRepository.findByName(name);

    if (projectOptional.isEmpty()) {
      model.addAttribute("errorMessage", "Project not found with name: " + name);
      return "projects/search";
    }

    Projects project = projectOptional.get();
    return "redirect:/projects/" + project.getProjectID();
  }

  /**
   * Displays the project finalisation form. Prevents finalising already completed projects.
   *
   * @param id the project's ID
   * @param model Spring Model for form binding and error messages
   * @return finalise.html template or redirects if already finalised/not found
   */
  @GetMapping("/{id}/finalise")
  public String showFinaliseForm(@PathVariable int id, Model model) {
    Optional<Projects> projectOptional = projectRepository.findByID(id);

    if (projectOptional.isEmpty()) {
      return "redirect:/projects";
    }

    Projects project = projectOptional.get();

    // Check if already finalised
    if (project.getIsProjectFinalised()) {
      model.addAttribute("errorMessage", "This project is already finalised");
      return "redirect:/projects/" + id;
    }

    model.addAttribute("project", project);
    return "projects/finalise";
  }

  /**
   * Processes project finalisation form submission. Marks project as complete with the provided
   * completion date.
   *
   * @param id the project's ID
   * @param completionDate the date the project was completed
   * @param model Spring Model for error handling
   * @return redirects to project detail on success, or back to form on error
   */
  @PostMapping("/{id}/finalise")
  public String finaliseProject(
      @PathVariable int id, @RequestParam String completionDate, Model model) {
    try {
      java.sql.Date sqlDate = java.sql.Date.valueOf(completionDate);

      ProjectService projectService =
          new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());

      ProjectUpdateResult result = projectService.finaliseProject(id, sqlDate);

      if (result.getSuccess()) {
        Optional<Projects> projectOptional = projectRepository.findByID(id);
        if (projectOptional.isPresent()) {
          Projects project = projectOptional.get();
          ProjectFinalisationMessage message =
              new ProjectFinalisationMessage(project.projectID, project.projectName, "User");
          messagingTemplate.convertAndSend(
              "/topic/project/" + project.projectID + "/finalized", message);
        }
        return "redirect:/projects/" + id;
      } else {
        model.addAttribute("errorMessage", result.getMessage());
        return "projects/finalise";
      }

    } catch (IllegalArgumentException e) {
      model.addAttribute("errorMessage", "Invalid date format");
      return "projects/finalise";
    }
  }
}
