package app;

import entities.applicant.Applicant;
import entities.applicant.InternationalStudent;
import entities.applicant.LocalStudent;

import java.util.List;

public class StudentApp extends App {
  private static final int LOCAL = 1;
  private static final int INTERNATIONAL = 2;
  private Applicant currentUser;
  
  public StudentApp(String firstName, String lastName, String password) {
  
  }
  
  public StudentApp() {
  }
  
  public void selectStudentType() {
    boolean validResponse = false;
    while (!validResponse) {
      System.out.println("Which type of student are you?");
      System.out.println("1. Local\n2. International");
      int response = scanner.nextInt();
      
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
  
  private void createLocalStudent() {
    List<String> studentDetails = createUser();
    managementSystem.registerApplicant(new LocalStudent(studentDetails.get(0),
            studentDetails.get(1),
            studentDetails.get(2),
            managementSystem));
  }
  
  private void createInternationalStudent() {
    List<String> studentDetails = createUser();
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(0),
            studentDetails.get(1),
            studentDetails.get(2),
            managementSystem));
  }
}
