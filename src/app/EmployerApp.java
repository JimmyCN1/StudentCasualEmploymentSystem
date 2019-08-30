package app;

import entities.employer.Employer;

import java.util.ArrayList;
import java.util.List;

public class EmployerApp extends App {
  private Employer currentUser;
  
  public EmployerApp(String employerName, String password) {
    currentUser = managementSystem.getEmployerByName(employerName);
  }
  
  public EmployerApp() {
  
  }
  
  public void createEmployer() {
    List<String> employerDetails = createUser();
    managementSystem.registerEmployer(new Employer(employerDetails.get(0),
            employerDetails.get(1),
            managementSystem));
    currentUser = managementSystem.getEmployerByName(employerDetails.get(0));
  }
  
  @Override
  public List<String> createUser() {
    List<String> userDetails = new ArrayList<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.add(employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.add(password);
    return userDetails;
  }
  
}
