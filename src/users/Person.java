package users;

import interfaces.User;

public abstract class Person implements User {
  private String firstName;
  private String lastName;
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  @Override
  public String getHashMapKey() {
    return getFirstName().toLowerCase() + getLastName().toLowerCase();
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  @Override
  public String getName() {
    return String.format("%s %s", getFirstName(), getLastName());
  }
}
