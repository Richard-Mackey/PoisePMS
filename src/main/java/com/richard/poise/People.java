package com.richard.poise;

public class People {
  public int personID;
  public String personName;
  public String phone;
  public String email;
  public String address;
  public String role;

  public People(
      int personID, String personName, String phone, String email, String address, String role) {
    this.personID = personID;
    this.personName = personName;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.role = role;
  }

  public int getPersonID() {

    return personID;
  }

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

  public void setPersonID(int personID) {

    this.personID = personID;
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

  // string builder for outputting infprmation
  public String toString() {
    return personID
        + ", "
        + personName
        + ", "
        + phone
        + ", "
        + email
        + ", "
        + address
        + ", "
        + role;
  }
}
