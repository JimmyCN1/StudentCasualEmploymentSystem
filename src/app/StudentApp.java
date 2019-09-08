package app;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import interfaces.AppInterface;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;

import java.util.InputMismatchException;
import java.util.Map;

public class StudentApp extends App implements AppInterface {
  private final int LOCAL = 1;
  private final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String firstName,
                    String lastName,
                    String password,
                    ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(firstName, lastName, password);
  }
  
  public StudentApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  public Applicant getCurrentUser() {
    return currentUser;
  }
  
  // set the current user logged in
  public void setCurrentUser(Applicant applicant) {
    currentUser = applicant;
  }
  
  public void selectStudentType() {
    boolean validResponse = false;
    while (!validResponse) {
      System.out.println("Which type of student are you?");
      System.out.println("1. Local\n2. International");
      int response = scanner.nextInt();
      scanner.nextLine();
      switch (response) {
        case (LOCAL):
          validResponse = true;
          createLocalStudent();
          break;
        case (INTERNATIONAL):
          validResponse = true;
          createInternationalStudent();
          break;
        default:
          break;
      }
    }
  }
  
  public void createLocalStudent() {
    Map<String, String> studentDetails = getPersonalDetails();
    PositionType positionType = getStudentAvailability();
    managementSystem.registerApplicant(new LocalStudent(
            studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            positionType,
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(
            studentDetails.get(FIRST_NAME).toLowerCase() +
                    studentDetails.get(LAST_NAME).toLowerCase()
    ));
  }
  
  private void createInternationalStudent() {
    Map<String, String> studentDetails = getPersonalDetails();
    managementSystem.registerApplicant(new InternationalStudent(
            studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(
            studentDetails.get(FIRST_NAME).toLowerCase() +
                    studentDetails.get(LAST_NAME).toLowerCase()
    ));
  }
  
  // returns the selected availability
  private PositionType getStudentAvailability() {
    PositionType type = null;
    while (!isValidResponse) {
      System.out.println("What is your availability?");
      System.out.printf("1. Part-Time\n2. Full-Time\n3. Internship\n\n");
      switch (scanner.nextInt()) {
        case (1):
          isValidResponse = true;
          type = PositionType.PART_TIME;
          break;
        case (2):
          isValidResponse = true;
          type = PositionType.FULL_TIME;
          break;
        case (3):
          isValidResponse = true;
          type = PositionType.INTERNSHIP;
          break;
      }
    }
    isValidResponse = false;
    return type;
  }
  
  private void verifyUser(String firstName, String lastName, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    Applicant applicant = managementSystem.getApplicantByName(
            firstName.toLowerCase() + lastName.toLowerCase());
    if (applicant == null) {
      throw new UserNotFoundException();
    } else {
      if (!applicant.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(applicant);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
  
  // if blacklisted, display blacklisted applicant message, else display the main menu
  @Override
  public void displayMainMenu() {
    boolean isLoggedIn = true;
    int response;
    while (isLoggedIn) {
      if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
        System.out.println("You have been blacklisted.\n\nPress 0 to logout..\n");
        try {
          response = scanner.nextInt();
          scanner.nextLine();
          if (response == 0) {
            isLoggedIn = false;
          }
        } catch (InputMismatchException e) {
          System.out.println("Please try again..\n\n");
          scanner.next();
        }
      } else {
        try {
          System.out.printf("What would you like to do?\n\n" +
                  "1. Update Your Job Preferences\n" +
                  "2. Update Your Availabilities\n" +
                  "3. Update Your Employment Records\n" +
                  "4. View Job Offers\n\n" +
                  "0. Logout\n\n");
          response = scanner.nextInt();
          scanner.nextLine();
          switch (response) {
            case (1):
              updateJobPreferences();
              break;
            case (2):
              updateAvailabilities();
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
          System.out.println("Please try again..\n\n");
          scanner.next();
        }
      }
      
    }
  }
  
  private void updateJobPreferences() {
    boolean goBack = false;
    while (!goBack) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Add A Job Preference\n" +
                "2. Remove A Job Preference\n" +
                "3. View Preferences\n" +
                "0. Go back\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            addJobPreference();
            break;
          case (2):
            removeJobPreference();
            break;
          case (3):
            viewPreferences();
            break;
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Please try again..\n\n");
        scanner.next();
      }
    }
  }
  
  private void addJobPreference() {
    System.out.println("Please type one of these job preferences to add to your selected preferences\n");
    for (String jc : managementSystem.getJobCategories()) {
      System.out.printf("%s ", jc);
    }
    System.out.println("\n");
    String response = scanner.nextLine();
    try {
      currentUser.addJobPreference(response);
      viewPreferences();
    } catch (InvalidJobCategoryException e) {
      System.out.printf("%s does not exist..\n\n", response);
    }
  }
  
  private void removeJobPreference() {
    System.out.println("Please type one of these job preferences to removed to your selected preferences\n");
    for (String jp : currentUser.getJobPreferences()) {
      System.out.printf("%s ", jp);
    }
    System.out.println("\n");
    String response = scanner.nextLine();
    try {
      currentUser.removeJobPreference(response);
      viewPreferences();
    } catch (JobCategoryNotFoundException e) {
      System.out.printf("%s was not found..\n\n", response);
    }
  }
  
  private void viewPreferences() {
    System.out.println("Your job preferences are now..");
    for (String jp : currentUser.getJobPreferences()) {
      System.out.printf("%s ", jp);
    }
    System.out.println("\n");
  }
  
  private void updateAvailabilities() {
    boolean goBack = false;
    while (!goBack) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Add An Availability\n" +
                "2. Remove An Availability\n" +
                "3. View Availabilities\n" +
                "0. Go back\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            addAvailability();
            break;
          case (2):
            removeAvailability();
            break;
          case (3):
            viewAvailabilities();
            break;
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Please try again..\n\n");
        scanner.next();
      }
    }
  }
  
  private void addAvailability() {
    System.out.println("Please type one of these availabilities to add to your selected availabilities\n");
    for (PositionType availability : PositionType.values()) {
      System.out.printf("%s ", availability);
    }
    System.out.println("\n");
    String response = scanner.nextLine();
    try {
      currentUser.addAvailability(response);
      viewAvailabilities();
    } catch (PositionTypeNotFoundException e) {
      System.out.println("Sorry, this is not a valid availability..\n");
    } catch (InternationalStudentAvailabilityException e) {
      System.out.println("Sorry, as an international student, you can only work part time..\n");
    }
  }
  
  private void removeAvailability() {
    System.out.println("Please type one of these availabilities to removed to your selected availabilities\n");
    for (PositionType availability : currentUser.getAvailabilities()) {
      System.out.printf("%s ", availability);
    }
    System.out.println("\n");
    String response = scanner.nextLine();
    try {
      currentUser.removeAvailability(response);
      viewAvailabilities();
    } catch (PositionTypeNotFoundException e) {
      System.out.println("Sorry, this is not a valid availability..\n");
    } catch (InternationalStudentAvailabilityException e) {
      System.out.println("Sorry, as an international student, you can only work part time..\n");
    }
  }
  
  private void viewAvailabilities() {
    System.out.println("Your availabilities are now..");
    for (PositionType availability : currentUser.getAvailabilities()) {
      System.out.printf("%s ", availability);
    }
    System.out.println("\n");
  }
  
}
