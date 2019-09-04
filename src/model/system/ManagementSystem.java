package model.system;

import model.applicant.Applicant;
import model.system.utilities.Security;
import model.employer.Employer;
import model.staff.SystemMaintenanceStaff;

import java.util.*;

public class ManagementSystem {
  private Map<String, Employer> employers = new HashMap<>();
  private Map<String, Applicant> applicants = new HashMap<>();
  private Map<String, SystemMaintenanceStaff> systemMaintenanceStaff = new HashMap<>();
  private Map<String, Applicant> blacklistedApplicants = new HashMap<>();
  private List<String> jobCategories = new ArrayList<>(
          Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE")
  );
  private Security security;
  
  public ManagementSystem() {
  }
  
  
  public List<Employer> getEmployers() {
    List<Employer> employers = new ArrayList<>();
    for (String e : this.employers.keySet()) {
      employers.add(this.employers.get(e));
    }
    return employers;
  }
  
  public List<Applicant> getApplicants() {
    List<Applicant> applicants = new ArrayList<>();
    for (String a : this.applicants.keySet()) {
      applicants.add(this.applicants.get(a));
    }
    return applicants;
  }
  
  public List<SystemMaintenanceStaff> getSystemMaintenanceStaff() {
    List<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
    for (String s : this.systemMaintenanceStaff.keySet()) {
      systemMaintenanceStaff.add(this.systemMaintenanceStaff.get(s));
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
    jobCategories.add(jobCategory.toUpperCase());
  }
}
