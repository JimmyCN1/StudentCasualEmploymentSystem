package app;

import driver.ManagementSystem;
import employer.Employer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployerApp extends App {
  private Employer currentUser;
  
  public EmployerApp() {
  }
  
  public void createEmployer() {
    List<String> employerDetails = createUser();
    managementSystem.registerEmployer(new Employer(employerDetails.get(0), employerDetails.get(1), employerDetails.get(2), managementSystem));
  }
  
}
