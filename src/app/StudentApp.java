package app;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import interfaces.AppInterface;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.employer.Employer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class StudentApp extends App implements AppInterface {
  private final int LOCAL = 1;
  private final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String username,
                    String password,
                    ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(username, password);
  }
  
  public StudentApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  @Override
  public Applicant getCurrentUser() {
    return currentUser;
  }
  
  // set the current user logged in
  @Override
  public void setCurrentUser(User applicant) {
    super.setCurrentUser(applicant);
    currentUser = (Applicant) applicant;
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
  
  // creates a new local student in the application
  private void createLocalStudent() {
    Map<String, String> studentDetails = getPersonalDetails();
    PositionType positionType = getStudentAvailability();
    LocalStudent newLocalStudent = new LocalStudent(
            studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            positionType,
            managementSystem);
    newLocalStudent.setUsername(studentDetails.get(USERNAME));
    managementSystem.registerApplicant(newLocalStudent);
    try {
      setCurrentUser(managementSystem.getApplicantByUsername(
              studentDetails.get(USERNAME).toLowerCase()
      ));
    } catch (ApplicantNotFoundException e) {
      System.out.println("Error creating applicant. Please try again");
    }
  }
  
  // creates a new international student in the application
  private void createInternationalStudent() {
    Map<String, String> studentDetails = getPersonalDetails();
    managementSystem.registerApplicant(new InternationalStudent(
            studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    try {
      setCurrentUser(managementSystem.getApplicantByUsername(
              studentDetails.get(USERNAME).toLowerCase()
      ));
    } catch (ApplicantNotFoundException e) {
      System.out.println("Error creating applicant. Please try again");
    }
  }
  
  // returns the selected availability
  private PositionType getStudentAvailability() {
    PositionType type = null;
    boolean goBack = false;
    while (!goBack) {
      System.out.println("What is your availability?");
      System.out.printf("1. Part-Time\n2. Full-Time\n3. Internship\n\n");
      switch (scanner.nextInt()) {
        case (1):
          goBack = true;
          type = PositionType.PART_TIME;
          break;
        case (2):
          goBack = true;
          type = PositionType.FULL_TIME;
          break;
        case (3):
          goBack = true;
          type = PositionType.INTERNSHIP;
          break;
      }
    }
    return type;
  }
  
  // determines whether the login details provided are provided
  private void verifyUser(String username, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    Applicant applicant = managementSystem.getApplicantByUsername(username);
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
    //TODO: updating employement records
    //TODO: uploading of cv (text files) option
    //TODO: apply to jobs
    //TODO: selecting interview slot/time
    //TODO: accept/reject job offers
    boolean isLoggedIn = true;
    int response;
    while (isLoggedIn) {
      if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
        isLoggedIn = showBlacklistedScreen();
      } else {
        try {
          System.out.printf("What would you like to do?\n\n" +
                  "1. Update Your Job Preferences\n" +
                  "2. Update Your Availabilities\n" +
                  "3. Update Your Employment Records\n" +
                  "4. View All Currently Posted Jobs\n" +
                  "5. Apply For A Job\n" +
                  "6. View Jobs Shorlisted For\n" +
                  "7. View Job Offers\n" +
                  "8. View Emails\n" +
                  "9. Change Login Details\n\n" +
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
//              viewAllCurrentlyPostedJobs():
              break;
            case (5):
              applyForAJob();
              break;
            case (6):
//              viewJobsShortlistedFor();
              break;
            case (7):
//                viewJobOffers();
              break;
            case (8):
//                viewEmails();
              break;
            case (9):
              changeLoginDetails();
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
  
  private void updateJobPreferences() {
    boolean goBack = false;
    while (!goBack) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Add A Job Preference\n" +
                "2. Remove A Job Preference\n" +
                "3. View Preferences\n" +
                "4. Lodge A Complaint\n\n" +
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
          case (4):
            lodgeComplaintAgainstEmployer();
            break;
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
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
        printInputMismatchMessage();
      }
    }
  }
  
  private void applyForAJob() {
    List<Position> allPosiitons = new ArrayList<>();
    for (Employer e : managementSystem.getEmployersAsList()) {
      allPosiitons.addAll(e.getPositions());
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
