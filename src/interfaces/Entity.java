package interfaces;

public interface Entity {
  int getId();
  
  String getHashMapKey();
  
  String getName();
  
  String getPassword();
  
  boolean verifyPassword(String password);
}
