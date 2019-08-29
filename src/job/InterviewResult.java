package job;

import applicant.Applicant;
import driver.ManagementSystem;

import java.util.Collection;

public class InterviewResult {
  private Collection<String> referenceChecks;
  private String interviewNotes;
  
  public InterviewResult(Collection<String> referenceChecks, String interviewNotes) {
    this.referenceChecks = referenceChecks;
    this.interviewNotes = interviewNotes;
  }
  
  public Collection<String> getReferenceChecks() { return referenceChecks; }
  
  public String getInterviewNotes() { return interviewNotes; }
}
