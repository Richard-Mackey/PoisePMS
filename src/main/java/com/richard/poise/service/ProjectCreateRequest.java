package com.richard.poise.service;

import java.sql.Date;

/**
 * Data Transfer Object for creating a new project. Encapsulates all required information for
 * project creation. Used to pass data from controllers to the service layer.
 */
public class ProjectCreateRequest {

  private String projectName;
  private String buildingType;
  private String projectAddress;
  private int ERFNumber;
  private double totalFee;
  private double amountPaidToDate;
  private java.sql.Date projectDeadline;
  private int architectID;
  private int contractorID;
  private int customerID;
  private int engineerID;
  private int managerID;
  private boolean projectFinalised;
  private java.sql.Date completionDate;

  public String getProjectName() {
    return projectName;
  }

  public String getBuildingType() {
    return buildingType;
  }

  public String getProjectAddress() {
    return projectAddress;
  }

  public int getERFNumber() {
    return ERFNumber;
  }

  public double getTotalFee() {
    return totalFee;
  }

  public double getAmountPaidToDate() {
    return amountPaidToDate;
  }

  public java.sql.Date getProjectDeadline() {
    return projectDeadline;
  }

  public int getArchitectID() {
    return architectID;
  }

  public int getContractorID() {
    return contractorID;
  }

  public int getCustomerID() {
    return customerID;
  }

  public int getEngineerID() {
    return engineerID;
  }

  public int getManagerID() {
    return managerID;
  }

  public boolean isProjectFinalised() {
    return projectFinalised;
  }

  public java.sql.Date getCompletionDate() {
    return completionDate;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setBuildingType(String buildingType) {
    this.buildingType = buildingType;
  }

  public void setProjectAddress(String projectAddress) {
    this.projectAddress = projectAddress;
  }

  public void setERFNumber(int ERFNumber) {
    this.ERFNumber = ERFNumber;
  }

  public void setTotalFee(double totalFee) {
    this.totalFee = totalFee;
  }

  public void setAmountPaidToDate(double amountPaidToDate) {
    this.amountPaidToDate = amountPaidToDate;
  }

  public void setProjectDeadline(Date projectDeadline) {
    this.projectDeadline = projectDeadline;
  }

  public void setArchitectID(int architectID) {
    this.architectID = architectID;
  }

  public void setContractorID(int contractorID) {
    this.contractorID = contractorID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public void setEngineerID(int engineerID) {
    this.engineerID = engineerID;
  }

  public void setManagerID(int managerID) {
    this.managerID = managerID;
  }

  public void setProjectFinalised(boolean projectFinalised) {
    this.projectFinalised = projectFinalised;
  }

  public void setCompletionDate(Date completionDate) {
    this.completionDate = completionDate;
  }
}
