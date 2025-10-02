package com.richard.poise.service;

/**
 * Data Transfer Object for creating a new person. Encapsulates all required information for person
 * creation. Used to pass data from controllers to the service layer.
 */
public class PeopleCreateRequest {

  private String personName;
  private String phone;
  private String email;
  private String address;
  private String role;

  /**
   * Gets the person's name.
   *
   * @return the person's full name
   */
  public String getPersonName() {

    return personName;
  }

  /**
   * Gets the person's phone number.
   *
   * @return the contact phone number
   */
  public String getPhone() {

    return phone;
  }

  /**
   * Gets the person's email address.
   *
   * @return the email address
   */
  public String getEmail() {

    return email;
  }

  /**
   * Gets the person's physical address.
   *
   * @return the physical address
   */
  public String getAddress() {

    return address;
  }

  /**
   * Gets the person's role in projects.
   *
   * @return the role (e.g., "customer", "architect", "contractor")
   */
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
