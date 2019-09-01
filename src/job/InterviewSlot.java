package job;

import interfaces.applicant.Applicant;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewSlot {
  private final int INTERVIEW_DURATION = 60;
  private LocalDate date;
  private LocalTime time;
  private int duration;
  private Applicant applicant;
  private InterviewResult interviewResult;
  
  public InterviewSlot(LocalDate date, LocalTime time, Applicant applicant) {
    this.date = date;
    this.time = time;
    duration = INTERVIEW_DURATION;
    this.applicant = applicant;
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
  
  public Applicant getApplicant() {
    return applicant;
  }
  
  public boolean setInterviewResult() {
    return false;
  }
}
