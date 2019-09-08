package app;

import exceptions.UserNotFoundException;
import model.system.ManagementSystem;
import exceptions.PasswordMissmatchException;

import java.util.*;

public class App {
  private final int EMPLOYER = 1;
  private final int STUDENT = 2;
  private final int SYSTEM_MAINTENANCE_STAFF = 3;
  final String EMPLOYER_NAME = "employerName";
  final String FIRST_NAME = "firstName";
  final String LAST_NAME = "lastName";
  final String PASSWORD = "password";
  public boolean isValidResponse = false;
  private EmployerApp employerApp;
  private StudentApp studentApp;
  private SystemMaintenanceStaffApp systemMaintenanceStaffApp;
  ManagementSystem managementSystem;
  public Scanner scanner = new Scanner(System.in);
  
  public App(ManagementSystem managementSystem) {
    this.managementSystem = managementSystem;
  }
  
  public void instantiateNewEmployerApp() {
    employerApp = new EmployerApp(managementSystem);
    employerApp.createEmployer();
    System.out.printf("%s has been registered to the system.\n\n",
            employerApp.getCurrentUser().getName());
    employerApp.displayMainMenu();
  }
  
  public void instantiateNewStudentApp() {
    studentApp = new StudentApp(managementSystem);
    studentApp.selectStudentType();
    
    System.out.printf("%s has been registered to the system.\n\n",
            studentApp.getCurrentUser().getName());
    studentApp.displayMainMenu();
  }
  
  public void instantiateNewSystemMaintenanceStaffApp() {
    systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(managementSystem);
    systemMaintenanceStaffApp.createSystemMaintenanceStaff();
    System.out.printf("%s has been registered to the system.\n\n",
            systemMaintenanceStaffApp.getCurrentUser().getName());
    systemMaintenanceStaffApp.displayMainMenu();
  }
  
  // login as an employer, applicant or system staff
  public void loginAs() {
    while (!isValidResponse) {
      try {
        System.out.println("Login as a:");
        System.out.println("1. Employer\n2. Student\n3. System Maintenance Staff");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (EMPLOYER):
            isValidResponse = true;
            loginAsEmployer();
            employerApp.displayMainMenu();
            break;
          case (STUDENT):
            isValidResponse = true;
            loginAsStudent();
            studentApp.displayMainMenu();
            break;
          case (SYSTEM_MAINTENANCE_STAFF):
            isValidResponse = true;
            loginAsSystemMaintenanceStaff();
            systemMaintenanceStaffApp.displayMainMenu();
            break;
          default:
            break;
        }
      } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Please try again..\n\n");
        scanner.next();
      }
      
    }
    isValidResponse = false;
  }
  
  private void loginAsEmployer() {
    Map<String, String> userDetails = getEmployerDetails();
    try {
      employerApp = new EmployerApp(userDetails.get(EMPLOYER_NAME),
              userDetails.get(PASSWORD), managementSystem);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  private void loginAsStudent() {
    Map<String, String> userDetails = getPersonalDetails();
    try {
      studentApp = new StudentApp(userDetails.get(FIRST_NAME),
              userDetails.get(LAST_NAME),
              userDetails.get(PASSWORD), managementSystem);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  private void loginAsSystemMaintenanceStaff() {
    Map<String, String> userDetails = getPersonalDetails();
    try {
      systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(userDetails.get(FIRST_NAME),
              userDetails.get(LAST_NAME),
              userDetails.get(PASSWORD), managementSystem);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  // returns the employer's name and password as a map
  public Map<String, String> getEmployerDetails() {
    Map<String, String> userDetails = new HashMap<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.put(EMPLOYER_NAME, employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.put(PASSWORD, password);
    return userDetails;
  }
  
  // returns the person's first and last name and password as a map
  public Map<String, String> getPersonalDetails() {
    Map<String, String> userDetails = new HashMap<>();
    System.out.println("What is your first name?");
    String firstName = scanner.nextLine();
    userDetails.put(FIRST_NAME, firstName);
    System.out.println("What is your last name?");
    String lastName = scanner.nextLine();
    userDetails.put(LAST_NAME, lastName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.put(PASSWORD, password);
    return userDetails;
  }
}
