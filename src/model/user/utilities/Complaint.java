package model.user.utilities;

import model.user.User;

public class Complaint {
  private String complaint;
  private User offendingUser;
  private User complaintee;
  
  public Complaint(String complaint, User offendingUser, User complaintee) {
    this.complaint = complaint;
    this.offendingUser = offendingUser;
    this.complaintee = complaintee;
  }
  
  public String getComplaint() {
    return this.complaint;
  }
  
  public User getOffendingUser() {
    return this.offendingUser;
  }
  
  public User getComplaintee() {return this.complaintee; }
}
