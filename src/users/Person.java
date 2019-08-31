package users;

import interfaces.Entity;

public abstract class Person implements Entity {
  private String firstName;
  private String lastName;
  
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
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