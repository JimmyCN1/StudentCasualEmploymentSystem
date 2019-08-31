package app;

import interfaces.Entity;
import interfaces.employer.Employer;
import exceptions.EmployerDoesNotExistException;
import exceptions.PasswordMissmatchException;

import java.util.HashMap;
import java.util.Map;

public class EmployerApp extends App {
  private final String EMPLOYER_NAME = "employerName";
  private Employer currentUser;
  
  public EmployerApp(String employerName, String password) throws EmployerDoesNotExistException, PasswordMissmatchException {
    Employer employer = managementSystem.getEmployerByName(employerName);
    if (employer == null) {
      throw new EmployerDoesNotExistException();
    } else {
      if (!employer.isPasswordMatch(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(employer);
      }
    }
    System.out.println(getCurrentUser().toString());
  }
  
  public EmployerApp() {
  
  }
  
  public Entity getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(Employer currentUser) {
    this.currentUser = currentUser;
  }
  
  
  public void createEmployer() {
    Map<String, String> employerDetails = createUser();
    managementSystem.registerEmployer(new Employer(employerDetails.get(EMPLOYER_NAME),
            employerDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getEmployerByName(employerDetails.get(EMPLOYER_NAME)));
  }
  
  @Override
  public Map<String, String> createUser() {
    Map<String, String> userDetails = new HashMap<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.put(EMPLOYER_NAME, employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.put(PASSWORD, password);
    return userDetails;
  }
  
}
