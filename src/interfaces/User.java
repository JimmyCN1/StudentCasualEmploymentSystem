package interfaces;

public interface User {
  int getId();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  boolean verifyPassword(String password);
}
