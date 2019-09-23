package model.user.staff;

import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;
import model.system.ManagementSystem;
import model.user.Person;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.employer.Employer;

public class SystemMaintenanceStaff extends Person {
  private static int systemMaintenanceStaffCount = 0;
  private int id;
  
  public SystemMaintenanceStaff(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName, lastName, password, managementSystem);
    systemMaintenanceStaffCount++;
    this.id = systemMaintenanceStaffCount;
    setFirstName(firstName);
    setLastName(lastName);
  }
  
  @Override
  public int getId() {
    return id;
  }
  
  @Override
  public UserStatus getStatus() {
    return null;
  }
  
  @Override
  public void setStatus(UserStatus status) {
  }
  
  public Employer getEmployerRecords(int id) {
    Employer returnEmployer = null;
    for (Employer employer : getManagementSystem().getEmployersAsList()) {
      if (employer.getId() == id) {
        returnEmployer = employer;
      }
    }
    return returnEmployer;
  }
  
  public Applicant getApplicantRecords(int id) {
    Applicant returnApplicant = null;
    for (Applicant applicant : getManagementSystem().getApplicantsAsList()) {
      if (applicant.getId() == id) {
        returnApplicant = applicant;
      }
    }
    return returnApplicant;
  }
  
  public void blacklistUser(User user) throws InvalidUserStatusException {
    getManagementSystem().addUserToBlacklist(user);
    user.setStatus(UserStatus.BLACKLISTED);
  }
  
  public void addNewJobCategory(String jobCategory) {
    getManagementSystem().addJobCategory(jobCategory);
  }
  
  @Override
  public String statusToString() {
    return null;
  }
}
