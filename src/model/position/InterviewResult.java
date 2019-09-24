package model.position;

import java.util.Collection;
import java.util.List;

public class InterviewResult {
  private List<String> referenceChecks;
  private String interviewNotes;
  
  public InterviewResult(List<String> referenceChecks, String interviewNotes) {
    this.referenceChecks = referenceChecks;
    this.interviewNotes = interviewNotes;
  }
  
  public Collection<String> getReferenceChecks() {
    return referenceChecks;
  }
  
  public String getInterviewNotes() {
    return interviewNotes;
  }
  
  public void setReferenceChecks(List<String> referenceChecks) {
    this.referenceChecks = referenceChecks;
  }
  
  public void setInterviewNotes(String interviewNotes) {
    this.interviewNotes = interviewNotes;
  }
}
