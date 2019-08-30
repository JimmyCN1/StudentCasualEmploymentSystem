package entities.applicant;

import driver.ManagementSystem;

public class InternationalStudent extends Applicant {
  public InternationalStudent(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName, lastName, password, managementSystem);
  }
}
