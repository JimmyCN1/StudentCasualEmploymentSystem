package model.applicant;

import enumerators.PositionType;
import model.driver.ManagementSystem;

public class InternationalStudent extends Applicant {
  public InternationalStudent(String firstName, String lastName, String password, PositionType applicantAvailability, ManagementSystem managementSystem) {
    super(firstName, lastName, password, applicantAvailability, managementSystem);
    setAvailability(PositionType.PART_TIME);
  }
}
