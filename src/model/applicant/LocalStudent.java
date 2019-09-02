package model.applicant;

import enumerators.ApplicantStatus;
import model.driver.ManagementSystem;

public class LocalStudent extends Applicant {
  public LocalStudent(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName, lastName, password, managementSystem);
  }
}
