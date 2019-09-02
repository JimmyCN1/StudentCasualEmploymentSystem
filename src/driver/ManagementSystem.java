package driver;

import model.applicant.Applicant;
import driver.utilities.Security;
import model.employer.Employer;
import model.staff.SystemMaintenanceStaff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagementSystem {
  private Map<String, Employer> employers = new HashMap<>();
  private Map<String, Applicant> applicants = new HashMap<>();
  private Map<String, SystemMaintenanceStaff> systemMaintenanceStaff = new HashMap<>();
  private Map<String, Applicant> blacklistedApplicants = new HashMap<>();
  private List<String> jobCategories = new ArrayList<>();
  private Security security;
  
  public ManagementSystem() {
  }
  
  
  public List<Employer> getEmployers() {
    List<Employer> employers = new ArrayList<>();
    for (Employer e : employers) {
      employers.add(e);
    }
    return employers;
  }
  
  public List<Applicant> getApplicants() {
    List<Applicant> applicants = new ArrayList<>();
    for (Applicant a : applicants) {
      applicants.add(a);
    }
    return applicants;
  }
  
  public List<SystemMaintenanceStaff> getSystemMaintenanceStaff() {
    List<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
    for (SystemMaintenanceStaff s : systemMaintenanceStaff) {
      systemMaintenanceStaff.add(s);
    }
    return systemMaintenanceStaff;
  }
  
  
  public Map<String, Applicant> getBlacklistedApplicants() {
    return blacklistedApplicants;
  }
  
  public List<String> getJobCategories() {
    return jobCategories;
  }
  
  public Security getSecurity() {
    return security;
  }
  
  
  public Employer getEmployerByName(String keyName) {
    return employers.get(keyName);
  }
  
  public Applicant getApplicantByName(String keyName) {
    return applicants.get(keyName);
  }
  
  public SystemMaintenanceStaff getSystemMaintenanceByName(String keyName) {
    return systemMaintenanceStaff.get(keyName);
  }
  
  public void registerApplicant(Applicant applicant) {
    applicants.put(applicant.getHashMapKey(),
            applicant);
  }
  
  public void registerEmployer(Employer employer) {
    employers.put(employer.getHashMapKey(), employer);
  }
  
  public void registerSystemMaintenanceStaff(SystemMaintenanceStaff systemMaintenanceStaff) {
    this.systemMaintenanceStaff.put(
            systemMaintenanceStaff.getHashMapKey(),
            systemMaintenanceStaff);
  }
  
  public void addApplicantToBlacklist(Applicant applicant) {
    blacklistedApplicants.put(
            applicant.getHashMapKey(),
            applicant);
  }
  
  public void addJobCategory(String jobCategory) {
    jobCategories.add(jobCategory);
  }
}
