package com.richard.poise;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
  List<Projects> getAllProjectsSummary();

  Optional<Projects> findByID(int projectID);

  Optional<Projects> findByName(String projectName);

  boolean updateProjectData(
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
      java.sql.Date completionDate);

  int createProjectData(
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
      java.sql.Date completionDate);

  boolean deleteProjectData(int project_id);

  boolean finaliseProjectData(int projectID, Date finalisedDate);

    List<Projects> getIncompleteProjects();

    List<Projects> getOverdueProjects();
}
