package com.richard.poise;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<People> findAll();
  Optional<People> findByID(int personID); // Changed

  Optional<People> findByName(String personName); // Changed

  List<People> findByRole(String role);

  boolean updatePersonData(
      int personID,
      String personName,
      String phone,
      String email,
      String personAddress,
      String role);

  int createPersonData(
      String personName, String phone, String email, String personAddress, String role);

  boolean deletePersonData(int person_id);

  List<String> getProjectsLinkedToPerson(int personID);

  boolean isCustomerInProjects(int personID);
}
