package model.position;

import enumerators.InterviewSlotStatus;
import model.user.applicant.Applicant;
import model.user.employer.Employer;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewSlot {
  private final int INTERVIEW_DURATION = 60;
  private LocalDate date;
  private LocalTime time;
  private int duration;
  private InterviewSlotStatus status;
  private Applicant applicant;
  private Position position;
  private Employer employer;
  private InterviewResult interviewResult;
  
  public InterviewSlot(LocalDate date, LocalTime time, Applicant applicant, Position position, Employer employer) {
    this.date = date;
    this.time = time;
    duration = INTERVIEW_DURATION;
    status = InterviewSlotStatus.BOOKED;
    this.applicant = applicant;
    this.position = position;
    this.employer = employer;
  }
  
  public InterviewSlot(LocalDate date, LocalTime time) {
    this.date = date;
    this.time = time;
    duration = INTERVIEW_DURATION;
    status = InterviewSlotStatus.FREE;
  }
  
  public LocalDate getDate() {
    return date;
  }
  
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  public LocalTime getTime() {
    return time;
  }
  
  public void setTime(LocalTime time) {
    this.time = time;
  }
  
  public int getDuration() {
    return duration;
  }
  
  public void setDuration(int duration) {
    this.duration = duration;
  }
  
  public InterviewSlotStatus getStatus() {
    return status;
  }
  
  public void setStatus(InterviewSlotStatus status) {
    this.status = status;
  }
  
  public Applicant getApplicant() {
    return applicant;
  }
  
  public void setApplicant(Applicant applicant) {
    this.applicant = applicant;
  }
  
  public Position getPosition() {
    return position;
  }
  
  public void setPosition(Position position) {
    this.position = position;
  }
  
  public Employer getEmployer() {
    return employer;
  }
  
  public void setEmployer(Employer employer) {
    this.employer = employer;
  }
  
  public InterviewResult getInterviewResult() {
    return interviewResult;
  }
  
  public void setInterviewResult(InterviewResult interviewResult) {
    this.interviewResult = interviewResult;
  }
  
  public void bookApplicant(Applicant applicant) {
    this.applicant = applicant;
    setStatus(InterviewSlotStatus.BOOKED);
  }
  
  @Override
  public boolean equals(Object o) {
    return equals((InterviewSlot) o);
  }
  
  public boolean equals(InterviewSlot interviewSlot) {
    return (this.date.isEqual(interviewSlot.date) &&
            this.time.equals(interviewSlot.time) &&
            this.duration == interviewSlot.duration &&
            this.status.equals(interviewSlot.status) &&
            this.position.equals(interviewSlot.position) &&
            this.employer.equals(interviewSlot.employer));
  }
}
