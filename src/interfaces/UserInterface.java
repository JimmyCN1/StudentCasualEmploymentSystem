package interfaces;

import java.io.Serializable;

public interface UserInterface extends Serializable {
  int getId();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  boolean verifyPassword(String password);
  
  String nameToString();
  
  String statusToString();
}
