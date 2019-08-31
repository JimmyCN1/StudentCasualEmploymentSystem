package job;

import interfaces.applicant.Applicant;
import driver.ManagementSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Position {
  private static int positionCount = 0;
  private int positionId;
  private String positionTitle;
  private String positionType;
  private double positionHourlyRate;
  private int positionMinHoursPerWeek;
  private int positionMaxHoursPerWeek;
  private List<Applicant> applicants = new ArrayList<>();
  private List<Applicant> shortlistedApplicants = new ArrayList<>();
  private List<Applicant> highRankingApplicants = new ArrayList<>();
  private List<Applicant> jobOffered = new ArrayList<>();
  private List<InterviewSlot> interviewSlots = new LinkedList<>();
  private List<Applicant> unsuccessfulApplicants = new ArrayList<>();
  private ManagementSystem managementSystem;
  
  public Position(String positionTitle,
                  String positionType,
                  double positionHourlyRate,
                  int positionMinHoursPerWeek,
                  int positionMaxHoursPerWeek,
                  ManagementSystem managementSystem) {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = positionTitle;
    this.positionType = positionType;
    this.positionHourlyRate = positionHourlyRate;
    this.positionMinHoursPerWeek = positionMinHoursPerWeek;
    this.positionMaxHoursPerWeek = positionMaxHoursPerWeek;
    this.managementSystem = managementSystem;
  }
  
  // getters
  public int getPositionId() {
    return positionId;
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
  
  public List<Applicant> getApplicants() {
    return applicants;
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
    return unsuccessfulApplicants;
  }
  
  //
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
  
  public void addApplicantToJobOffered(Applicant applicant) {
    jobOffered.add(applicant);
  }
  
  public void addApplicantToUnsuccessfulApplicants(Applicant applicant) {
    unsuccessfulApplicants.add(applicant);
  }
  
  public void addApplicantToShortlist(Applicant applicant) {
    shortlistedApplicants.add(applicant);
  }
  
  private void setApplicantToPending(Applicant applicant) {
    applicant.setPending();
  }
}

