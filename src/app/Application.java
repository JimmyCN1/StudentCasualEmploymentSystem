package app;

import java.util.Scanner;

public class Application {
  private static final int EMPLOYER = 1;
  private static final int STUDENT = 2;
  private static final int SYSTEM_MAINTENANCE_STAFF = 3;
  private static final int LOGIN = 4;
  private static final int QUIT = 0;
  
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
      System.out.println("~~~Welcome to the Student Casual Employment System~~~\n");
      System.out.println("Please type the corresponding number to determine who you are:");
      System.out.println("1. Employer Registration\n" +
              "2. Student Registration\n" +
              "3.System Maintenance Staff Registration\n" +
              "4. Login\n\n" +
              "0. Press '0' to quit");
      
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
          SystemMaintenanceStaffApp systemMaintenanceStaffApp = new SystemMaintenanceStaffApp();
          systemMaintenanceStaffApp.createSystemMaintenanceStaff();
          break;
        case (LOGIN):
          loginAs();
          break;
        case (QUIT):
          System.exit(0);
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
