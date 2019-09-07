package model.employer;

import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.InterviewSlotNotFoundException;
import exceptions.PositionNotFoundException;
import exceptions.InterviewSlotClashException;
import interfaces.User;
import model.applicant.Applicant;
import model.position.InterviewSlot;
import model.system.ManagementSystem;
import model.position.Position;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employer implements User {
  private static int employerCount = 0;
  private int employerId;
  private String employerName;
  private String password;
  
  private Map<String, Position> positions = new HashMap<>();
  private List<String> complaints = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public Employer(String employerName, String password, ManagementSystem managementSystem) {
    employerCount++;
    this.employerId = employerCount;
    this.employerName = employerName;
    this.password = password;
    this.managementSystem = managementSystem;
  }
  
  // Getters
  public static int getEmployerCount() {
    return employerCount;
  }
  
  public int getId() {
    return employerId;
  }
  
  @Override
  public String getHashMapKey() {
    return employerName.toLowerCase();
  }
  
  @Override
  public String getName() {
    return employerName;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  public List<String> getComplaints() {
    return complaints;
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
      if (positionId == this.positions.get(p).getPositionId()) {
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
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  public void addComplaint(String complaint) {
    complaints.add(complaint);
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
    applicant.addNotification(
            String.format("You have been shortlisted for %s!", position.getPositionTitle())
    );
  }
  
  public void bookInterview(LocalDate date, LocalTime time, Applicant applicant, Position position)
          throws InterviewSlotClashException, InterviewSlotNotFoundException {
    InterviewSlot interviewSlot = new InterviewSlot(date, time);
    position.addInterview(date, time);
    position.bookInterviewForApplicant(applicant,
            position.getInterviewSlot(interviewSlot.getDate(), interviewSlot.getTime()));
    interviewSlot.bookApplicant(applicant);
    
  }
  
  // TODO: to implement the update applicant method with respect to the passed position
  public void updateApplicant(Applicant applicant, Position position) {
  
  }
  
  // the passed applicants will receive a string notification of the string passed
  public void notifyApplicants(List<Applicant> applicants, String notification) {
    for (Applicant applicant : applicants) {
      applicant.addNotification(notification);
    }
  }
  
  // the passed applicant will receive a string notification of the string passed
  public void notifyApplicant(Applicant applicant, String notification) {
    applicant.addNotification(notification);
  }
  
  // position list of applicants job offered to will be updated with the passed applicant
  // and the applicants status is set to pending
  public void offerJob(Applicant applicant, Position position) {
    position.addApplicantToJobOffered(applicant);
    applicant.setStatus(ApplicantStatus.PENDING);
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
  
  // complaint will be lodged against the passed applicant
  public void lodgeComplaint(Applicant applicant, String complaint) {
    applicant.addComplaint(complaint);
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Employer) object);
  }
  
  private boolean equals(Employer employer) {
    return employer.getId() == this.getId() && employer.getName() == this.getName();
  }
}
