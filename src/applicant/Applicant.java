package applicant;

import java.io.File;

public class Applicant {
  private static int applicantCount = 0;
  private int applicantId;
  private String firstName;
  private String lastName;
  private String password;
  private File cv;
  
  public Applicant(String firstName, String lastName, String password) {
    applicantCount++;
    this.applicantId = applicantCount;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
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
}
