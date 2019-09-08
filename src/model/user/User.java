package model.user;

import enumerators.UserStatus;
import exceptions.UserNotFoundException;
import interfaces.UserInterface;
import model.system.ManagementSystem;
import model.user.utilities.Complaint;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements UserInterface {
  private static int userCount = 0;
  private final int MAX_COMPLAINTS = 3;
  private int userId;
  private String name;
  private List<Complaint> complaints = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public User(String name, ManagementSystem managementSystem) {
    userCount++;
    userId = userCount;
    this.name = name;
    this.managementSystem = managementSystem;
  }
  
  public int getUserId() {
    return userId;
  }
  
  public String getName() {
    return name;
  }
  
  public abstract UserStatus getStatus();
  
  public List<Complaint> getComplaints() {
    return complaints;
  }
  
  public abstract void setStatus(UserStatus status);
  
  public void addComplaint(Complaint complaint) {
    complaints.add(complaint);
  }
  
  // lodges a complaint against the applicant
  // if the applicant has 3 or more complaints, they are blacklisted
  public void lodgeComplaint(String complaint, String userName)
          throws UserNotFoundException {
    boolean foundMatch = false;
    for (int i = 0; i < managementSystem.getUsersAsList().size(); i++) {
      if (managementSystem.getUsersAsList().get(i).getName().equals(userName)) {
        foundMatch = true;
        Complaint newComplaint = new Complaint(complaint, managementSystem.getUsersAsList().get(i));
        lodgeComplaint(newComplaint);
        if (complaints.size() >= MAX_COMPLAINTS) {
          setStatus(UserStatus.BLACKLISTED);
        }
      }
    }
    if (!foundMatch) {
      throw new UserNotFoundException();
    }
  }
  
  public void lodgeComplaint(Complaint complaint) {
    complaint.getOffendingUser().addComplaint(complaint);
  }
  
  @Override
  public String toString() {
    return String.format("User Name: %s\nStatus: %s\n%s\n", getName(), getStatus(), getComplaints());
  }
  
  public String complaintsToString() {
    String complaints = "";
    for (int i = 0; i < this.complaints.size(); i++) {
      complaints += String.format("Complaint%s : %s\n", i + 1, this.complaints.get(i));
    }
    return complaints;
  }
}
