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
    return this.complaint;
  }
  
  public User getOffendingUser() {
    return this.offendingUser;
  }
  
  // this method is useless as the complaint has already been set in the constructor
  //public void setComplaint(String complaint) {
  //  this.complaint = complaint;
  //}
}
