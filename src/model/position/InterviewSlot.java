package model.position;

import enumerators.InterviewSlotStatus;
import model.user.applicant.Applicant;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewSlot {
  private final int INTERVIEW_DURATION = 60;
  private LocalDate date;
  private LocalTime time;
  private int duration;
  private InterviewSlotStatus status;
  private Applicant applicant;
  private InterviewResult interviewResult;
  
  public InterviewSlot(LocalDate date, LocalTime time, Applicant applicant) {
    this.date = date;
    this.time = time;
    duration = INTERVIEW_DURATION;
    status = InterviewSlotStatus.BOOKED;
    this.applicant = applicant;
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
  
  public LocalTime getTime() {
    return time;
  }
  
  public int getDuration() {
    return duration;
  }
  
  public InterviewSlotStatus getStatus() {
    return status;
  }
  
  public Applicant getApplicant() {
    return applicant;
  }
  
  public void bookApplicant(Applicant applicant) {
    this.applicant = applicant;
    setStatus(InterviewSlotStatus.BOOKED);
  }
  
  private void setStatus(InterviewSlotStatus status) {
    this.status = status;
  }
  
  public boolean setInterviewResult() {
    return false;
  }
}
