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
  
  public Employer getEmployersRecords(int id) {
    Employer employer = null;
    for (Employer e : getManagementSystem().getEmployersAsList()) {
      if (e.getId() == id) {
        employer = e;
      }
    }
    return employer;
  }
  
  public Applicant getApplicantRecords(int id) {
    Applicant applicant = null;
    for (Applicant a : getManagementSystem().getApplicantsAsList()) {
      if (a.getId() == id) {
        applicant = a;
      }
    }
    return applicant;
  }
  
  //Blacklist a user
  public void blacklistUser(User user) throws InvalidUserStatusException {
    getManagementSystem().addUserToBlacklist(user);
    user.setStatus(UserStatus.BLACKLISTED);
  }
  
  //Create New Job Category
  public void addNewJobCategory(String jobCategory) {
    getManagementSystem().addJobCategory(jobCategory);
  }
  
  @Override
  public String statusToString() {
    return null;
  }
}
