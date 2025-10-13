package com.richard.poise.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectsTest {

  @Test
  void testFormattedTotalFee() {
    // Arrange - set up test data
    Projects project = new Projects(1, "Test Project", false);
    project.setTotalFee(1234.56);

    // Act - run the method we're testing
    String formatted = project.getFormattedTotalFee();

    // Assert - check the result is correct
    assertEquals("1234.56", formatted);
  }

  @Test
  void testFormattedAmountPaidToDate() {
    // Arrange - set up test data
    Projects project = new Projects(1, "Test Project", false);
    project.setAmountPaidToDate(999.99);

    // Act - run the method we're testing
    String formatted = project.getFormattedAmountPaidToDate();

    // Assert - check the result is correct
    assertEquals("999.99", formatted);
  }

  @Test
  void testProjectFinalisedFalse() {
    Projects project = new Projects(1, "Test Project", false);

    Boolean finalised = project.getIsProjectFinalised();

    assertFalse(finalised);
  }

  @Test
  void testProjectFinalisedTrue() {
    Projects project = new Projects(1, "Test Project", true);

    Boolean finalised = project.getIsProjectFinalised();

    assertTrue(finalised);
  }
}
