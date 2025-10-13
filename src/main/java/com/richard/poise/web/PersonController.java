package com.richard.poise.web;

import com.richard.poise.model.People;
import com.richard.poise.repository.DatabasePersonRepository;
import com.richard.poise.repository.PersonRepository;
import com.richard.poise.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Web controller for person-related operations. Handles HTTP requests for viewing, creating,
 * updating, and deleting people. Maps URLs to appropriate service methods and returns Thymeleaf
 * views.
 */
@Controller
@RequestMapping("/people")
public class PersonController {

  private final PersonRepository personRepository;

  public PersonController() {
    this.personRepository = new DatabasePersonRepository();
  }

  /**
   * Displays list of all people or filtered by role.
   *
   * @param model Spring Model for passing data to view
   * @return list.html template
   */
  @GetMapping
  public String listPeople(Model model) { // Changed method name
    List<People> people = personRepository.findAll();
    model.addAttribute("people", people);
    return "people/list";
  }

  /**
   * Displays details for a specific person.
   *
   * @param id the person's ID
   * @param model Spring Model for passing data to view
   * @return detail.html template or redirects if not found
   */
  @GetMapping("/{id}")
  public String personDetail(@PathVariable int id, Model model) { // Changed method name
    Optional<People> personOptional = personRepository.findByID(id); // Changed variable name

    if (personOptional.isEmpty()) { // Changed variable name
      return "redirect:/people";
    }

    People person = personOptional.get(); // Changed variable name
    model.addAttribute("person", person); // Changed to "person" singular
    return "people/detail";
  }

  /**
   * Displays the person creation form.
   *
   * @param model Spring Model for form binding
   * @return create.html template
   */
  @GetMapping("/create")
  public String showCreateForm(Model model) {
    model.addAttribute("person", new PersonFormData());
    return "people/create";
  }

  /**
   * Processes person creation form submission. Validates email format before creating.
   *
   * @param formData form data from create page
   * @param model Spring Model for error handling
   * @return redirects to person list on success, or back to form on error
   */
  @PostMapping("/create")
  public String createPerson(@ModelAttribute PersonFormData formData, Model model) {
    // Validate email format
    if (!isValidEmail(formData.getEmail())) {
      model.addAttribute(
          "errorMessage",
          "Invalid email format. Please enter a valid email (e.g., user@example.com)");
      model.addAttribute("person", formData);
      return "people/create";
    }

    // Convert form data to PeopleCreateRequest
    PeopleCreateRequest request = new PeopleCreateRequest();
    request.setPersonName(formData.getPersonName());
    request.setPhone(formData.getPhone());
    request.setEmail(formData.getEmail());
    request.setAddress(formData.getAddress());
    request.setRole(formData.getRole());

    PersonService peopleService = new PersonService(new DatabasePersonRepository());
    PersonUpdateResult result = peopleService.createPerson(request);

    if (result.getSuccess()) {
      return "redirect:/people";
    } else {
      model.addAttribute("errorMessage", result.getMessage());
      model.addAttribute("person", formData);
      return "people/create";
    }
  }

