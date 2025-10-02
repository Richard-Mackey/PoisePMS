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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectRepository projectRepository;

  public ProjectController() {
    this.projectRepository = new DatabaseProjectRepository();
  }

  @GetMapping
  public String listProjects(Model model) {
    List<Projects> projects = projectRepository.getAllProjectsSummary();
    model.addAttribute("projects", projects);
      model.addAttribute("pageTitle", "All Projects");
      return "projects/list";
  }

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
            personRepository.findByID(project.getArchitectID())
                    .ifPresent(architect -> model.addAttribute("architect", architect));
        }

        if (project.getContractorID() > 0) {
            personRepository.findByID(project.getContractorID())
                    .ifPresent(contractor -> model.addAttribute("contractor", contractor));
        }

        if (project.getCustomerID() > 0) {
            Optional<People> customerOptional = personRepository.findByID(project.getCustomerID());
            System.out.println("Customer found: " + customerOptional.isPresent());  // Debug line
            customerOptional.ifPresent(customer -> {
                System.out.println("Customer name: " + customer.getPersonName());  // Debug line
                model.addAttribute("customer", customer);
            });
        }

        if (project.getEngineerID() > 0) {
            personRepository.findByID(project.getEngineerID())
                    .ifPresent(engineer -> model.addAttribute("engineer", engineer));
        }

        if (project.getManagerID() > 0) {
            personRepository.findByID(project.getManagerID())
                    .ifPresent(manager -> model.addAttribute("manager", manager));
        }

        return "projects/detail";
    }

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
        request.setArchitectID(formData.getArchitectID() != null ? formData.getArchitectID() : 0);
        request.setContractorID(formData.getContractorID() != null ? formData.getContractorID() : 0);
        request.setEngineerID(formData.getEngineerID() != null ? formData.getEngineerID() : 0);
        request.setManagerID(formData.getManagerID() != null ? formData.getManagerID() : 0);

        request.setAmountPaidToDate(0.0);
        request.setProjectFinalised(false);

        ProjectService projectService =
                new ProjectService(new DatabaseProjectRepository(), new DatabasePersonRepository());

        ProjectUpdateResult result = projectService.createProject(request);

        return "redirect:/projects";
    }

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

        ProjectService projectService = new ProjectService(
                new DatabaseProjectRepository(),
                new DatabasePersonRepository()
        );

        ProjectUpdateResult result = projectService.updateProject(id, request);

        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable int id) {
        ProjectService projectService = new ProjectService(
                new DatabaseProjectRepository(),
                new DatabasePersonRepository()
        );

        ProjectUpdateResult result = projectService.deleteProject(id);

        return "redirect:/projects";
    }
    @GetMapping("/incomplete")
    public String incompleteProjects(Model model) {
        List<Projects> projects = projectRepository.getIncompleteProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("pageTitle", "Incomplete Projects");
        return "projects/list";  // Reuses existing template
    }

    @GetMapping("/overdue")
    public String overdueProjects(Model model) {
        List<Projects> projects = projectRepository.getOverdueProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("pageTitle", "Overdue Projects");
        return "projects/list";  // Reuses existing template
    }
    @GetMapping("/search")
    public String searchPage() {
        return "projects/search";  // We'll create this template
    }

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
    @GetMapping("/{id}/finalise")
    public String showFinaliseForm(@PathVariable int id, Model model) {
        Optional<Projects> projectOptional = projectRepository.findByID(id);

        if (projectOptional.isEmpty()) {
            return "redirect:/projects";
        }

        Projects project = projectOptional.get();

        // Check if already finalised
        if (project.isProjectFinalised()) {
            model.addAttribute("errorMessage", "This project is already finalised");
            return "redirect:/projects/" + id;
        }

        model.addAttribute("project", project);
        return "projects/finalise";
    }

    @PostMapping("/{id}/finalise")
    public String finaliseProject(@PathVariable int id,
                                  @RequestParam String completionDate,
                                  Model model) {
        try {
            java.sql.Date sqlDate = java.sql.Date.valueOf(completionDate);

            ProjectService projectService = new ProjectService(
                    new DatabaseProjectRepository(),
                    new DatabasePersonRepository()
            );

            ProjectUpdateResult result = projectService.finaliseProject(id, sqlDate);

            if (result.getSuccess()) {
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