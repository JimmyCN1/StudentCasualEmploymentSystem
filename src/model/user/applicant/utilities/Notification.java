package model.user.applicant.utilities;

import model.user.employer.Employer;

public class Notification {
  private String message;
  private Employer employer;
  
  public Notification(String message, Employer employer) {
    this.message = message;
    this.employer = employer;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public Employer getEmployer() {
    return employer;
  }
}
