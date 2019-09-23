package model.user;

import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;
import exceptions.UserNotFoundException;
import interfaces.UserInterface;
import model.system.ManagementSystem;
import model.user.utilities.Complaint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements UserInterface, Serializable {
  private static int userCount = 0;
  private final int MAX_COMPLAINTS = 3;
  private int userId;
  private String name;
  private List<Complaint> complaints = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public User(String name, ManagementSystem managementSystem) {
    userCount++;// increment the userCount whenever constructor is called
    // this. syntax not used here as i assume that the variable is global
    this.userId = userCount;// assigns userCount to userID
    this.name = name;
    this.managementSystem = managementSystem;
  }
  
  public int getUserId() {
    return this.userId;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  // abstract methods are declared so they can be manipulated by children
  public abstract UserStatus getStatus();
  
  public List<Complaint> getComplaints() {
    return this.complaints;
  }
  
  @Override
  public ManagementSystem getManagementSystem() {
    return this.managementSystem;
  }
  
  //abstract methods are declared so they can be manipulated by children
  public abstract void setStatus(UserStatus status) throws InvalidUserStatusException;
  
  public void addComplaint(Complaint complaint) {
    this.complaints.add(complaint);
  }

  public void lodgeComplaint(String complaint, String userName)
          throws UserNotFoundException, InvalidUserStatusException {
    boolean foundMatch = false;
    for (int i = 0; i < this.managementSystem.getUsersAsList().size(); i++) {
      if (this.managementSystem.getUsersAsList().get(i).getName().equals(userName)) {
        foundMatch = true;
        Complaint newComplaint = new Complaint(complaint, this.managementSystem.getUsersAsList().get(i));
        lodgeComplaint(newComplaint);
        if (this.complaints.size() >= this.MAX_COMPLAINTS) {
          setStatus(UserStatus.BLACKLISTED);
        }
      }
    }
    if (!foundMatch) {
      throw new UserNotFoundException();
    }
  }
  
  // this is very confusing and unclear, i do not understand the goal of this method
  // and i think it is unnecessary
  public void lodgeComplaint(Complaint complaint) {
    complaint.getOffendingUser().addComplaint(complaint);// 
  }
  
  @Override
  public String toString() {
    return String.format("User Name: %s\nStatus: %s\n%s\n", getName(), getStatus(), getComplaints());
  }
  
  public String complaintsToString() {// this might return an error as unsure if global or local complaints
    String complaints = "";
    for (int i = 0; i < this.complaints.size(); i++) {
      complaints += String.format("Complaint%s : %s\n", i + 1, this.complaints.get(i));
    }
    return complaints;
  }
}
