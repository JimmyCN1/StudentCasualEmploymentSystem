package job;

import applicant.Applicant;

import java.time.LocalDate;
import java.time.LocalTime;

public class InterviewSlot {
  private LocalDate date;
  private LocalTime time;
  private int duration;
  private Applicant applicant;
  private InterviewResult interviewResult;
  
  public InterviewSlot(LocalDate date, LocalTime time, int duration) {
    this.date = date;
    this.time = time;
    this.duration = duration;
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
