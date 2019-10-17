package app;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.EmployerNotFoundException;
import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.User;
import model.user.employer.Employer;

import java.util.InputMismatchException;
import java.util.Map;

public class EmployerApp extends AbstractApp {
  private final String EMPLOYER_NAME = "employerName";
  private Employer currentUser;
  private PositionApp positionApp;
  
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
  
  @Override
  public void setCurrentUser(User employer) {
    super.setCurrentUser(employer);
    this.currentUser = (Employer) employer;
  }
  
  // creates a new employer in the application
  public void createEmployer() {
    Map<String, String> employerDetails = getEmployerDetails();
    System.out.println("What is the company email?");
    String email = scanner.nextLine();
    System.out.println("What is the company phone number?");
    String phoneNumber = scanner.nextLine();
    Employer newEmployer = new Employer(employerDetails.get(EMPLOYER_NAME),
            employerDetails.get(PASSWORD),
            managementSystem);
    newEmployer.setUsername(employerDetails.get(USERNAME));
    newEmployer.setEmail(email);
    newEmployer.setPhoneNumber(phoneNumber);
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
  
  public void displayMainMenu() {
    boolean isLoggedIn = true;
    int response;
    while (isLoggedIn) {
      if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
        isLoggedIn = showBlacklistedScreen();
      } else {
        try {
          System.out.printf("What would you like to do?\n\n" +
                  "1. Create A Position\n" +
                  "2. Manage Current Positions\n\n" +
                  "0. Logout\n\n");
          response = scanner.nextInt();
          scanner.nextLine();
          switch (response) {
            case (1):
              createAPosition();
              break;
            case (2):
              manageCurrentPositions();
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
  
  private void createAPosition() {
    System.out.println("What is the position title?");
    String title = scanner.nextLine();
    boolean validResponse = false;
    PositionType positionType = PositionType.FULL_TIME;
    while (!validResponse) {
      try {
        System.out.printf("What is the position type?\n" +
                "1. Part Time\n" +
                "2. Full Time\n" +
                "3. Internship\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            positionType = PositionType.FULL_TIME;
            validResponse = true;
            break;
          case (2):
            positionType = PositionType.PART_TIME;
            validResponse = true;
            break;
          case (3):
            positionType = PositionType.INTERNSHIP;
            validResponse = true;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
    validResponse = false;
    double hourlyRate = 0;
    while (!validResponse) {
      try {
        System.out.println("What is the hourly rate?");
        hourlyRate = scanner.nextDouble();
        validResponse = true;
      } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Please input a number (eg. 20.3)");
        scanner.next();
      }
    }
    validResponse = false;
    int minHours = 0;
    while (!validResponse) {
      try {
        System.out.println("What is the minimum hours per week?");
        minHours = scanner.nextInt();
        validResponse = true;
      } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Please input a number (eg. 20)");
        scanner.next();
      }
    }
    validResponse = false;
    int maxHours = 0;
    while (!validResponse) {
      try {
        System.out.println("What is the maximum hours per week?");
        maxHours = scanner.nextInt();
        validResponse = true;
      } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Please input a number (eg. 40)");
        scanner.next();
      }
    }
    Position newPosition = new Position(
            title,
            positionType,
            hourlyRate,
            minHours,
            maxHours,
            currentUser,
            managementSystem
    );
    currentUser.addPosition(newPosition);
    System.out.printf("The position '%s' has successfully been created\n\n", title);
  }
  
  private void manageCurrentPositions() {
    boolean validResponse = false;
    Position positionToManage = null;
    int response = 0;
    while (!validResponse) {
      try {
        System.out.printf("Which position would you like to manage?\n");
        System.out.println(currentUser.listToStringAsOrderedList(currentUser.getPositionsAsList()));
        response = scanner.nextInt();
        scanner.nextLine();
        positionToManage = currentUser.getPositionsAsList().get(response - 1);
        positionApp = new PositionApp(positionToManage, currentUser, managementSystem);
        positionApp.displayMainMenu();
        validResponse = true;
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      } catch (ArrayIndexOutOfBoundsException e) {
        printInputMismatchMessage();
      }
    }
  }
}
