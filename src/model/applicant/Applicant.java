package model.applicant;

import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.InvalidJobCategoryException;
import exceptions.JobCategoryNotFoundException;
import model.system.ManagementSystem;
import interfaces.Entity;
import model.employer.Employer;
import users.Person;

import java.util.ArrayList;
import java.util.List;

public abstract class Applicant extends Person implements Entity {
  private static int applicantCount = 0;
  private int applicantId;
  private String password;
  private String cv;
  private ApplicantStatus status;
  private PositionType availability;
  private List<String> jobPreferences = new ArrayList<>();
  private List<String> complaints = new ArrayList<>();
  private ManagementSystem managementSystem;
  
  public Applicant(String firstName, String lastName, String password, PositionType availability,
                   ManagementSystem managementSystem) {
    applicantCount++;
    this.applicantId = applicantCount;
    setFirstName(firstName);
    setLastName(lastName);
    this.password = password;
    this.status = ApplicantStatus.AVAILABLE;
    this.availability = availability;
    this.managementSystem = managementSystem;
  }
  
  @Override
  public int getId() {
    return applicantId;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  public ApplicantStatus getStatus() {
    return status;
  }
  
  public PositionType getAvailability() {
    return availability;
  }
  
  public String getCv() {
    return cv;
  }
  
  public List<String> getJobPreferences() {
    return jobPreferences;
  }
  
  public List<String> getComplaints() {
    return complaints;
  }
  
  public void setStatus(ApplicantStatus applicantStatus) {
    this.status = applicantStatus;
  }
  
  public void setAvailability(PositionType availability) {
    this.availability = availability;
  }
  
  public void addJobPreference(String jobPreference)
          throws InvalidJobCategoryException {
    String jobPref = jobPreference.toUpperCase();
    if (managementSystem.getJobCategories().contains(jobPref)) {
      jobPreferences.add(jobPref);
    } else {
      throw new InvalidJobCategoryException();
    }
  }
  
  public boolean removeJobPreference(String jobPreference)
          throws JobCategoryNotFoundException {
    String jobPref = jobPreference.toUpperCase();
    boolean wasPreferenceRemoved = false;
    if (jobPreferences.contains(jobPref)) {
      jobPreferences.remove(jobPref);
    } else {
      throw new JobCategoryNotFoundException();
    }
    return wasPreferenceRemoved;
  }
  
  public void addComplaint(String complaint) {
    complaints.add(complaint);
  }
  
  public String fetchNotification(String notification) {
    return String.format("You have one notification...\n%s",
            notification);
  }
  
  public void lodgeComplaint(String complaint, Employer employer) {
    employer.addComplaint(complaint);
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Applicant) object);
  }
  
  private boolean equals(Applicant applicant) {
    return applicant.getFirstName() == this.getFirstName() &&
            applicant.getLastName() == this.getLastName();
  }
}
