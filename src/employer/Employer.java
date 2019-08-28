package employer;

import applicant.Applicant;
import driver.ManagementSystem;
import job.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Employer {
  private static int employerCount = 0;
  private int employerId;
  private String firstName;
  private String lastName;
  private String password;
  private List<Position> positions;
  private ManagementSystem managementSystem;
  
  public Employer(String firstName, String lastName, String password) {
    employerCount++;
    this.employerId = employerCount;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
  
  public List<Applicant> searchForMatchingApplicant(Position position) {
    return new ArrayList<Applicant>();
  }
  
  public boolean shortlistApplicant(Applicant applicant, Position position) {
    return false;
  }
  
  public boolean rankApplicants(Position position) {
    return false;
  }
  
  public boolean bookInterview(Applicant applicant, Position position) {
    return false;
  }
  
  public boolean updateApplicant(Applicant applicant, Position position) {
    return false;
  }
  
  public boolean mailApplicant(String mail, Applicant applicant) {
    return false;
  }
  
  public boolean notifyHighRankingApplicants(Position position) {
    return false;
  }
  
  public boolean offerJob(Applicant applicant, Position position) {
    return false;
  }
}
