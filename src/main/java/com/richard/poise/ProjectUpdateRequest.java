package com.richard.poise;

import java.sql.Date;

public class ProjectUpdateRequest {
  private String projectName;
  private String buildingType;
  private String projectAddress;
  private Integer ERFNumber;
  private Double totalFee;
  private Double amountPaidToDate;
  private java.sql.Date projectDeadline;
  private Integer architectID;
  private Integer contractorID;
  private Integer customerID;
  private Integer engineerID;
  private Integer managerID;
  private Boolean projectFinalised; // Note: Boolean not boolean
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

    public Integer getERFNumber() {
        return ERFNumber;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public Double getAmountPaidToDate() {
        return amountPaidToDate;
    }

    public java.sql.Date getProjectDeadline() {
        return projectDeadline;
    }

    public Integer getArchitectID() {
        return architectID;
    }

    public Integer getContractorID() {
        return contractorID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Integer getEngineerID() {
        return engineerID;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public Boolean isProjectFinalised() {
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

    public void setERFNumber(Integer ERFNumber) {
        this.ERFNumber = ERFNumber;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public void setAmountPaidToDate(Double amountPaidToDate) {
        this.amountPaidToDate = amountPaidToDate;
    }

    public void setProjectDeadline(Date projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public void setArchitectID(Integer architectID) {
        this.architectID = architectID;
    }

    public void setContractorID(Integer contractorID) {
        this.contractorID = contractorID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public void setEngineerID(Integer engineerID) {
        this.engineerID = engineerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    public void setProjectFinalised(Boolean projectFinalised) {
        this.projectFinalised = projectFinalised;
    }
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

}