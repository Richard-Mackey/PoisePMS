package com.richard.poise.service;

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