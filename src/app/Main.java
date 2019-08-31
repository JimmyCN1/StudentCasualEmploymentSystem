package app;

import exceptions.EmployerDoesNotExistException;
import exceptions.PasswordMissmatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final int EMPLOYER = 1;
  private static final int STUDENT = 2;
  private static final int SYSTEM_MAINTENANCE_STAFF = 3;
  private static final int LOGIN = 4;
  private static final int QUIT = 0;
  
  private static final int EMPLOYER_NAME = 0;
  private static final int FIRST_NAME = 0;
  private static final int LAST_NAME = 1;
  private static final int PASSWORD = 2;
  
  public static void main(String[] args) {
    App app = new App();
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
      System.out.println("~~~Welcome to the Student Casual Employment System~~~\n");
      System.out.println("Please type the appropriate number to register or login:\n");
      System.out.println("1. Employer Registration\n" +
              "2. Student Registration\n" +
              "3. System Maintenance Staff Registration\n" +
              "4. Login\n\n" +
              "0. Press '0' to quit");
      
      int response = scanner.nextInt();
      
      switch (response) {
        case (EMPLOYER):
//          EmployerApp employerApp = new EmployerApp();
//          employerApp.createEmployer();
//          System.out.printf("%s has been registered to the system.\n\n",
//                  employerApp.getCurrentUser().getName());
          app.instantiateNewEmployerApp();
          break;
        case (STUDENT):
//          StudentApp studentApp = new StudentApp();
//          studentApp.selectStudentType();
//          System.out.printf("%s has been registered to the system.\n\n",
//                  studentApp.getCurrentUser().getName());
          app.instantiateNewStudentApp();
          break;
        case (SYSTEM_MAINTENANCE_STAFF):
//          SystemMaintenanceStaffApp systemMaintenanceStaffApp = new SystemMaintenanceStaffApp();
//          systemMaintenanceStaffApp.createSystemMaintenanceStaff();
//          System.out.printf("%s has been registered to the system.\n\n",
//                  systemMaintenanceStaffApp.getCurrentUser().getName());
          app.instantiateNewSystemMaintenanceStaffApp();
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
    Scanner scanner = new Scanner(System.in);
    List<String> userDetails = new ArrayList<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.add(employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.add(password);
    try {
      EmployerApp employerApp = new EmployerApp(userDetails.get(0), userDetails.get(1));
    } catch (EmployerDoesNotExistException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
    
  }
  
  private static void loginAsStudent() {
  
  
  }
  
  private static void loginAsSystemMaintenanceStaff() {
  
  }
}
