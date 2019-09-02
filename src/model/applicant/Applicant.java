package model.applicant;

import enumerators.ApplicantStatus;
import model.driver.ManagementSystem;
import interfaces.Entity;
import users.Person;

public abstract class Applicant extends Person implements Entity {
  private static int applicantCount = 0;
  private int applicantId;
  private String password;
  private String cv;
  private ApplicantStatus applicantStatus;
  private ManagementSystem managementSystem;
  
  public Applicant(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    applicantCount++;
    this.applicantId = applicantCount;
    setFirstName(firstName);
    setLastName(lastName);
    this.password = password;
    this.applicantStatus = ApplicantStatus.AVAILABLE;
    this.managementSystem = managementSystem;
  }
  
  public int getApplicantId() {
    return applicantId;
  }
  
  public String getPassword() {
    return password;
  }
  
  public ApplicantStatus getApplicantStatus() {
    return applicantStatus;
  }
  
  public String getCv() {
    return cv;
  }
  
  public void setStatus(ApplicantStatus applicantStatus) {
    this.applicantStatus = applicantStatus;
  }

//  public void setPending() {
//    applicantStatus = ApplicantStatus.PENDING;
//  }
//
//  public void setUnknown() { applicantStatus = ApplicantStatus.UNKNOWN; }
//
//  public void setAsBlackListed() {
//    applicantStatus = ApplicantStatus.BLACKLISTED;
//  }
  
  public String fetchNotification(String notification) {
    return String.format("You have one notification...\n%s",
            notification);
  }
  
  @Override
  public boolean isPasswordMatch(String password) {
    return this.password.equals(password);
  }
  
  @Override
  public boolean equals(Object object) {
    return equals((Applicant) object);
  }
  
  private boolean equals(Applicant applicant) {
    return applicant.getFirstName() == this.getFirstName() &&
            applicant.getLastName() == this.getLastName();
  }
}
