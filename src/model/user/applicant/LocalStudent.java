package model.user.applicant;

import enumerators.PositionType;
import model.system.ManagementSystem;

public class LocalStudent extends Applicant {
  public LocalStudent(String firstName, String lastName, String password, PositionType availability, ManagementSystem managementSystem) {
    super(firstName, lastName, password, availability, managementSystem);
  }
}
