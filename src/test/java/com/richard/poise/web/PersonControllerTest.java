package com.richard.poise.web;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonControllerTest {

  @Test
  public void testValidEmailFormat() {
    PersonController controller = new PersonController();

    boolean result = controller.isValidEmail("test@example.com");

    assertTrue(result, "Valid email should return true");
  }

  @Test
  public void testInvalidEmailFormat() {
    PersonController controller = new PersonController();

    boolean result = controller.isValidEmail("notanemail");

    assertFalse(result, "Email without top level domain should return false");
  }

  @Test
  public void testInvalidEmailAtSign() {
    PersonController controller = new PersonController();

    boolean result = controller.isValidEmail("testatexample.com");

    assertFalse(result, "Email without @ should return false");
  }

  @Test
  public void testEmptyEmailIsValid() {
    PersonController controller = new PersonController();

    boolean result = controller.isValidEmail("");

    assertTrue(result, "Empty email should be valid (optional field)");
  }
}
