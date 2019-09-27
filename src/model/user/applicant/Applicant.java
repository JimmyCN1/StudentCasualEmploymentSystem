package model.user.applicant;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import model.position.InterviewSlot;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.Person;
import model.user.applicant.utilities.*;

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
  private String cv; // The name of the text file
  private UserStatus status;
  private LocalDate lastStudentUpdate;
  private List<PositionType> availabilities = new ArrayList<>();
  private List<InterviewSlot> interviewSlots = new ArrayList<>();
  private List<Position> shortlisted = new ArrayList<>();
  private List<Position> appliedPositions = new ArrayList<>();
  private Position jobOffer = null;
  private Position currentJob = null;
  
  private List<License> licenses = new ArrayList<>();
  private List<PastJob> pastJobs = new ArrayList<>();
  private List<Qualification> qualifications = new ArrayList<>();
  private List<Reference> references = new ArrayList<>();
  private List<Position> appliedJobs = new ArrayList<>();
  private List<Position> interviewsOffered = new ArrayList<>();
  private List<String> jobPreferences = new ArrayList<>();
  
  // List of all notifications the applicant receives from all employers
  private List<Notification> notifications = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public Applicant(String firstName, String lastName, String password, PositionType availability,
                   ManagementSystem managementSystem) {
    super(firstName, lastName, password, managementSystem);
    applicantCount++;
    this.applicantId = applicantCount;
    setFirstName(firstName);
    setLastName(lastName);
    this.status = UserStatus.AVAILABLE;
    this.lastStudentUpdate = LocalDate.now();
    this.availabilities.add(availability);
    this.managementSystem = managementSystem;
  }
  
  @Override
  public int getId() {
    return this.applicantId;
  }
  
  @Override
  public UserStatus getStatus() {
    return this.status;
  }
  
  public List<PositionType> getAvailabilities() {
    return this.availabilities;
  }
  
  
  public List<Position> getAppliedPositions() {
    return appliedPositions;
  }
  
  public List<InterviewSlot> getInterviewSlots() {
    return this.interviewSlots;
  }
  
  public String getCv() {
    return this.cv;
  }
  
  public List<String> getJobPreferences() {
    return this.jobPreferences;
  }
  
  public List<License> getLicenses() {
    return licenses;
  }
  
  public List<PastJob> getPastJobs() {
    return pastJobs;
  }
  
  public List<Qualification> getQualifications() {
    return qualifications;
  }
  
  public List<Reference> getReferences() {
    return references;
  }
  
  public List<Position> getAppliedJobs() {
    return appliedJobs;
  }
  
  public List<Position> getInterviewsOffered() {
    return interviewsOffered;
  }
  
  public List<Position> getShortlisted() {
    return shortlisted;
  }
  
  public void addShortlisted(Position position) {
    shortlisted.add(position);
  }
  
  @Override
  public void setStatus(UserStatus applicantStatus) {
    this.status = applicantStatus;
  }
  
  public void setJobOffer(Position jobOffer) {
	  this.jobOffer = jobOffer;
  }
  
  public void addJobPreference(String jobPreference)
          throws InvalidJobCategoryException {
    String jobPref = jobPreference.toUpperCase();
    if (this.managementSystem.getJobCategories().contains(jobPref)) {
      this.jobPreferences.add(jobPref);
    } else {
      throw new InvalidJobCategoryException();
    }
  }
  
  // adds an interview slot to an applicant list of interview slots
  public void addInterviewSlot(InterviewSlot interviewSlot) {
    boolean addInterview = true;
    for (InterviewSlot i : interviewSlots) {
      if (i.equals(interviewSlot)) {
        addInterview = false;
      }
    }
    if (addInterview) {
      interviewSlot.bookApplicant(this);
      interviewSlots.add(interviewSlot);
      jobOffer.addApplicantToInterviewedCandidates(this);
    }
  }
  
  public boolean removeJobPreference(String jobPreference)
          throws JobCategoryNotFoundException {
    String jobPref = jobPreference.toUpperCase();
    boolean wasPreferenceRemoved = false;
    if (!this.managementSystem.getJobCategories().contains(jobPref)) {
      throw new JobCategoryNotFoundException();
    } else {
      if (this.jobPreferences.contains(jobPref)) {
        this.jobPreferences.remove(jobPref);
        wasPreferenceRemoved = true;
      }
    }
    return wasPreferenceRemoved;
  }
  
  public void addAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    if (!this.availabilities.contains(availability) && this.availabilities.size() < this.MAX_AVAILABILITIES)
      this.availabilities.add(availability);
  }
  
  public void addAvailability(String availability)
          throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
    PositionType type = PositionType.determinePositionType(availability);
    if (!this.availabilities.contains(availability) && this.availabilities.size() < this.MAX_AVAILABILITIES)
      this.availabilities.add(type);
  }
  
  public boolean removeAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    return this.availabilities.remove(availability);
  }
  
  public boolean removeAvailability(String availability)
          throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
    PositionType type = PositionType.determinePositionType(availability);
    return this.availabilities.remove(type);
  }
  
  public void applyToPosition(Position position) {
    if (!position.getAppliedApplicants().contains(this)) {
      appliedPositions.add(position);
      position.addApplicantToAppliedApplicants(this);
    }
  }
  
  // Adds a notification to the notification list of the applicant
  public void addNotification(Notification notification) {
    this.notifications.add(notification);
  }
  
  public void addPositionToInterviewOffered(Position position) {
    if (!interviewsOffered.contains(position)) {
      interviewsOffered.add(position);
    }
  }
  
  public Position removePositionFromInterviewOffered(int position) {
    return interviewsOffered.remove(position);
  }
  
  // Returns all the notifications sent to the applicant
  public List<Notification> getNotifications() {
    return this.notifications;
  }
  
  // Returns the notification located at the index passed through
  public Notification getNotification(int index) {
    return this.notifications.get(index);
  }
  
  // on boards the applicant to the position
  // sets the applicants status to employed
  public void acceptOffer() throws NoJobOfferException, ApplicantNotFoundException {
    if (this.jobOffer != null) {
      this.jobOffer.onBoardApplicant(this);// sets applicant to employed and adjusts related
      // Employer.job and Applicant variables, also adds applicant to the staff list
      this.currentJob = this.jobOffer;
      this.jobOffer = null;
      setStatus(UserStatus.EMPLOYED);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // the position revokes the offer
  // sets the applicants status to available
  public void rejectOffer() throws NoJobOfferException, ApplicantNotFoundException {
    if (this.jobOffer != null) {
      this.jobOffer.revokeOffer(this);
      jobOffer.addApplicantToApplicantsWhichRejectedOffer(this);
      this.jobOffer = null;
      setStatus(UserStatus.AVAILABLE);
    } else {
      throw new NoJobOfferException();
    }
  }
  
  // if the applicant has been inactive for more than two weeks,
  // set the applicants activity to unknown
  public void handleInactivity() {
    if (!this.status.equals(UserStatus.EMPLOYED) || !this.status.equals(UserStatus.BLACKLISTED)) {
      if (this.lastStudentUpdate.compareTo(LocalDate.now()) > this.TWO_WEEKS) {
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
    return String.format("%s\n\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
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
            Math.abs(Period.between(this.lastStudentUpdate, LocalDate.now()).getDays()));
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
  
  public String jobOfferToString() throws NullPointerException {
    if (this.jobOffer == null) {
      return "Current Job Offer: None";
    } else {
      return String.format("Current Job Offer: %s - %s",
              this.jobOffer.getEmployer(),
              this.jobOffer.getTitle());
    }
  }
  
  public String employerToString() throws NullPointerException {
    if (currentJob == null) {
      return String.format("Current Employer : Unemployed");
    } else {
      return String.format("Current Employer: %s", currentJob.getEmployer());
    }
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
    for (Position p : this.appliedJobs) {
      jobs += String.format("Employer: %s, Position Title: %s\n",
              p.getEmployer().getName(),
              p.getTitle());
    }
    return jobs;
  }
  
  public void addLicense(License license) {
    this.licenses.add(license);
  }
  
  public License removeLicense(int licenseIndex) {
    return this.licenses.remove(licenseIndex);
  }
  
  public void addPastJob(PastJob pastJob) {
    this.pastJobs.add(pastJob);
  }
  
  public PastJob removePastJob(int pastJobIndex) {
    return this.pastJobs.remove(pastJobIndex);
  }
  
  public void addQualification(Qualification qualification) {
    this.qualifications.add(qualification);
  }
  
  public Qualification removeQualification(int qualificationIndex) {
    return this.qualifications.remove(qualificationIndex);
  }
  
  public void addReference(Reference reference) {
    this.references.add(reference);
  }
  
  public Reference removeReference(int referenceIndex) {
    return this.references.remove(referenceIndex);
  }
  
  
}
