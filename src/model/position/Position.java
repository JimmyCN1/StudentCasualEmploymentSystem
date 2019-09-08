package model.position;

import enumerators.UserStatus;
import enumerators.InterviewSlotStatus;
import enumerators.PositionType;
import exceptions.*;
import model.user.applicant.Applicant;
import model.user.applicant.ApplicantRanking;
import model.user.applicant.SortByRank;
import model.system.ManagementSystem;
import model.user.employer.Employer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Position {
  private static int positionCount = 0;
  private final int TOP_APPLICANTS_CUTOFF = 5;
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
  private List<Applicant> shortlistedByEmployer = new ArrayList<>();
  private List<Applicant> highRankingApplicants = new ArrayList<>();
  private List<Applicant> applicantsJobOfferedTo = new ArrayList<>();
  private List<InterviewSlot> interviewSlots = new LinkedList<>();
  private List<Applicant> unsuccessfulApplicants = new ArrayList<>();
  private List<Applicant> staff = new ArrayList<>();
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
  
  public List<String> getApplicableJobCategories() {
    return applicableJobCategories;
  }
  
  public List<Applicant> getAppliedApplicants() {
    return appliedApplicants;
  }
  
  public List<Applicant> getShortListedApplicants() {
    return shortlistedByEmployer;
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
  
  // returns the open interview slots
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
  
  // returns the matching applicant from the passed applicant list
  public Applicant getApplicant(Applicant applicant, List<Applicant> applicants)
          throws UserNotFoundException {
    Applicant matchingApplicant = null;
    for (Applicant a : applicants) {
      if (a.getId() == (applicant.getId())) {
        matchingApplicant = a;
      }
    }
    if (matchingApplicant == null) {
      throw new UserNotFoundException();
    }
    return matchingApplicant;
  }
  
  // returns the interview slot with the matching date and time
  public InterviewSlot getInterviewSlot(LocalDate date, LocalTime time)
          throws InterviewSlotNotFoundException {
    InterviewSlot slot = null;
    for (InterviewSlot i : interviewSlots) {
      if (date.equals(i.getDate()) && time.equals(i.getTime())) {
        slot = i;
      }
    }
    if (slot == null) {
      throw new InterviewSlotNotFoundException();
    }
    return slot;
  }
  
  // adds all the valid job categories to the list of applicable job categories
  public void addApplicableJobCategory(List<String> jobCategories)
          throws InvalidJobCategoryException {
    for (int i = 0; i < jobCategories.size(); i++) {
      addApplicableJobCategory(jobCategories.get(i));
    }
  }
  
  // add the passed job category to the list of applicable job categories
  public void addApplicableJobCategory(String jobCategory)
          throws InvalidJobCategoryException {
    String category = jobCategory.toUpperCase();
    if (managementSystem.getJobCategories().contains(category) &&
            !getAppliedApplicants().contains(jobCategory)) {
      applicableJobCategories.add(category);
    } else {
      throw new InvalidJobCategoryException();
    }
  }
  
  // determine suitable applicants from the applied applicants
  // rank each candidate
  // sort candidates and determine top five candidates
  public void filterApplicants() {
    for (Applicant a : getAppliedApplicants()) {
      addApplicantToSuitableApplicants(a);
    }
    setCandidateRankings();
    setHighRankingApplicants();
  }
  
  // if applicant availability is a match and they have at least one jobPreference match
  // add them to the list of suitable candidates
  private void addApplicantToSuitableApplicants(Applicant applicant) {
    if (!suitableApplicants.contains(applicant)) {
      boolean isSuitableApplicant = false;
      if (applicant.getAvailabilities().contains(positionType)) {
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
  }
  
  // ranks candidates according to how many job pref matches they get
  // requires suitable applicants to be determined already and sort
  // by who gets the highest points/rank
  private void setCandidateRankings() {
    List<ApplicantRanking> applicants = new ArrayList<>();
    int rankCount;
    for (Applicant a : suitableApplicants) {
      rankCount = 0;
      for (String p : a.getJobPreferences()) {
        if (applicableJobCategories.contains(p)) {
          rankCount++;
        }
      }
      applicants.add(new ApplicantRanking(a, rankCount));
    }
    Collections.sort(applicants, new SortByRank());
    rankedApplicants = applicants;
  }
  
  // set the top five applicants
  private void setHighRankingApplicants() {
    List<Applicant> applicants = new ArrayList<>();
    for (int i = 0; i < TOP_APPLICANTS_CUTOFF; i++) {
      if (i < rankedApplicants.size()) {
        applicants.add(rankedApplicants.get(i).getApplicant());
      }
    }
    highRankingApplicants = applicants;
  }
  
  // adds the passed applicant to the applied applicant list
  // should be invoked when the applicant applied to the position
  public void addApplicantToAppliedApplicants(Applicant applicant) {
    if (!appliedApplicants.contains(applicant)) {
      appliedApplicants.add(applicant);
    }
  }
  
  // adds the passed applicant to the job offered list
  // should be invoked when the employer offers an applicant a job
  public void addApplicantToJobOffered(Applicant applicant) {
    if (!applicantsJobOfferedTo.contains(applicant) &&
            !applicant.getStatus().equals(UserStatus.PENDING)) {
      applicantsJobOfferedTo.add(applicant);
    }
  }
  
  // adds the passed applicant to the unsuccessful applicants list
  // should be invoked after all the job offers have been made
  public void addApplicantToUnsuccessfulApplicants(Applicant applicant) {
    if (!unsuccessfulApplicants.contains(applicant)) {
      unsuccessfulApplicants.add(applicant);
    }
  }
  
  // adds the passed applicant to the shortlist
  // should be invoked when the employer wishes to shortlist a particular applicant
  public void addApplicantToShortlist(Applicant applicant) {
    if (!shortlistedByEmployer.contains(applicant)) {
      shortlistedByEmployer.add(applicant);
    }
  }
  
  // insert an interview chronologically into the interview slots list before applicant has been assigned to slot
  public void addInterview(LocalDate date, LocalTime time)
          throws InterviewSlotClashException {
    if (!slotIsFree(date, time)) {
      throw new InterviewSlotClashException();
    } else {
      int size = interviewSlots.size();
      InterviewSlot interviewSlot = new InterviewSlot(date, time);
      if (size == 0) {
        interviewSlots.add(interviewSlot);
      } else {
        boolean wasAdded = false;
        for (int i = 0; i < size; i++) {
          if (!wasAdded) {
            if (interviewSlot.getDate().isBefore(interviewSlots.get(i).getDate())) {
              interviewSlots.add(i, interviewSlot);
              wasAdded = true;
            } else if (interviewSlot.getDate().equals(interviewSlots.get(i).getDate()) &&
                    interviewSlot.getTime().isBefore(interviewSlots.get(i).getTime())) {
              interviewSlots.add(i, interviewSlot);
              wasAdded = true;
            }
          }
        }
        if (!wasAdded) {
          interviewSlots.add(interviewSlot);
        }
      }
    }
  }
  
  // insert an interview chronologically into the interview slots list after applicant has been assigned to slot
  public void addInterview(LocalDate date, LocalTime time, Applicant applicant)
          throws InterviewSlotClashException, ApplicantAlreadyBookedException {
    if (!slotIsFree(date, time)) {
      throw new InterviewSlotClashException();
    } else if (applicantHasBeenScheduled(applicant)) {
      throw new ApplicantAlreadyBookedException();
    } else {
      int size = interviewSlots.size();
      InterviewSlot interviewSlot = new InterviewSlot(date, time, applicant);
      if (size == 0) {
        interviewSlots.add(interviewSlot);
      } else {
        boolean wasAdded = false;
        for (int i = 0; i < size; i++) {
          if (!wasAdded) {
            if (interviewSlot.getDate().isBefore(interviewSlots.get(i).getDate())) {
              interviewSlots.add(i, interviewSlot);
              wasAdded = true;
            } else if (interviewSlot.getDate().equals(interviewSlots.get(i).getDate()) &&
                    interviewSlot.getTime().isBefore(interviewSlots.get(i).getTime())) {
              interviewSlots.add(i, interviewSlot);
              wasAdded = true;
            }
          }
        }
        if (!wasAdded) {
          interviewSlots.add(interviewSlot);
        }
      }
    }
  }
  
  // assigns the passed applicant to the passed interview slot
  public void bookInterviewForApplicant(Applicant applicant, InterviewSlot interviewSlot) {
    interviewSlot.bookApplicant(applicant);
  }
  
  private boolean slotIsFree(LocalDate date, LocalTime time) {
    boolean isSlotFree = true;
    for (InterviewSlot i : interviewSlots) {
      if (date.equals(i.getDate()) && time.equals(i.getTime())) {
        isSlotFree = false;
      }
    }
    return isSlotFree;
  }
  
  private boolean applicantHasBeenScheduled(Applicant applicant) {
    boolean hasNotBeenScheduled = false;
    for (InterviewSlot i : interviewSlots) {
      if (applicant.equals(i.getApplicant())) {
        hasNotBeenScheduled = true;
      }
    }
    return hasNotBeenScheduled;
  }
  
  // adds the applicant to staff, removes applicant from jobOfferedTo list
  // sets the applicants status to available
  public void onBoardApplicant(Applicant applicant) throws ApplicantNotFoundException {
    if (!applicantsJobOfferedTo.contains(applicant)) {
      throw new ApplicantNotFoundException();
    } else {
      staff.add(applicant);
      applicantsJobOfferedTo.remove(applicant);
      applicant.setStatus(UserStatus.EMPLOYED);
    }
  }
  
  // removes the applicant from jobOfferedTo list
  // sets the applicants status to available
  public void revokeOffer(Applicant applicant) throws ApplicantNotFoundException {
    if (!applicantsJobOfferedTo.contains(applicant)) {
      throw new ApplicantNotFoundException();
    } else {
      applicantsJobOfferedTo.remove(applicant);
      applicant.setStatus(UserStatus.AVAILABLE);
    }
  }
}

