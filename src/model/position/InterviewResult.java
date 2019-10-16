package model.position;

import java.io.Serializable;

public class InterviewResult implements Serializable {
  private static final long serialVersionUID = 4L;

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
  
  @Override
  public String toString() {
    return String.format("Has valid references: \n" +
                    "Notes: ",
            hasValidReferences,
            interviewNotes);
  }
}
