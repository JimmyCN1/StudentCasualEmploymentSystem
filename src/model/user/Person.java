package model.user;


import model.system.ManagementSystem;

import java.io.Serializable;

public abstract class Person extends User implements Serializable {
  private String firstName;
  private String lastName;
  
  public Person(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName + " " + lastName, password, managementSystem);
  }
  
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
  
  @Override
  public String nameToString() {
    return String.format("Name: %s", getName());
  }
}
