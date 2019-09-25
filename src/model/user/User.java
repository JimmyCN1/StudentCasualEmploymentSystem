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
  private String username;
  private String password;
  private String name;
  private List<Complaint> complaints = new ArrayList<>();
  
  private ManagementSystem managementSystem;
  
  public User(String name, String password, ManagementSystem managementSystem) {
    userCount++;// increment the userCount whenever constructor is called
    // this. syntax not used here as i assume that the variable is global
    this.userId = userCount;// assigns userCount to userID
    this.name = name;
    this.password = password;
    this.managementSystem = managementSystem;
  }
  
  public int getUserId() {
    return this.userId;
  }
  
  @Override
  public String getUsername() {
    return username;
  }
  
  @Override
  public String getHashMapKey() {
    return username;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  public abstract UserStatus getStatus();
  
  public List<Complaint> getComplaints() {
    return this.complaints;
  }
  
  @Override
  public ManagementSystem getManagementSystem() {
    return this.managementSystem;
  }
  
  @Override
  public void setUsername(String username) {
    this.username = username;
  }
  
  @Override
  public void setPassword(String password) {
    this.password = password;
  }
  
  //abstract methods are declared so they can be manipulated by children
  public abstract void setStatus(UserStatus status) throws InvalidUserStatusException;
  
  @Override
  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
  
  public void addComplaint(Complaint complaint) {
    this.complaints.add(complaint);
  }
  
  // attempt lodge complaint if only the string and the user's name are passed
  // contains call to overloaded method with complaint object as the parameter (located below)
  public void lodgeComplaint(String complaint, String offendingUserName)
          throws UserNotFoundException, InvalidUserStatusException {
    boolean foundMatch = false;
    for (int i = 0; i < this.managementSystem.getUsersAsList().size(); i++) {
      if (this.managementSystem.getUsersAsList().get(i).getName().equals(offendingUserName)) {
        foundMatch = true;
        Complaint newComplaint = new Complaint(complaint, this.managementSystem.getUsersAsList().get(i), this);
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
  
  // lodge complaint as a complaint object
  public void lodgeComplaint(Complaint complaint) {
    complaint.getOffendingUser().addComplaint(complaint);// 
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
