package entities.employer;

import entities.applicant.Applicant;
import driver.ManagementSystem;
import job.Position;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Employer {
  private static int employerCount = 0;
  private int employerId;
  private String employerName;
  private String password;
  private List<Position> positions = new ArrayList<>();
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
  
  public String getPassword() {
    return password;
  }
  
  public List<Position> getPositions() {
    return positions;
  }
  
  public Position getPositionById(int positionId) {
    Position positionMatch = null;
    for (Position position : positions) {
      if (positionId == position.getPositionId()) {
        positionMatch = position;
      }
    }
    return positionMatch;
  }
  
  public Position getPositionByTitle(String positionTitle) {
    Position positionMatch = null;
    for (Position position : positions) {
      if (positionTitle == position.getPositionTitle()) {
        positionMatch = position;
      }
    }
    return positionMatch;
  }
  
  public void addPosition(String title) {
    positions.add(new Position(title, managementSystem));
  }
  
  public List<Applicant> searchForMatchingApplicant(Position position) {
    return new ArrayList<Applicant>();
  }
  
  public void shortlistApplicant(Applicant applicant, Position position) {
    position.shortlistApplicant(applicant);
  }
  
  public void rankApplicants(Position position) {
    return;
  }
  
  public void bookInterview(LocalDate date, LocalTime time, Applicant applicant, Position position) {
    position.addInterview(date, time, applicant);
  }
  
  public void updateApplicant(Applicant applicant, Position position) {
    return;
  }
  
  public void mailApplicant(String mail, Applicant applicant) {
    return;
  }
  
  public void notifyHighRankingApplicants(String notification, Position position) {
    for (Applicant applicant : position.getHighRankingApplicants()) {
      return;
    }
  }
  
  public void offerJob(Applicant applicant, Position position) {
    return;
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Employer) object);
  }
  
  private boolean equals(Employer employer) {
    return employer.employerName == this.employerName;
  }
}
