package app;

import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import model.user.employer.Employer;
import exceptions.PasswordMissmatchException;

import java.util.Map;

public class EmployerApp extends App implements AppInterface {
  private final String EMPLOYER_NAME = "employerName";
  private Employer currentUser;
  
  public EmployerApp(String employerName,
                     String password,
                     ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(employerName, password);
  }
  
  public EmployerApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  // set the current user logged in
  public Employer getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(Employer employer) {
    this.currentUser = employer;
  }
  
  // creates a new employer in the application
  public void createEmployer() {
    Map<String, String> employerDetails = getEmployerDetails();
    managementSystem.registerEmployer(new Employer(employerDetails.get(EMPLOYER_NAME),
            employerDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getEmployerByName(employerDetails.get(EMPLOYER_NAME).toLowerCase()));
  }
  
  // determines whether the login details provided are provided
  private void verifyUser(String employerName, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    Employer employer = managementSystem.getEmployerByName(employerName);
    if (employer == null) {
      throw new UserNotFoundException();
    } else {
      if (!employer.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(employer);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
  
  @Override
  public void displayMainMenu() {
  
  }
}
