package model.user.applicant.utilities;

public class Reference {
  private String name;
  private int phoneNumber;
  private String email;
  
  public Reference(String name, int phoneNumber, String email) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
  
  public String getName() {
    return name;
  }
  
  public int getPhoneNumber() {
    return phoneNumber;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setPhoneNumber(int phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
}
