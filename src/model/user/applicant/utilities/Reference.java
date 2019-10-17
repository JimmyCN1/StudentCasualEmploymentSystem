package model.user.applicant.utilities;

import java.io.Serializable;

public class Reference implements Serializable {
  private static final long serialVersionUID = 16L;

  private String name;
  private String phoneNumber;
  private String email;
  
  public Reference(String name, String phoneNumber, String email) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
  
  public String getName() {
    return name;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  public String toString()
  {
    return "Reference name: " + name + "\nPhone number: " + phoneNumber + "\nEmail: " + email;
  }
}
