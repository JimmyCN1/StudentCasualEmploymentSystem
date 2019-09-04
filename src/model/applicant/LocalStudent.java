package model.applicant;

import enumerators.PositionType;
import model.system.ManagementSystem;

public class LocalStudent extends Applicant {
  public LocalStudent(String firstName, String lastName, String password, PositionType applicantAvailability, ManagementSystem managementSystem) {
    super(firstName, lastName, password, applicantAvailability, managementSystem);
  }
}
