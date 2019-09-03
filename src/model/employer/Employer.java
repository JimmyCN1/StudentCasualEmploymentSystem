package model.employer;

import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.ScheduleMultipleInterviewsWithSameApplicantException;
import exceptions.TakenInterviewSlotException;
import interfaces.Entity;
import model.applicant.Applicant;
import model.driver.ManagementSystem;
import model.position.Position;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employer implements Entity {
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
  
  // getters
  public static int getEmployerCount() {
    return employerCount;
  }
  
  public int getEmployerId() {
    return employerId;
  }
  
  public String getEmployerName() {
    return employerName;
  }
  
  public String getHashMapKey() {
    return employerName.toLowerCase();
  }
  
  @Override
  public String getName() {
    return getEmployerName();
  }
  
  public List<String> getComplaints() {
    return complaints;
  }
  
  public List<Position> getPositions() {
    List<Position> positionsArray = new ArrayList<>();
    for (String p : this.positions.keySet()) {
      positionsArray.add(this.positions.get(p));
    }
    return positionsArray;
  }
  
  public Position getPositionById(int positionId) {
    Position matchingPosition = null;
    for (String p : this.positions.keySet()) {
      if (positionId == this.positions.get(p).getPositionId()) {
        matchingPosition = this.positions.get(p);
      }
    }
    return matchingPosition;
  }
  
  public Position getPositionByTitle(String positionTitle) {
    return positions.get(positionTitle.toLowerCase());
  }
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  public void addComplaint(String complaint) {
    complaints.add(complaint);
  }
  
  public void addPosition(String title, PositionType type, double hourlyRate, int minHoursPerWeek, int maxHoursPerWeek) {
    positions.put(title.toLowerCase(), new Position(title, type, hourlyRate, minHoursPerWeek, maxHoursPerWeek, this, managementSystem));
  }
  
  public void addPosition(Position position) {
    positions.put(position.getHashMapKey(), position);
  }
  
  public List<Applicant> searchForMatchingApplicant(Position position) {
    return new ArrayList<Applicant>();
  }
  
  public void shortlistApplicant(Applicant applicant, Position position) {
    position.addApplicantToShortlist(applicant);
  }
  
  // TODO:
  public void rankApplicants(Position position) {
    return;
  }
  
  public void bookInterview(LocalDate date, LocalTime time, Applicant applicant, Position position)
          throws TakenInterviewSlotException, ScheduleMultipleInterviewsWithSameApplicantException {
    position.addInterview(date, time, applicant);
  }
  
  // TODO:
  public void updateApplicant(Applicant applicant, Position position) {
    return;
  }
  
  public void notifyApplicants(String notification, List<Applicant> applicants) {
    for (Applicant applicant : applicants) {
      applicant.fetchNotification(notification);
    }
  }
  
  public void offerJob(Applicant applicant, Position position) {
    position.addApplicantToJobOffered(applicant);
    applicant.setStatus(ApplicantStatus.PENDING);
  }
  
  public void handleUnsuccessfulApplicants(Position position) {
    for (Applicant applicant : position.getAppliedApplicants()) {
      if (!position.getApplicantsJobOfferedTo().contains(applicant)) {
        position.addApplicantToUnsuccessfulApplicants(applicant);
      }
    }
  }
  
  public void lodgeComplaint(String complaint, Applicant applicant) {
    applicant.addComplaint(complaint);
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Employer) object);
  }
  
  private boolean equals(Employer employer) {
    return employer.employerName == this.employerName;
  }
}
