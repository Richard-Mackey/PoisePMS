package com.richard.poise.service;

/**
 * Form backing object for person data in web forms. Used by Thymeleaf to bind HTML form inputs to
 * Java objects. Handles data transfer between view layer and controller.
 */
public class PersonFormData {
  private String personName;
  private String phone;
  private String email;
  private String address;
  private String role;

  // Empty constructor (required for Spring)
  public PersonFormData() {}

  public String getPersonName() {
    return personName;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getRole() {
    return role;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
