package interfaces;

import model.system.ManagementSystem;

import java.io.Serializable;

public interface UserInterface extends Serializable {
  int getId();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  ManagementSystem getManagementSystem();
  
  boolean verifyPassword(String password);
  
  // formatted toString method for user's name
  String nameToString();
  
  // formatted toString method for user's status
  String statusToString();
}
