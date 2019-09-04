package model.applicant;

import enumerators.PositionType;
import model.system.ManagementSystem;

public class InternationalStudent extends Applicant {
  public InternationalStudent(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName, lastName, password, PositionType.PART_TIME, managementSystem);
  }
}
