package com.richard.poise.service;
/**
 * Form backing object for project data in web forms.
 * Used by Thymeleaf to bind HTML form inputs to Java objects.
 * Handles data transfer between view layer and controller for project creation/editing.
 */
public class ProjectFormData {
    private String projectName;
    private String buildingType;
    private String projectAddress;
    private int ERFNumber;
    private double totalFee;
    private Integer customerID;
    private Integer architectID;
    private Integer contractorID;
    private Integer engineerID;
    private Integer managerID;

    // Empty constructor (required for Spring)
    public ProjectFormData() {
    }

    // Getters and Setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public int getERFNumber() {
        return ERFNumber;
    }

    public void setERFNumber(int ERFNumber) {
        this.ERFNumber = ERFNumber;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }
    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    public Integer getArchitectID() {
        return architectID;
    }

    public void setArchitectID(Integer architectID) {
        this.architectID = architectID;
    }
    public Integer getContractorID() {
        return contractorID;
    }

    public void setContractorID(Integer contractorID) {
        this.contractorID = contractorID;
    }
    public Integer getEngineerID() {
        return engineerID;
    }

    public void setEngineerID(Integer engineerID) {
        this.engineerID = engineerID;
    }
    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }
}