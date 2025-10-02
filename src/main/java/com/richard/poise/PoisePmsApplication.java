package com.richard.poise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application entry point for PoisePMS. Launches the web application and
 * configures component scanning. Deployed on Render with PostgreSQL database integration.
 */
@SpringBootApplication
public class PoisePmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PoisePmsApplication.class, args);
  }
}
