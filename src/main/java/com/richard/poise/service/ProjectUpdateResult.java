package com.richard.poise.service;

/**
 * Response object for project operations (create, update, delete, finalise). Encapsulates operation
 * success status and user-friendly messages. Provides a consistent return type for service layer
 * methods.
 */
public class ProjectUpdateResult {
  private boolean success;
  private String message;

  public ProjectUpdateResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public boolean getSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }
}
