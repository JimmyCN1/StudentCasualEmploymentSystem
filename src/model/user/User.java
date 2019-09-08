package model.user;

import enumerators.UserStatus;
import interfaces.UserInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements UserInterface {
  private static int userCount = 0;
  private final int MAX_COMPLAINTS = 3;
  private int userId;
  private List<String> complaints = new ArrayList<>();
  
  public User() {
    userCount++;
    userId = userCount;
  }
  
  public int getUserCount() {
    return userCount;
  }
  
  public abstract UserStatus getStatus();
  
  public List<String> getComplaints() {
    return complaints;
  }
  
  public abstract void setStatus(UserStatus status);
  
  public void addComplaint(String complaint) {
    complaints.add(complaint);
  }
  
  // lodges a complaint against the applicant
  // if the applicant has 3 or more complaints, they are blacklisted
  public void lodgeComplaint(String complaint, User user) {
    user.addComplaint(complaint);
    if (complaints.size() >= MAX_COMPLAINTS) {
      setStatus(UserStatus.BLACKLISTED);
    }
  }
  
  public String complaintsToString() {
    String complaints = "";
    for (int i = 0; i < this.complaints.size(); i++) {
      complaints += String.format("Complaint%s : %s\n", i + 1, this.complaints.get(i));
    }
    return complaints;
  }
}
