package interfaces;

import model.system.ManagementSystem;

import java.io.Serializable;

public interface UserInterface extends Serializable {
  int getId();
  
  String getUserName();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  ManagementSystem getManagementSystem();
  
  void setUserName(String userName);
  
  void setPassword(String password);
  
  boolean verifyPassword(String password);
  
  // formatted toString method for user's name
  String nameToString();
  
  // formatted toString method for user's status
  String statusToString();
}