  /**
   * Displays the person edit form.
   *
   * @param id the person's ID
   * @param model Spring Model for form binding
   * @return edit.html template or redirects if not found
   */
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable int id, Model model) {
    Optional<People> personOptional = personRepository.findByID(id);

    if (personOptional.isEmpty()) {
      return "redirect:/people";
    }

    People people = personOptional.get();

    // Convert Projects to ProjectFormData for the form
    PersonFormData formData = new PersonFormData();
    formData.setPersonName(people.getPersonName());
    formData.setPhone(people.getPhone());
    formData.setEmail(people.getEmail());
    formData.setAddress(people.getAddress());
    formData.setRole(people.getRole());

    model.addAttribute("person", formData);
    model.addAttribute("personId", id);
    return "people/edit";
  }

  /**
   * Processes person edit form submission. Validates email format before updating.
   *
   * @param id the person's ID
   * @param formData updated form data
   * @param model Spring Model for error handling
   * @return redirects to person detail on success, or back to form on error
   */
  @PostMapping("/{id}/edit")
  public String updatePeople(
      @PathVariable int id, @ModelAttribute PersonFormData formData, Model model) {
    // Validate email format
    if (!isValidEmail(formData.getEmail())) {
      model.addAttribute(
          "errorMessage",
          "Invalid email format. Please enter a valid email (e.g., user@example.com)");
      model.addAttribute("person", formData);
      model.addAttribute("personId", id);
      return "people/edit";
    }

    PersonUpdateRequest request = new PersonUpdateRequest();
    request.setPersonName(formData.getPersonName());
    request.setPhone(formData.getPhone());
    request.setEmail(formData.getEmail());
    request.setAddress(formData.getAddress());
    request.setRole(formData.getRole());

    PersonService personService = new PersonService(new DatabasePersonRepository());

    PersonUpdateResult result = personService.updatePerson(id, request);

    if (result.getSuccess()) {
      return "redirect:/people/" + id;
    } else {
      model.addAttribute("errorMessage", result.getMessage());
      model.addAttribute("person", formData);
      model.addAttribute("personId", id);
      return "people/edit";
    }
  }

  /**
   * Handles person deletion.
   *
   * @param id the person's ID
   * @return redirects to person list after deletion
   */
  @PostMapping("/{id}/delete")
  public String deletePerson(@PathVariable int id) {
    PersonService personService = new PersonService(new DatabasePersonRepository());

    PersonUpdateResult result = personService.deletePerson(id);

    return "redirect:/people";
  }

  /**
   * Displays the person search page.
   *
   * @return search.html template
   */
  @GetMapping("/search")
  public String searchPage() {
    return "people/search"; // We'll create this template
  }

  /**
   * Searches for a person by ID.
   *
   * @param id the person's ID to search for
   * @param model Spring Model for error messages
   * @return redirects to person detail if found, or back to search with error
   */
  @GetMapping("/search/by-id")
  public String searchById(@RequestParam int id, Model model) {
    Optional<People> peopleOptional = personRepository.findByID(id);

    if (peopleOptional.isEmpty()) {
      model.addAttribute("errorMessage", "Person not found with ID: " + id);
      return "people/search";
    }

    // Found it - redirect to detail page
    return "redirect:/people/" + id;
  }

  /**
   * Searches for a person by name.
   *
   * @param name the person's name to search for
   * @param model Spring Model for error messages
   * @return redirects to person detail if found, or back to search with error
   */
  @GetMapping("/search/by-name")
  public String searchByName(@RequestParam String name, Model model) {
    Optional<People> peopleOptional = personRepository.findByName(name);

    if (peopleOptional.isEmpty()) {
      model.addAttribute("errorMessage", "Person not found with name: " + name);
      return "people/search";
    }

    People people = peopleOptional.get();
    return "redirect:/people/" + people.getPersonID();
  }

  /**
   * Displays people filtered by specific role.
   *
   * @param role the role to filter by (e.g., "customer", "architect")
   * @param model Spring Model for passing data to view
   * @return list.html template with filtered results
   */
  @GetMapping("/role/{role}")
  public String filterByRole(@PathVariable String role, Model model) {
    List<People> people = personRepository.findByRole(role);
    model.addAttribute("people", people);
    model.addAttribute("filterRole", role); // So we can show which filter is active
    return "people/list";
  }

  /**
   * Validates email format. Checks for basic email structure (contains @, proper domain format).
   *
   * @param email the email address to validate
   * @return true if valid or empty (optional field), false otherwise
   */
  public boolean isValidEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return true; // Empty is OK (optional field)
    }

    return email.contains("@")
        && email.indexOf("@") == email.lastIndexOf("@")
        && email.indexOf("@") > 0
        && email.indexOf("@") < email.length() - 1
        && email.substring(email.indexOf("@")).contains(".")
        && email.length() >= 5;
  }
}
