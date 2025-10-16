package com.richard.poise.service;

import java.time.LocalDateTime;

/**
 * WebSocket message sent when a project is finalized. Notifies all users viewing the project that
 * it has been completed.
 */
public class ProjectFinalisationMessage {
  private int projectId;
  private String projectName;
  private String finalizedBy;
  private LocalDateTime timestamp;

  // Empty constructor required for JSON deserialization
  public ProjectFinalisationMessage() {}

  public ProjectFinalisationMessage(int projectId, String projectName, String finalizedBy) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.finalizedBy = finalizedBy;
    this.timestamp = LocalDateTime.now();
  }

  // Getters and setters
  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getFinalizedBy() {
    return finalizedBy;
  }

  public void setFinalizedBy(String finalizedBy) {
    this.finalizedBy = finalizedBy;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
