package model.user.utilities;

import model.user.User;

public class Complaint {
  private String complaint;
  private User offendingUser;
  
  public Complaint(String complaint, User offendingUser) {
    this.complaint = complaint;
    this.offendingUser = offendingUser;
  }
  
  public String getComplaint() {
    return complaint;
  }
  
  public User getOffendingUser() {
    return offendingUser;
  }
  
  public void setComplaint(String complaint) {
    this.complaint = complaint;
  }
}
