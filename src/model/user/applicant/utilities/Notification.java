package model.user.applicant.utilities;

import model.user.employer.Employer;

public class Notification {
  private String notification;
  private Employer employer;
  
  // TODO: implement class;
  
  public Notification(String notification, Employer employer) {
    this.notification = notification;
    this.employer = employer;
  }
  
  public String getNotification() {
    return notification;
  }
  
  public Employer getEmployer() {
    return employer;
  }
  
  @Override
  public String toString() {
    return String.format("%s\n\n%s", employer.getName(), notification);
  }
}
