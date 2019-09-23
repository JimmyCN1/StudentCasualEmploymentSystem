package app;

import exceptions.EmployerNotFoundException;
import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import model.user.User;
import model.user.employer.Employer;

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
//    boolean isLoggedIn = true;
//    int response;
//    while (isLoggedIn) {
//      if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
//        System.out.println("You have been blacklisted.\n\nPress 0 to logout..\n");
//        try {
//          response = scanner.nextInt();
//          scanner.nextLine();
//          if (response == 0) {
//            isLoggedIn = false;
//          }
//        } catch (InputMismatchException e) {
//          System.out.println("Please try again..\n\n");
//          scanner.next();
//        }
//      } else {
//        try {
//          System.out.printf("What would you like to do?\n\n" +
//                  "1. Update Your Job Preferences\n" +
//                  "2. Update Your Availabilities\n" +
//                  "3. Update Your Employment Records\n" +
//                  "4. View Job Offers\n\n" +
//                  "0. Logout\n\n");
//          response = scanner.nextInt();
//          scanner.nextLine();
//          switch (response) {
//            case (1):
//              updateJobPreferences();
//              break;
//            case (2):
//              updateAvailabilities();
//              break;
//            case (3):
////              updateEmploymentRecords();
//              break;
//            case (4):
////                viewJobOffers();
//              break;
//            case (0):
//              isLoggedIn = false;
//              break;
//          }
//        } catch (InputMismatchException e) {
//          printInputMismatchMessage();
//        }
//      }
//    }
  }
}
