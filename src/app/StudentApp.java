package app;

import enumerators.PositionType;
import exceptions.InvalidJobCategoryException;
import exceptions.JobCategoryNotFoundException;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.position.Position;
import model.system.ManagementSystem;
import exceptions.PasswordMissmatchException;
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
  
  // TODO: make localStudent availability a variable
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
  
  @Override
  public void displayMainMenu() {
    boolean isLoggedIn = true;
    while (isLoggedIn) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Update Your Job Preferences\n" +
                "3. Update Your Availabilities\n" +
                "4. Update Your Employment Records\n" +
                "5. Accept/Reject A Job Offer\n\n" +
                "0. Logout\n\n");
        
        switch (scanner.nextInt()) {
          case (1):
            updateJobPreferences();
            break;
          case (2):
            break;
          case (3):
            break;
          case (4):
            break;
          case (5):
            break;
          case (0):
            isLoggedIn = false;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Please try again..\n\n");
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
      System.out.println("Your job preferences are now..");
      for (String jp : currentUser.getJobPreferences()) {
        System.out.printf("%s ", jp);
      }
      System.out.println("\n");
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
      System.out.println("Your job preferences are now..");
      for (String jp : currentUser.getJobPreferences()) {
        System.out.printf("%s ", jp);
      }
      System.out.println("\n");
    } catch (JobCategoryNotFoundException e) {
      System.out.printf("%s was not found..\n\n", response);
    }
  }
}
