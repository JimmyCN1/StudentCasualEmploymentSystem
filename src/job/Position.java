package job;

import entities.applicant.Applicant;
import driver.ManagementSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Position {
  private static int positionCount = 0;
  private int positionId;
  private String positionTitle;
  // private List<(Enum) Shedule.>;
  private List<Applicant> shortlistedApplicants;
  private List<Applicant> highRankingApplicants;
  private List<Applicant> jobOffered;
  private List<InterviewSlot> interviewSlots = new LinkedList<>();
  private List<Applicant> unsuccessfullApplicants;
  private ManagementSystem managementSystem;
  
  public Position(String positionTitle, ManagementSystem managementSystem) {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = positionTitle;
    this.managementSystem = managementSystem;
  }
  
  public Position() {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = "Unknown";
    this.managementSystem = managementSystem;
  }
  
  // getters
  public int getPositionId() {
    return positionId;
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
  
  public List<Applicant> getShortListedApplicants() {
    return shortlistedApplicants;
  }
  
  public List<Applicant> getHighRankingApplicants() {
    return highRankingApplicants;
  }
  
  public List<Applicant> getJobOffered() {
    return jobOffered;
  }
  
  public List<InterviewSlot> getInterviewSlots() {
    return interviewSlots;
  }
  
  public List<Applicant> getUnsuccessfullApplicants() {
    return unsuccessfullApplicants;
  }
  
  public void addInterview(LocalDate date, LocalTime time, Applicant applicant) {
    InterviewSlot interviewSlot = new InterviewSlot(date, time, applicant);
    if (interviewSlots.size() == 0) {
      interviewSlots.add(interviewSlot);
    } else {
      boolean wasAdded = false;
      for (int i = 0; i < interviewSlots.size(); i++) {
        if (interviewSlot.getDate().isBefore(interviewSlots.get(i).getDate()) &&
                interviewSlot.getTime().isBefore(interviewSlots.get(i).getTime())) {
          interviewSlots.add(i, interviewSlot);
        }
      }
      if (!wasAdded) {
        interviewSlots.add(interviewSlot);
      }
    }
  }
  
  public void shortlistApplicant(Applicant applicant) {
    shortlistedApplicants.add(applicant);
  }
  
  private void setPending(Applicant applicant) {
    applicant.setPending();
  }
}

