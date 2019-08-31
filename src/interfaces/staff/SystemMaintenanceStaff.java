package interfaces.staff;

import interfaces.Entity;
import interfaces.applicant.Applicant;
import driver.ManagementSystem;
import interfaces.employer.Employer;
import users.Person;

public class SystemMaintenanceStaff extends Person implements Entity {
  private static int systemMaintenanceStaffCount = 0;
  private int id;
  private String password;
  private ManagementSystem managementSystem;
  
  public SystemMaintenanceStaff(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    systemMaintenanceStaffCount++;
    this.id = systemMaintenanceStaffCount;
    setFirstName(firstName);
    setLastName(lastName);
    this.password = password;
  }
  
  public Employer getEmployerRecords(int id) {
    Employer returnEmployer = null;
    for (Employer employer : managementSystem.getEmployers()) {
      if (employer.getEmployerId() == id) {
        returnEmployer = employer;
      }
    }
    return returnEmployer;
  }
  
  public Applicant getApplicantRecords(int id) {
    Applicant returnApplicant = null;
    for (Applicant applicant : managementSystem.getApplicants()) {
      if (applicant.getApplicantId() == id) {
        returnApplicant = applicant;
      }
    }
    return returnApplicant;
  }
  
  public void blackListApplicant(Applicant applicant) {
    managementSystem.addApplicantToBlacklist(applicant);
    applicant.setAsBlackListed();
  }
  
  public void addNewJobCategory(String jobCategory) {
    managementSystem.addJobCategory(jobCategory);
  }
  
  @Override
  public boolean isPasswordMatch(String password) {
    return this.password.equals(password);
  }
}
