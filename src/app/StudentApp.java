package app;

import interfaces.applicant.Applicant;
import interfaces.applicant.InternationalStudent;
import interfaces.applicant.LocalStudent;

import java.util.Map;

public class StudentApp extends App {
  private final int LOCAL = 1;
  private final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String firstName, String lastName, String password) {
  
  }
  
  public StudentApp() {
  }
  
  public Applicant getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(Applicant applicant) {
    currentUser = applicant;
  }
  
  public void something() {
    Applicant currentUser = (Applicant) getCurrentUser();
    
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
    setCurrentUser(managementSystem.getApplicantByName(FIRST_NAME, LAST_NAME));
  }
  
  public void createInternationalStudent() {
    Map<String, String> studentDetails = createUser();
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(FIRST_NAME),
            studentDetails.get(LAST_NAME),
            studentDetails.get(PASSWORD),
            managementSystem));
    setCurrentUser(managementSystem.getApplicantByName(FIRST_NAME, LAST_NAME));
  }
}
