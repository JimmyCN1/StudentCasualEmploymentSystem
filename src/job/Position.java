package job;

import applicant.Applicant;
import driver.ManagementSystem;

import java.util.Collection;

public class Position {
  private static int positionCount = 0;
  private int positionId;
  private String positionTitle;
  // private Collection<(Enum) Shedule.>
  private Collection<Applicant> shortListedApplicants;
  private Collection<Applicant> highRankingApplicants;
  private Collection<Applicant> jobOffered;
  private Collection<InterviewSlot> interviewSlots;
  private Collection<Applicant> unsuccessfullApplicants;
  private ManagementSystem managementSystem;
  
  public Position(String positionTitle) {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = positionTitle;
  }
  
  public Position() {
    positionCount++;
    this.positionId = positionCount;
    this.positionTitle = "Unknown";
  }
  
  public int getPositionId() {
    return positionId;
  }
  
  public String getPositionTitle() {
    return positionTitle;
  }
  
  public Collection<Applicant> getShortListedApplicants() {
    return shortListedApplicants;
  }
  
  public Collection<Applicant> getHighRankingApplicants() {
    return highRankingApplicants;
  }
  
  public Collection<Applicant> getJobOffered() {
    return jobOffered;
  }
  
  public Collection<InterviewSlot> getInterviewSlots() {
    return interviewSlots;
  }
  
  public Collection<Applicant> getUnsuccessfullApplicants() {
    return unsuccessfullApplicants;
  }
  
  public void setPending(boolean bool, Applicant applicant) {
  
  }
}

