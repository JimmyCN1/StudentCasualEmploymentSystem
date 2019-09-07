package model.user.applicant;

import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.*;
import model.position.Position;
import model.system.ManagementSystem;
import interfaces.UserInterface;
import model.user.applicant.utilities.Job;
import model.user.employer.Employer;
import model.user.Person;

// Questionable
import model.user.applicant.utilities.Reference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Applicant extends Person implements UserInterface {
  private static int applicantCount = 0;
  private final int MAX_AVAILABILITIES = 3;
  private final int TWO_WEEKS = 14;
  private int applicantId;
  private String password;
  private String cv; // The name of the text file
  private ApplicantStatus status;
  private LocalDate lastStudentUpdate;
  private List<PositionType> availabilities;
  private Position jobOffer = null;
  private Position employer = null;
  
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
    lastStudentUpdate = LocalDate.now();
    availabilities.add(availability);
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
  
  public List<PositionType> getAvailabilities() {
    return availabilities;
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
  
  public void addAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    if (!availabilities.contains(availability) && availabilities.size() < MAX_AVAILABILITIES)
      availabilities.add(availability);
  }
  
  public boolean removeAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    return availabilities.remove(availability);
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
  
  // onboards the applicant to the position
  // sets the applicants status to employed
  public void acceptOffer() throws NoJobOfferException, ApplicantNotFoundException {
    if (jobOffer != null) {
      jobOffer.onBoardApplicant(this);
      employer = jobOffer;
      jobOffer = null;
      setStatus(ApplicantStatus.EMPLOYED);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // the position revokes the offer
  // sets the applicants status to available
  public void rejectOffer() throws NoJobOfferException, ApplicantNotFoundException {
    if (jobOffer != null) {
      jobOffer.revokeOffer(this);
      jobOffer = null;
      setStatus(ApplicantStatus.AVAILABLE);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // if the applicant has been inactive for more than two weeks,
  // set the applicants activity to unknown
  public void handleInactivity() {
    if (!status.equals(ApplicantStatus.EMPLOYED) || !status.equals(ApplicantStatus.BLACKLISTED)) {
      if (lastStudentUpdate.compareTo(LocalDate.now()) > TWO_WEEKS) {
        setStatus(ApplicantStatus.UNKNOWN);
      }
    }
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
