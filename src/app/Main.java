package app;

import model.system.ManagementSystem;

import java.util.Scanner;

public class Main {
  private static final int EXIT_SUCCESS = 0;
  private static final int EMPLOYER = 1;
  private static final int STUDENT = 2;
  private static final int SYSTEM_MAINTENANCE_STAFF = 3;
  private static final int LOGIN = 4;
  private static final int QUIT = 0;
  
  public static void main(String[] args) {
    ManagementSystem managementSystem = new ManagementSystem();
    App app = new App(managementSystem);
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
      
      // determine which kind of app to instantiate or to login as a
      // previous user to a particular app
      switch (response) {
        case (EMPLOYER):
          app.instantiateNewEmployerApp();
          break;
        case (STUDENT):
          app.instantiateNewStudentApp();
          break;
        case (SYSTEM_MAINTENANCE_STAFF):
          app.instantiateNewSystemMaintenanceStaffApp();
          break;
        case (LOGIN):
          app.loginAs();
          break;
        case (QUIT):
          System.exit(EXIT_SUCCESS);
        default:
          break;
      }
    }
  }
}
