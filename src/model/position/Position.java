package model.position;

import enumerators.ApplicantStatus;
import enumerators.InterviewSlotStatus;
import enumerators.PositionType;
import exceptions.ScheduleMultipleInterviewsWithSameApplicantException;
import exceptions.TakenInterviewSlotException;
import model.applicant.Applicant;
import model.applicant.ApplicantRanking;
import model.applicant.SortByRank;
import model.driver.ManagementSystem;
import model.employer.Employer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Position {
  private static int positionCount = 0;
  private final int TOP_FIVE = 5;
  private int positionId;
  private String positionTitle;
  private PositionType positionType;
  private List<String> applicableJobCategories = new ArrayList<>();
  private double positionHourlyRate;
  private int positionMinHoursPerWeek;
  private int positionMaxHoursPerWeek;
  private List<Applicant> appliedApplicants = new ArrayList<>();
  private List<Applicant> suitableApplicants = new ArrayList<>();
  private List<ApplicantRanking> rankedApplicants = new ArrayList<>();
  private List<Applicant> shortlistedApplicants = new ArrayList<>();
  private List<Applicant> highRankingApplicants = new ArrayList<>();
  private List<Applicant> applicantsJobOfferedTo = new ArrayList<>();
  private List<InterviewSlot> interviewSlots = new LinkedList<>();
  private List<Applicant> unsuccessfulApplicants = new ArrayList<>();
  private Employer positionOwner;
  private ManagementSystem managementSystem;
  
  public Position(String positionTitle,
                  PositionType positionType,
                  double positionHourlyRate,
                  int positionMinHoursPerWeek,
                  int positionMaxHoursPerWeek,
                  Employer positionOwner,
                  ManagementSystem managementSystem) {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = positionTitle;
    this.positionType = positionType;
    this.positionHourlyRate = positionHourlyRate;
    this.positionMinHoursPerWeek = positionMinHoursPerWeek;
    this.positionMaxHoursPerWeek = positionMaxHoursPerWeek;
    this.positionOwner = positionOwner;
    this.managementSystem = managementSystem;
  }
  
  // getters
  public Employer getEmployer() {
    return positionOwner;
  }
  
  public int getPositionId() {
    return positionId;
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
  
  public String getHashMapKey() {
    return positionTitle.toLowerCase();
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
  
  public List<Applicant> getApplicantsJobOfferedTo() {
    return applicantsJobOfferedTo;
  }
  
  public List<InterviewSlot> getInterviewSlots() {
    return interviewSlots;
  }
  
  public List<InterviewSlot> getFreeInterviewSlots() {
    List<InterviewSlot> freeSlots = new ArrayList<>();
    for (InterviewSlot i : interviewSlots) {
      if (i.getStatus() == InterviewSlotStatus.FREE) {
        freeSlots.add(i);
      }
    }
    return freeSlots;
  }
  
  public List<Applicant> getUnsuccessfullApplicants() {
    return unsuccessfulApplicants;
  }
  
  public Applicant getApplicantById(Applicant applicant, List<Applicant> applicants) {
    Applicant matchingApplicant = null;
    for (Applicant a : applicants) {
      if (a.getId() == (applicant.getId())) {
        matchingApplicant = a;
      }
    }
    return matchingApplicant;
  }
  
  public void addApplicableJobCategory(List<String> jobCategories) {
    for (int i = 0; i < jobCategories.size(); i++) {
      addApplicableJobCategory(jobCategories.get(i));
    }
  }
  
  public void addApplicableJobCategory(String jobCategory) {
    String category = jobCategory.toUpperCase();
    if (managementSystem.getJobCategories().contains(category)) {
      applicableJobCategories.add(category);
    }
  }
  
  // if applicant availability is a match and they have at least one jobPreference match
  // add them to the list of suitable candidates
  public void addApplicantToSuitableApplicants(Applicant applicant) {
    boolean isSuitableApplicant = false;
    if (applicant.getAvailability().equals(positionType)) {
      for (String p : applicant.getJobPreferences()) {
        if (applicableJobCategories.contains(p)) {
          isSuitableApplicant = true;
        }
      }
    }
    if (isSuitableApplicant) {
      suitableApplicants.add(applicant);
    }
  }
  
  // ranks candidates according to how many job pref matches they get
  // requires suitable applicants to be determined already and sort
  // by who gets the highest points/rank
  public void setCandidateRankings() {
    int rankCount;
    for (Applicant a : suitableApplicants) {
      rankCount = 0;
      for (String p : a.getJobPreferences()) {
        if (applicableJobCategories.contains(p)) {
          rankCount++;
        }
      }
      rankedApplicants.add(new ApplicantRanking(a, rankCount));
    }
    Collections.sort(rankedApplicants, new SortByRank());
  }
  
  // set the top five applicants
  public void setHighRankingApplicants() {
    List<Applicant> applicants = new ArrayList<>();
    for (int i = 0; i < TOP_FIVE; i++) {
      if (i < rankedApplicants.size()) {
        applicants.add(rankedApplicants.get(i).getApplicant());
      }
    }
  }
  
  public void addApplicantToAppliedApplicants(Applicant applicant) {
    appliedApplicants.add(applicant);
  }
  
  public void addApplicantToJobOffered(Applicant applicant) {
    applicantsJobOfferedTo.add(applicant);
  }
  
  public void addApplicantToUnsuccessfulApplicants(Applicant applicant) {
    unsuccessfulApplicants.add(applicant);
  }
  
  public void addApplicantToShortlist(Applicant applicant) {
    shortlistedApplicants.add(applicant);
  }
  
  private void setApplicantToPending(Applicant applicant) {
    applicant.setStatus(ApplicantStatus.PENDING);
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

