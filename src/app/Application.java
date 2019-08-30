package app;

//import employer.Employer;

import enums.Entity;

import java.util.Scanner;

public class Application {
  private static final int EMPLOYER = 1;
  private static final int STUDENT = 2;
  private static final int SYSTEM_MAINTENANCE_STAFF = 3;
  private static final int LOGIN = 4;
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
      System.out.println("~~~Welcome to the Student Casual Employment System~~~\n");
      System.out.println("Please type the corresponding number to determine who you are:");
      System.out.println("1. Employer Registration\n2. Student Registration\n3.System Maintenance Staff Registration\n4. Login");
      
      int response = scanner.nextInt();
      
      switch (response) {
        case (EMPLOYER):
          EmployerApp employerApp = new EmployerApp();
          employerApp.createEmployer();
          break;
        case (STUDENT):
          StudentApp studentApp = new StudentApp();
          studentApp.selectStudentType();
          break;
        case (SYSTEM_MAINTENANCE_STAFF):
          //TODO: implement maintenance staff functionality
          break;
        case (LOGIN):
          // TODO: implement login functionality
          loginAs();
          break;
        default:
          break;
      }
    }
  }
  
  private static void loginAs() {
    Scanner scanner = new Scanner(System.in);
    boolean validResponse = false;
    while (!validResponse) {
      System.out.println("Login as a:");
      System.out.println("1. Employer\n2. Student\n3. System MaintenanceStaff");
      int response = scanner.nextInt();
      switch (response) {
        case (EMPLOYER):
          validResponse = true;
          loginAsEmployer();
          break;
        case (STUDENT):
          validResponse = true;
          loginAsStudent();
          break;
        case (SYSTEM_MAINTENANCE_STAFF):
          validResponse = true;
          loginAsSystemMaintenanceStaff();
          break;
        default:
          break;
      }
    }
  }
  
  private static void loginAsEmployer() {
  
  }
  
  private static void loginAsStudent() {
  
  
  }
  
  private static void loginAsSystemMaintenanceStaff() {
  
  }
}
