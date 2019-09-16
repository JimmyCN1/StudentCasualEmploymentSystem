package model.user.applicant;

import enumerators.UserStatus;
import enumerators.PositionType;
import exceptions.*;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.utilities.*;
import model.user.Person;

// Questionable

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public abstract class Applicant extends Person implements Serializable {
  private static int applicantCount = 0;
  private final int MAX_AVAILABILITIES = 3;
  private final int TWO_WEEKS = 14;
  private int applicantId;
  private String password;
  private String cv; // The name of the text file
  private UserStatus status;
  private LocalDate lastStudentUpdate;
  private List<PositionType> availabilities = new ArrayList<>();
  private Position jobOffer = null;
  private Position currentJob = null;
  
  private List<License> licenses = new ArrayList<>();
  private List<Qualification> qualifications = new ArrayList<>();
  private List<PastJob> pastJobs = new ArrayList<>();
  private List<Reference> references = new ArrayList<>();
  private List<Position> appliedJobs = new ArrayList<>();
  private List<String> jobPreferences = new ArrayList<>();
  
  // List of all notifications the applicant receives from all employers
  private List<Notification> notifications = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public Applicant(String firstName, String lastName, String password, PositionType availability,
                   ManagementSystem managementSystem) {
    super(firstName, lastName, managementSystem);
    applicantCount++;
    this.applicantId = applicantCount;
    setFirstName(firstName);
    setLastName(lastName);
    this.password = password;
    this.status = UserStatus.AVAILABLE;
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
  
  @Override
  public UserStatus getStatus() {
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
  
  @Override
  public void setStatus(UserStatus applicantStatus) {
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
  
  public void addAvailability(String availability)
          throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
    PositionType type = PositionType.determinePositionType(availability);
    if (!availabilities.contains(availability) && availabilities.size() < MAX_AVAILABILITIES)
      availabilities.add(type);
  }
  
  public boolean removeAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    return availabilities.remove(availability);
  }
  
  public boolean removeAvailability(String availability)
          throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
    PositionType type = PositionType.determinePositionType(availability);
    return availabilities.remove(type);
  }
  
  // Adds a notification to the notification list of the applicant
  public void addNotification(Notification notification) {
    notifications.add(notification);
  }
  
  // Returns all the notifications sent to the applicant
  public List<Notification> getNotifications() {
    return notifications;
  }
  
  // Returns the notification located at the index passed through
  public Notification getNotification(int index) {
    return notifications.get(index);
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  // on boards the applicant to the position
  // sets the applicants status to employed
  public void acceptOffer() throws NoJobOfferException, ApplicantNotFoundException {
    // TODO: remove job from applied jobs array?
    if (jobOffer != null) {
      jobOffer.onBoardApplicant(this);
      currentJob = jobOffer;
      jobOffer = null;
      setStatus(UserStatus.EMPLOYED);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // the position revokes the offer
  // sets the applicants status to available
  public void rejectOffer() throws NoJobOfferException, ApplicantNotFoundException {
    // TODO: remove job from applied jobs array?
    if (jobOffer != null) {
      jobOffer.revokeOffer(this);
      jobOffer = null;
      setStatus(UserStatus.AVAILABLE);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // if the applicant has been inactive for more than two weeks,
  // set the applicants activity to unknown
  public void handleInactivity() {
    if (!status.equals(UserStatus.EMPLOYED) || !status.equals(UserStatus.BLACKLISTED)) {
      if (lastStudentUpdate.compareTo(LocalDate.now()) > TWO_WEEKS) {
        setStatus(UserStatus.UNKNOWN);
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
  
  @Override
  public String toString() {
    return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n",
            nameToString(),
            statusToString(),
            lastUpdateToString(),
            availabilitiesToString(),
            jobPreferencesToString(),
            appliedJobsToString(),
            jobOfferToString(),
            employerToString(),
            complaintsToString());
  }
  
  @Override
  public String statusToString() {
    return String.format("Status: %s", getStatus().toString());
  }
  
  public String lastUpdateToString() {
    return String.format("Number Of Days Since Last Update: %d",
            Math.abs(Period.between(lastStudentUpdate, LocalDate.now()).getDays()));
  }
  
  public String availabilitiesToString() {
    String availabilities = "";
    for (PositionType availability : getAvailabilities()) {
      availabilities += String.format("%s ", availability);
    }
    return String.format("Availabilities: %s", availabilities);
  }
  
  public String jobPreferencesToString() {
    String preferences = "";
    for (String pref : getJobPreferences()) {
      preferences += String.format("%s ", pref);
    }
    return String.format("Availabilities: %s", preferences);
  }
  
  public String jobOfferToString() {
    return String.format("Current Job Offer: %s - %s",
            jobOffer.getEmployer(),
            jobOffer.getTitle());
  }
  
  public String employerToString() {
    return String.format("Current Employer: %s", currentJob.getEmployer());
  }
  
  public String notificationsToString() {
    String notifications = "";
    for (Notification notification : this.notifications) {
      notifications += String.format("%s\n\n", notification.toString());
    }
    return notifications;
  }
  
  public String appliedJobsToString() {
    String jobs = "";
    for (Position p : appliedJobs) {
      jobs += String.format("Employer: %s, Position Title: %s\n",
              p.getEmployer().getName(),
              p.getTitle());
    }
    return jobs;
  }
}
