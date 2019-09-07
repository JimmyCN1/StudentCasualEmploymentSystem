package model.user;

import enumerators.UserStatus;
import interfaces.UserInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements UserInterface {
  private static int userCount = 0;
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
    if (complaints.size() >= 3) {
      setStatus(UserStatus.BLACKLISTED);
    }
  }
}
