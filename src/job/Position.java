package job;

import exceptions.ScheduleMultipleInterviewsWithSameApplicantException;
import exceptions.TakenInterviewSlotException;
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
  private List<Applicant> appliedApplicants = new ArrayList<>();
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
  
  public List<Applicant> getAppliedApplicants() {
    return appliedApplicants;
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
  
  public Applicant getApplicantById(Applicant applicant, List<Applicant> applicants) {
    Applicant matchingApplicant = null;
    for (Applicant a : applicants) {
      if (a.getApplicantId() == (applicant.getApplicantId())) {
        matchingApplicant = a;
      }
    }
    return matchingApplicant;
  }
  
  public void addApplicantToAppliedApplicants(Applicant applicant) {
    appliedApplicants.add(applicant);
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
  
  // insert an interview chronologically into the interview slots list
  public void addInterview(LocalDate date, LocalTime time, Applicant applicant)
          throws TakenInterviewSlotException, ScheduleMultipleInterviewsWithSameApplicantException {
    if (!slotIsFree(date, time)) {
      throw new TakenInterviewSlotException();
    } else if (applicantHasBeenScheduled(applicant)) {
      throw new ScheduleMultipleInterviewsWithSameApplicantException();
    } else {
      int size = interviewSlots.size();
      InterviewSlot interviewSlot = new InterviewSlot(date, time, applicant);
      if (size == 0) {
        interviewSlots.add(interviewSlot);
      } else {
        boolean wasAdded = false;
        for (int i = 0; i < size; i++) {
          if (interviewSlot.getDate().isBefore(interviewSlots.get(i).getDate())) {
            interviewSlots.add(i, interviewSlot);
            wasAdded = true;
          } else if (interviewSlot.getDate().equals(interviewSlots.get(i).getDate()) &&
                  interviewSlot.getTime().isBefore(interviewSlots.get(i).getTime())) {
            interviewSlots.add(i, interviewSlot);
            wasAdded = true;
          }
        }
        if (!wasAdded) {
          interviewSlots.add(interviewSlot);
        }
      }
    }
    
  }
  
  public boolean slotIsFree(LocalDate date, LocalTime time) {
    boolean isSlotFree = true;
    for (InterviewSlot i : interviewSlots) {
      if (date.equals(i.getDate()) && time.equals(i.getTime())) {
        isSlotFree = false;
      }
    }
    return isSlotFree;
  }
  
  public boolean applicantHasBeenScheduled(Applicant applicant) {
    boolean hasNotBeenScheduled = false;
    for (InterviewSlot i : interviewSlots) {
      if (applicant.equals(i.getApplicant())) {
        hasNotBeenScheduled = true;
      }
    }
    return hasNotBeenScheduled;
  }
}

