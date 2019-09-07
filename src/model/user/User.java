package model.user;

import interfaces.UserInterface;

public abstract class User implements UserInterface {
  private static int userCount = 0;
  private int userId;
  
  public User() {
    userCount++;
    userId = userCount;
  }
  
  
  public int getUserCount() {
    return userCount;
  }
}
