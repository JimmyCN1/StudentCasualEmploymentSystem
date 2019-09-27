package model.user.employer;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import model.position.InterviewSlot;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.applicant.utilities.Notification;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employer extends User implements Serializable {
  private static int employerCount = 0;
  private int id;
  private String name;
  private UserStatus status = null;
  private String email;
  private String phoneNumber;
  
  private Map<String, Position> positions = new HashMap<>();
  
  private ManagementSystem managementSystem;
  
  public Employer(String name, String password, ManagementSystem managementSystem) {
    super(name, password, managementSystem);
    employerCount++;
    this.id = employerCount;
    this.name = name;
    this.status = UserStatus.AVAILABLE;
    this.managementSystem = managementSystem;
  }
  
  // Getters
  public static int getEmployerCount() {
    return employerCount;
  }
  
  @Override
  public int getId() {
    return id;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public UserStatus getStatus() {
    return status;
  }
  
  public String getEmail() {
    return email;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  // returns an array of all the positions that the employer has posted
  public List<Position> getPositions() {
    List<Position> positionsArray = new ArrayList<>();
    
    for (String p : this.positions.keySet()) {
      positionsArray.add(this.positions.get(p));
    }
    
    return positionsArray;
  }
  
  // if the positionId exists, returns the matching position, else throws an exception
  public Position getPositionById(int positionId) throws PositionNotFoundException {
    Position matchingPosition = null;
    
    for (String p : this.positions.keySet()) {
      if (positionId == this.positions.get(p).getId()) {
        matchingPosition = this.positions.get(p);
      }
    }
    
    if (matchingPosition == null) {
      throw new PositionNotFoundException();
    }
    
    return matchingPosition;
  }
  
  // if the positionTitle exists, returns the matching position, else throws an exception
  public Position getPositionByTitle(String positionTitle) throws PositionNotFoundException {
    Position p = null;
    p = positions.get(positionTitle.toLowerCase());
    
    if (p == null) {
      throw new PositionNotFoundException();
    }
    
    return p;
  }
  
  public List<PositionType> getApplicantAvailability(Applicant applicant)
          throws UserBlacklistedException {
    if (status.equals(UserStatus.BLACKLISTED)) {
      throw new UserBlacklistedException();
    } else {
      return applicant.getAvailabilities();
    }
  }
  
  // returns an array of all the positions that the employer has posted
  public List<Position> getPositionsAsList() {
    List<Position> positions = new ArrayList<>();
    for (String e : this.positions.keySet()) {
      positions.add(this.positions.get(e));
    }
    return positions;
  }
  
  // employer status must only be AVAILABLE or BLACKLISTED
  @Override
  public void setStatus(UserStatus employerStatus) throws InvalidUserStatusException {
    if (employerStatus != UserStatus.BLACKLISTED || employerStatus != UserStatus.AVAILABLE) {
      throw new InvalidUserStatusException();
    }
    this.status = employerStatus;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.getPassword().equals(password);
  }
  
  // creates and adds a new position to the positions map
  public void addPosition(String title, PositionType type, double hourlyRate, int minHoursPerWeek, int maxHoursPerWeek) {
    positions.put(title.toLowerCase(), new Position(title, type, hourlyRate, minHoursPerWeek, maxHoursPerWeek, this, managementSystem));
  }
  
  // adds a new position to the positions map
  public void addPosition(Position position) {
    positions.put(position.getHashMapKey(), position);
  }
  
  // filters and ranks the applicants that applied to the passed position
  public void rankApplicants(Position position) {
    position.filterApplicants();
  }
  
  public void shortlistApplicant(Applicant applicant, Position position) {
    position.addApplicantToShortlist(applicant);
    applicant.addNotification(new Notification(
            String.format("You have been shortlisted for %s!", position.getTitle()),
            this));
    applicant.addShortlisted(position);
  }
  
  public void bookInterview(LocalDate date, LocalTime time, Applicant applicant, Position position)
          throws InterviewSlotClashException, InterviewSlotNotFoundException {
    InterviewSlot interviewSlot = new InterviewSlot(date, time, applicant, position, this);
    position.addInterview(date, time);
    position.bookInterviewForApplicant(applicant,
            position.getInterviewSlot(interviewSlot.getDate(), interviewSlot.getTime()));
    interviewSlot.bookApplicant(applicant);
    
  }
  
  // the passed applicants will receive a string notification of the string passed
  public void notifyApplicants(List<Applicant> applicants, Notification notification) {
    for (Applicant applicant : applicants) {
      applicant.addNotification(notification);
    }
  }
  
  // the passed applicant will receive a string notification of the string passed
  public void notifyApplicant(Applicant applicant, Notification notification) {
    applicant.addNotification(notification);
  }
  
  // position list of applicants job offered to will be updated with the passed applicant
  // and the applicants status is set to pending
  public void offerInterview(Applicant applicant, Position position) throws UserBlacklistedException {
    if (applicant.getStatus().equals(UserStatus.BLACKLISTED)) {
      throw new UserBlacklistedException();
    } else {
      position.addApplicantToInterviewOffered(applicant);
      applicant.addPositionToInterviewOffered(position);
    }
  }
  
  // position list of applicants job offered to will be updated with the passed applicant
  // and the applicants status is set to pending
  public void offerJob(Applicant applicant, Position position) throws UserBlacklistedException {
    if (applicant.getStatus().equals(UserStatus.BLACKLISTED)) {
      throw new UserBlacklistedException();
    } else {
      position.addApplicantToJobOffered(applicant);
      applicant.setStatus(UserStatus.PENDING);
      applicant.setJobOffer(position);
    }
  }
  
  // when called, all the unsuccessful applicants will be added to added to the
  // passed positions unsuccessful applicants list
  public void handleUnsuccessfulApplicants(Position position) {
    for (Applicant applicant : position.getAppliedApplicants()) {
      if (!position.getApplicantsJobOfferedTo().contains(applicant)) {
        position.addApplicantToUnsuccessfulApplicants(applicant);
      }
    }
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Employer) object);
  }
  
  private boolean equals(Employer employer) {
    return employer.getId() == this.getId() && employer.getName() == this.getName();
  }
  
  @Override
  public String nameToString() {
    return String.format("Name: %s", getName());
  }
  
  @Override
  public String statusToString() {
    return String.format("Status: %s", getStatus());
  }
  
  public String toStringVerbose() {
    String verboseString = super.toString();
    verboseString += String.format(
            "Email: %s\n" +
                    "Phone Number: %s",
            email, phoneNumber);
    return verboseString;
  }
  
  public String listToStringAsOrderedList(List<Position> positions) {
    String positionsString = "";
    for (int i = 0; i < positions.size(); i++) {
      positionsString += String.format("%d. %s", i + 1, positions.get(i).getTitle());
    }
    return positionsString;
  }
}
