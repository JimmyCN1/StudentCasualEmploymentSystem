package app;

import enumerators.PositionType;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import exceptions.PasswordMissmatchException;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;

import java.util.Map;

public class StudentApp extends App implements AppInterface {
  private final int LOCAL = 1;
  private final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String firstName, String lastName, String password, ManagementSystem managementSystem)
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
    managementSystem.registerApplicant(new LocalStudent(studentDetails.get(FIRST_NAME),
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
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(FIRST_NAME),
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
  
  private void verifyUser(String firstName, String lastName, String password) throws UserNotFoundException, PasswordMissmatchException {
    Applicant applicant = managementSystem.getApplicantByName(firstName.toLowerCase() + lastName.toLowerCase());
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
      System.out.printf("What would you like to do\n\n?" +
              "2. Update Your Job Preferences\n" +
              "3. Update Your Availabilities\n" +
              "4. Update Your Employment Records\n" +
              "5. Accept/Reject A Job Offer");
    }
    
  }
}
