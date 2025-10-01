package com.richard.poise;

public class PersonFormData {
    private String personName;
    private String phone;
    private String email;
    private String address;
    private String role;

    // Empty constructor (required for Spring)
    public PersonFormData() {
    }

    // Getters
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

    // Setters (required for form binding!)
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