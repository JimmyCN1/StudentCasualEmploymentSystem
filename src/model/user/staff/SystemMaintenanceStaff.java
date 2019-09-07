package model.user.staff;

import enumerators.UserStatus;
import interfaces.UserInterface;
import model.user.applicant.Applicant;
import model.system.ManagementSystem;
import model.user.employer.Employer;
import model.user.Person;

public class SystemMaintenanceStaff extends Person implements UserInterface {
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
  
  @Override
  public int getId() {
    return id;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  public Employer getEmployerRecords(int id) {
    Employer returnEmployer = null;
    for (Employer employer : managementSystem.getEmployers()) {
      if (employer.getId() == id) {
        returnEmployer = employer;
      }
    }
    return returnEmployer;
  }
  
  public Applicant getApplicantRecords(int id) {
    Applicant returnApplicant = null;
    for (Applicant applicant : managementSystem.getApplicants()) {
      if (applicant.getId() == id) {
        returnApplicant = applicant;
      }
    }
    return returnApplicant;
  }
  
  public void blackListApplicant(Applicant applicant) {
    managementSystem.addApplicantToBlacklist(applicant);
    applicant.setStatus(UserStatus.BLACKLISTED);
  }
  
  public void addNewJobCategory(String jobCategory) {
    managementSystem.addJobCategory(jobCategory);
  }
  
  @Override
  public UserStatus getStatus() {
    return null;
  }
  
  @Override
  public void setStatus(UserStatus status) {
  }
}
