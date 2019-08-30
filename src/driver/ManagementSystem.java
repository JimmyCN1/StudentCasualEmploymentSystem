package driver;

import applicant.Applicant;
import driver.utilities.Security;
import employer.Employer;
import staff.SystemMaintenanceStaff;

import java.util.ArrayList;
import java.util.Collection;

public class ManagementSystem {
  private Collection<Employer> employers = new ArrayList<>();
  private Collection<Applicant> applicants = new ArrayList<>();
  private Collection<Applicant> blacklistedApplicants = new ArrayList<>();
  private Collection<String> jobCategories = new ArrayList<>();
  private Security security;
  private Collection<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
  
  
  public Collection<Employer> getEmployers() {
    return employers;
  }
  
  public Collection<Applicant> getApplicants() {
    return applicants;
  }
  
  public Collection<Applicant> getBlacklistedApplicants() {
    return blacklistedApplicants;
  }
  
  public Collection<String> getJobCategories() {
    return jobCategories;
  }
  
  public Security getSecurity() {
    return security;
  }
  
  public Collection<SystemMaintenanceStaff> getSystemMaintenanceStaff() {
    return systemMaintenanceStaff;
  }
  
  public void registerApplicant(Applicant applicant) {
    applicants.add(applicant);
    
    
  }
  
  public boolean registerEmployer() {
    return false;
  }
  
}
