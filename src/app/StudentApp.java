package app;

import applicant.InternationalStudent;
import applicant.LocalStudent;
import driver.ManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentApp {
  private static final int LOCAL = 1;
  private static final int INTERNATIONAL = 2;
  private ManagementSystem managementSystem;
  private Scanner scanner = new Scanner(System.in);
  
  public StudentApp() {
    managementSystem = new ManagementSystem();
  }
  
  public void selectStudentType() {
    boolean validResponse = false;
    while (!validResponse) {
      System.out.println("Which type of student are you?");
      System.out.println("1. Local\n2. International");
      int response = 0;
      response = scanner.nextInt();
      
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
    List<String> studentDetails = createStudent();
    managementSystem.registerApplicant(new LocalStudent(studentDetails.get(0), studentDetails.get(1), studentDetails.get(2)));
  }
  
  public void createInternationalStudent() {
    List<String> studentDetails = createStudent();
    managementSystem.registerApplicant(new InternationalStudent(studentDetails.get(0), studentDetails.get(1), studentDetails.get(2)));
  }
  
  public List<String> createStudent() {
    List<String> studentDetails = new ArrayList<>();
    System.out.println("What is your first name?");
    String firstName = scanner.nextLine();
    studentDetails.add(firstName);
    System.out.println("What is your last name?");
    String lastName = scanner.nextLine();
    studentDetails.add(lastName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    studentDetails.add(password);
    return studentDetails;
  }
}
