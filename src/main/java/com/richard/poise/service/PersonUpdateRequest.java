package com.richard.poise.service;

public class PersonUpdateRequest {
    private String personName;
    private String phone;
    private String email;
    private String address;
    private String role;

    public String getPersonName(){
        return personName;
    }
    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public String getRole(){
        return role;
    }
    public void setPersonName(String personName){
        this.personName = personName;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setRole(String role){
        this.role = role;
    }
}
