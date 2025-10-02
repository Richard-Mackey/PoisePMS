package com.richard.poise.service;

/**
 * Response object for person operations (create, update, delete). Encapsulates operation success
 * status and user-friendly messages. Provides a consistent return type for service layer methods.
 */
public class PersonUpdateResult {
  private boolean success;
  private String message;

  public PersonUpdateResult(boolean success, String message) {
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
