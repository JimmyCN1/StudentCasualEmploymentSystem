package entities.applicant;

import driver.ManagementSystem;

import java.io.File;

public abstract class Applicant {
  private static int applicantCount = 0;
  private int applicantId;
  private String firstName;
  private String lastName;
  private String password;
  private File cv;
  private String status;
  private ManagementSystem managementSystem;
  
  public Applicant(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    applicantCount++;
    this.applicantId = applicantCount;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.status = "searching";
    this.managementSystem = managementSystem;
  }
  
  public int getApplicantId() {
    return applicantId;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public String getPassword() {
    return password;
  }
  
  public File getCv() {
    return cv;
  }
  
  public void setPending() {
    status = "pending";
  }
  
  public void setAsBlackListed() {
    status = "blacklisted";
  }
}
