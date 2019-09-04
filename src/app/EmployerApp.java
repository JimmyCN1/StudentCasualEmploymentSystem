package app;

import exceptions.EntityNotFoundException;
import model.system.ManagementSystem;
import interfaces.Entity;
import model.employer.Employer;
import exceptions.PasswordMissmatchException;

import java.util.HashMap;
import java.util.Map;

public class EmployerApp extends App {
  private final String EMPLOYER_NAME = "employerName";
  private Employer currentUser;
  
  public EmployerApp(String employerName, String password, ManagementSystem managementSystem)
          throws EntityNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(employerName, password);
  }
  
  public EmployerApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  public Entity getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(Employer employer) {
    this.currentUser = employer;
  }
  
  public void createEmployer() {
    Map<String, String> employerDetails = getNewUserDetails();
    managementSystem.registerEmployer(new Employer(employerDetails.get(EMPLOYER_NAME),
            employerDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getEmployerByName(employerDetails.get(EMPLOYER_NAME).toLowerCase()));
  }
  
  @Override
  public Map<String, String> getNewUserDetails() {
    Map<String, String> userDetails = new HashMap<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.put(EMPLOYER_NAME, employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.put(PASSWORD, password);
    return userDetails;
  }
  
  private void verifyUser(String employerName, String password)
          throws EntityNotFoundException, PasswordMissmatchException {
    Employer employer = managementSystem.getEmployerByName(employerName);
    if (employer == null) {
      throw new EntityNotFoundException();
    } else {
      if (!employer.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(employer);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
  
  
}
