package app;

import enumerators.PositionType;
import model.system.ManagementSystem;
import exceptions.EntityDoesNotExistException;
import exceptions.PasswordMissmatchException;
import model.applicant.Applicant;
import model.applicant.InternationalStudent;
import model.applicant.LocalStudent;

import java.util.Map;

public class StudentApp extends App {
  private final int LOCAL = 1;
  private final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String firstName, String lastName, String password, ManagementSystem managementSystem)
          throws EntityDoesNotExistException, PasswordMissmatchException {
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
  
  public void createLocalStudent() {
    Map<String, String> studentDetails = getNewUserDetails();
    managementSystem.registerApplicant(new LocalStudent(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            PositionType.FULL_TIME,
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(
            studentDetails.get(FIRST_NAME).toLowerCase() +
                    studentDetails.get(LAST_NAME).toLowerCase()
    ));
  }
  
  public void createInternationalStudent() {
    Map<String, String> studentDetails = getNewUserDetails();
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(
            studentDetails.get(FIRST_NAME).toLowerCase() +
                    studentDetails.get(LAST_NAME).toLowerCase()
    ));
  }
  
  private void verifyUser(String firstName, String lastName, String password) throws EntityDoesNotExistException, PasswordMissmatchException {
    Applicant applicant = managementSystem.getApplicantByName(firstName.toLowerCase() + lastName.toLowerCase());
    if (applicant == null) {
      throw new EntityDoesNotExistException();
    } else {
      if (!applicant.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(applicant);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
}
