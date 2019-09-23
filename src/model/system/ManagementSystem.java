package model.system;

import exceptions.ApplicantNotFoundException;
import exceptions.EmployerNotFoundException;
import exceptions.SystemMaintenanceStaffNotFoundException;
import interfaces.UserInterface;
import model.Serialisation.SaveState;
import model.system.utilities.Security;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class ManagementSystem implements Serializable {
  private Map<Integer, User> users = new HashMap<>();
  private Map<String, Employer> employers = new HashMap<>();
  private Map<String, Applicant> applicants = new HashMap<>();
  private Map<String, SystemMaintenanceStaff> systemMaintenanceStaff = new HashMap<>();
  private Map<String, User> blacklistedUsers = new HashMap<>();
  private List<String> jobCategories = new ArrayList<>(
          Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE")
  );
  private Security security;
  
  private SaveState saveState = new SaveState(this);
  
  public ManagementSystem() {
  }
  
  public List<User> getUsersAsList() {
    List<User> users = new ArrayList<>();
    for (Integer userId : this.users.keySet()) {
      users.add(this.users.get(userId));
    }
    return users;
  }
  
  public List<Employer> getEmployersAsList() {
    List<Employer> employers = new ArrayList<>();
    for (String e : this.employers.keySet()) {
      employers.add(this.employers.get(e));
    }
    return employers;
  }
  
  public List<Applicant> getApplicantsAsList() {
    List<Applicant> applicants = new ArrayList<>();
    for (String a : this.applicants.keySet()) {
      applicants.add(this.applicants.get(a));
    }
    return applicants;
  }
  
  public List<SystemMaintenanceStaff> getSystemMaintenanceStaffAsList() {
    List<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
    for (String s : this.systemMaintenanceStaff.keySet()) {
      systemMaintenanceStaff.add(this.systemMaintenanceStaff.get(s));
    }
    return systemMaintenanceStaff;
  }
  
  public List<User> getBlacklistedAsList() {
    List<User> blacklisted = new ArrayList<>();
    for (String b : blacklistedUsers.keySet()) {
      blacklisted.add(blacklistedUsers.get(b));
    }
    
    return blacklisted;
  }
  
  
  public Map<String, User> getBlacklistedUsers() {
    return blacklistedUsers;
  }
  
  public List<String> getJobCategories() {
    return jobCategories;
  }
  
  public Security getSecurity() {
    return security;
  }
  
  public UserInterface getUserByName(String keyName) {
    return users.get(keyName);
  }
  
  public Employer getEmployerByName(String keyName)
          throws EmployerNotFoundException {
    if (employers.containsKey(keyName)) {
      return employers.get(keyName);
    } else {
      throw new EmployerNotFoundException();
    }
  }
  
  public Applicant getApplicantByName(String keyName)
          throws ApplicantNotFoundException {
    if (applicants.containsKey(keyName)) {
      return applicants.get(keyName);
      
    } else {
      throw new ApplicantNotFoundException();
    }
  }
  
  public SystemMaintenanceStaff getSystemMaintenanceByName(String keyName)
          throws SystemMaintenanceStaffNotFoundException {
    return systemMaintenanceStaff.get(keyName);
  }
  
  public void registerUser(User user) {
    users.put(user.getUserId(), user);
  }
  
  public void registerApplicant(Applicant applicant) {
    registerUser(applicant);
    applicants.put(applicant.getHashMapKey(), applicant);
  }
  
  public void registerEmployer(Employer employer) {
    registerUser(employer);
    employers.put(employer.getHashMapKey(), employer);
  }
  
  public void registerSystemMaintenanceStaff(SystemMaintenanceStaff systemMaintenanceStaff) {
    registerUser(systemMaintenanceStaff);
    this.systemMaintenanceStaff.put(
            systemMaintenanceStaff.getHashMapKey(),
            systemMaintenanceStaff);
  }
  
  public void addUserToBlacklist(User user) {
    blacklistedUsers.put(
            user.getHashMapKey(),
            user);
  }
  
  public void addJobCategory(String jobCategory) {
    jobCategories.add(jobCategory.toUpperCase());
  }
  
  
  // serialisation (Saving object states)
  public void recoverState() {
    try {
      Scanner read = new Scanner(new File("sizes.txt")).useDelimiter(":");
      
      read.next();
      int sizeU = read.nextInt();
      read.next();
      int sizeA = read.nextInt();
      read.next();
      int sizeE = read.nextInt();
      read.next();
      int sizeS = read.nextInt();
      read.next();
      int sizeB = read.nextInt();
      
      saveState.recoverUsers(sizeU, users);
      saveState.recoverApplicants(sizeA, applicants);
      saveState.recoverEmployers(sizeE, employers);
      saveState.recoverSystemMaintenance(sizeS, systemMaintenanceStaff);
      saveState.recoverBlacklisted(sizeB, blacklistedUsers);
    } catch (IOException | ClassNotFoundException ex) {
      System.out.println(ex);
    }
  }
  
  // Saves the state of all the objects so program can be closed and then reopened in same state
  public void saveState() {
    try {
      saveState.saveUsers();
      saveState.saveApplicants();
      saveState.saveEmployers();
      saveState.saveSystemMaintenanceStaff();
      saveState.saveBlacklisted();
      saveState.saveSizes(users.size(), applicants.size(), employers.size(), systemMaintenanceStaff.size(), blacklistedUsers.size());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  
}
