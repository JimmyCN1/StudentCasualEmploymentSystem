package interfaces;

public interface UserInterface {
  int getId();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  boolean verifyPassword(String password);
  
  String nameToString();
  
  String statusToString();
}
