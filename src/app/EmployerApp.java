package app;

import enumerators.UserStatus;
import exceptions.EmployerNotFoundException;
import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import model.user.User;
import model.user.employer.Employer;

import java.util.InputMismatchException;
import java.util.Map;

public class EmployerApp extends App implements AppInterface {
  private final String EMPLOYER_NAME = "employerName";
  private Employer currentUser;
  
  public EmployerApp(String username,
                     String password,
                     ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(username, password);
  }
  
  public EmployerApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  // set the current user logged in
  @Override
  public Employer getCurrentUser() {
    return currentUser;
  }
  
  @Override
  public void setCurrentUser(User employer) {
    super.setCurrentUser(employer);
    this.currentUser = (Employer) employer;
  }
  
  // creates a new employer in the application
  public void createEmployer() {
    Map<String, String> employerDetails = getEmployerDetails();
    Employer newEmployer = new Employer(employerDetails.get(EMPLOYER_NAME),
            employerDetails.get(PASSWORD),
            managementSystem);
    newEmployer.setUsername(employerDetails.get(USERNAME));
    managementSystem.registerEmployer(newEmployer);
    try {
      setCurrentUser(managementSystem.getEmployerByUsername(employerDetails.get(USERNAME)));
    } catch (EmployerNotFoundException e) {
      System.out.println("Error creating employer. Please try again..");
    }
  }
  
  // determines whether the login details provided are provided
  private void verifyUser(String username, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    Employer employer = managementSystem.getEmployerByUsername(username);
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
    //TODO: searching for suitable candidates
    //TODO: shortlisting and ranking candidates
    //TODO: updating candidate based on interview and reference check
    //TODO: creation of jobs
    //TODO: creation of job offers
    boolean isLoggedIn = true;
    int response;
    while (isLoggedIn) {
      if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
        isLoggedIn = showBlacklistedScreen();
      } else {
        try {
          System.out.printf("What would you like to do?\n\n" +
                  "1. Search for matching candidates\n" +
                  "2. Shortlist Applicants\n" +
                  "3. Rank Applicants\n" +
                  "4. Mail Applicants\n" +
                  "5. Set Interview Times\n" +
                  "6. Mail Applicants\n" +
                  "7. Offer Job\n\n" +
                  "0. Logout\n\n");
          response = scanner.nextInt();
          scanner.nextLine();
          switch (response) {
            case (1):
//              searchForMatchingCandidates();
              break;
            case (2):
//              updateAvailabilities();
              break;
            case (3):
//              updateEmploymentRecords();
              break;
            case (4):
//                viewJobOffers();
              break;
            case (0):
              isLoggedIn = false;
              break;
          }
        } catch (InputMismatchException e) {
          printInputMismatchMessage();
        }
      }
    }
  }
}
