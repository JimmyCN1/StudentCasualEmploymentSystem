package app;

import driver.ManagementSystem;
import exceptions.EntityDoesNotExistException;
import exceptions.PasswordMissmatchException;
import interfaces.applicant.Applicant;
import interfaces.applicant.InternationalStudent;
import interfaces.applicant.LocalStudent;

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
    Map<String, String> studentDetails = createUser();
    managementSystem.registerApplicant(new LocalStudent(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME)));
  }
  
  public void createInternationalStudent() {
    Map<String, String> studentDetails = createUser();
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME)));
  }
  
  private void verifyUser(String firstName, String lastName, String password) throws EntityDoesNotExistException, PasswordMissmatchException {
    Applicant applicant = managementSystem.getApplicantByName(firstName, lastName);
    if (applicant == null) {
      throw new EntityDoesNotExistException();
    } else {
      if (!applicant.isPasswordMatch(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(applicant);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
}
