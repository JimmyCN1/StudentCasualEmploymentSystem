package driver;

import interfaces.applicant.Applicant;
import driver.utilities.Security;
import interfaces.employer.Employer;
import interfaces.staff.SystemMaintenanceStaff;

import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
  private List<Employer> employers;
  private List<Applicant> applicants = new ArrayList<>();
  private List<Applicant> blacklistedApplicants = new ArrayList<>();
  private List<String> jobCategories = new ArrayList<>();
  private Security security;
  private List<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
  
  public ManagementSystem() {
    this.employers = new ArrayList<>();
  }
  
  
  public List<Employer> getEmployers() {
    return employers;
  }
  
  public List<Applicant> getApplicants() {
    return applicants;
  }
  
  public List<Applicant> getBlacklistedApplicants() {
    return blacklistedApplicants;
  }
  
  public List<String> getJobCategories() {
    return jobCategories;
  }
  
  public Security getSecurity() {
    return security;
  }
  
  public List<SystemMaintenanceStaff> getSystemMaintenanceStaff() {
    return systemMaintenanceStaff;
  }
  
  public Employer getEmployerByName(String name) {
    Employer matchingEmployee = null;
    for (Employer employer : employers) {
      if (name == employer.getEmployerName()) {
        matchingEmployee = employer;
      }
    }
    return matchingEmployee;
  }
  
  public Applicant getApplicantByName(String firstName, String lastName) {
    Applicant matchingApplicant = null;
    for (Applicant applicant : applicants) {
      if (firstName == applicant.getFirstName() &&
              lastName == applicant.getLastName()) {
        matchingApplicant = applicant;
      }
    }
    return matchingApplicant;
  }
  
  public SystemMaintenanceStaff getSystemMaintenanceByName(String firstName, String lastName) {
    SystemMaintenanceStaff matchingStaff = null;
    for (SystemMaintenanceStaff maintenanceStaff : systemMaintenanceStaff) {
      if (firstName == maintenanceStaff.getFirstName() &&
              lastName == maintenanceStaff.getLastName()) {
        matchingStaff = maintenanceStaff;
      }
    }
    return matchingStaff;
  }
  
  public void registerApplicant(Applicant applicant) {
    applicants.add(applicant);
  }
  
  public void registerEmployer(Employer employer) {
    employers.add(employer);
  }
  
  public void registerSystemMaintenanceStaff(SystemMaintenanceStaff systemMaintenanceStaff) {
    this.systemMaintenanceStaff.add(systemMaintenanceStaff);
  }
  
  public void addApplicantToBlacklist(Applicant applicant) {
    blacklistedApplicants.add(applicant);
  }
  
  public void addJobCategory(String jobCategory) {
    jobCategories.add(jobCategory);
  }
}
