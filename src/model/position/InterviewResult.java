package model.position;

public class InterviewResult {
  private boolean hasValidReferences;
  private String interviewNotes;
  
  public InterviewResult(boolean hasValidReferences, String interviewNotes) {
    this.hasValidReferences = hasValidReferences;
    this.interviewNotes = interviewNotes;
  }
  
  public boolean getReferenceValidity() {
    return hasValidReferences;
  }
  
  public String getInterviewNotes() {
    return interviewNotes;
  }
  
  public void setReferenceValidity(boolean hasValidReferences) {
    this.hasValidReferences = hasValidReferences;
  }
  
  public void setInterviewNotes(String interviewNotes) {
    this.interviewNotes = interviewNotes;
  }
}
