package model.applicant;

import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.InvalidJobCategoryException;
import exceptions.JobCategoryNotFoundException;
import model.position.Position;
import model.system.ManagementSystem;
import interfaces.User;
import model.employer.Employer;
import users.Person;

// Questionable
import model.applicant.utilities.Job;
import model.applicant.utilities.Reference;

import java.util.ArrayList;
import java.util.List;

public abstract class Applicant extends Person implements User {
  private static int applicantCount = 0;
  private int applicantId;
  private String password;
  private String cv; // The name of the text file
  private ApplicantStatus status;
  private PositionType availability;
  private Position jobOffer = null;
  
  private List<Job> pastJobs = new ArrayList<>();
  private List<Reference> references = new ArrayList<>();
  private List<Position> appliedJobs = new ArrayList<>();
  private List<String> jobPreferences = new ArrayList<>();
  private List<String> complaints = new ArrayList<>();
  
  // List of all notifications the applicant receives from all employers
  private List<String> notifications = new ArrayList<>();
  
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
    if (!managementSystem.getJobCategories().contains(jobPref)) {
      throw new JobCategoryNotFoundException();
    } else {
      if (jobPreferences.contains(jobPref)) {
        jobPreferences.remove(jobPref);
        wasPreferenceRemoved = true;
      }
    }
    return wasPreferenceRemoved;
  }
  
  public void addComplaint(String complaint) {
    complaints.add(complaint);
  }
  
  // Adds a notification to the notification list of the applicant
  public void addNotification(String notification) {
    notifications.add(notification);
  }
  
  // Returns all the notifications sent to the applicant
  public List<String> getNotifications() {
    return notifications;
  }
  
  // Returns the notification located at the index passed through
  public String getNotification(int index) {
    return notifications.get(index);
  }
  
  public void lodgeComplaint(String complaint, Employer employer) {
    employer.addComplaint(complaint);
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  // TODO: accept and reject job offers
  
  @Override
  public boolean equals(Object object) {
    return equals((Applicant) object);
  }
  
  private boolean equals(Applicant applicant) {
    return applicant.getFirstName() == this.getFirstName() &&
            applicant.getLastName() == this.getLastName();
  }
}
