package com.richard.poise.model;

import java.sql.Date;
import java.text.DecimalFormat;

public class Projects {
  public int projectID;
  public String projectName;
  public String buildingType;
  public String projectAddress;
  public int ERFNumber;
  public double totalFee;
  public double amountPaidToDate;
  public java.sql.Date projectDeadline;
  public int architectID;
  public int contractorID;
  public int customerID;
  public int engineerID;
  public int managerID;
  public boolean projectFinalised;
  public java.sql.Date completionDate;
  // allows for fee values to be given to 2dp in output
  public static final DecimalFormat currencyFormat = new DecimalFormat("0.00");

  // summary projects constructor
  public Projects(int projectID, String projectName, boolean projectFinalised) {
    this.projectID = projectID;
    this.projectName = projectName;
    this.projectFinalised = projectFinalised;
  }

  // all projects constructor
  public Projects(
      int projectID,
      String projectName,
      String buildingType,
      String projectAddress,
      int ERFNumber,
      double totalFee,
      double amountPaidToDate,
      java.sql.Date projectDeadline,
      int architectID,
      int contractorID,
      int customerID,
      int engineerID,
      int managerID,
      boolean projectFinalised,
      java.sql.Date completionDate) {
    this.projectID = projectID;
    this.projectName = projectName;
    this.buildingType = buildingType;
    this.projectAddress = projectAddress;
    this.ERFNumber = ERFNumber;
    this.totalFee = totalFee;
    this.amountPaidToDate = amountPaidToDate;
    this.projectDeadline = projectDeadline;
    this.architectID = architectID;
    this.contractorID = contractorID;
    this.customerID = customerID;
    this.engineerID = engineerID;
    this.managerID = managerID;
    this.projectFinalised = projectFinalised;
    this.completionDate = completionDate;
  }

  public int getProjectID() {
    return projectID;
  }

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

  public String getFormattedTotalFee() {
    return currencyFormat.format(totalFee);
  }

  public String getFormattedAmountPaidToDate() {
    return currencyFormat.format(amountPaidToDate);
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

  public boolean getIsProjectFinalised() {
    return projectFinalised;
  }

  public java.sql.Date getCompletionDate() {
    return completionDate;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
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

  public void setEngineerID(int engineer_ID) {
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

  // string builder to output information
  public String toString() {
    return projectID
        + ", "
        + projectName
        + ", "
        + buildingType
        + ", "
        + projectAddress
        + ", "
        + ERFNumber
        + ", "
        + totalFee
        + ", "
        + amountPaidToDate
        + ", "
        + projectDeadline
        + ", "
        + architectID
        + ", "
        + contractorID
        + ", "
        + customerID
        + ", "
        + engineerID
        + ", "
        + managerID
        + ", "
        + projectFinalised
        + ", "
        + completionDate;
  }

  public String toSummaryString() {
    return "ID: "
        + projectID
        + " | Project: "
        + projectName
        + " | Status: "
        + (projectFinalised ? "Completed" : "In Progress");
  }
}
