package app;

import driver.ManagementSystem;
import exceptions.EntityDoesNotExistException;
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
  }
  
  public void instantiateNewStudentApp() {
    studentApp = new StudentApp(managementSystem);
    studentApp.selectStudentType();
    
    System.out.printf("%s has been registered to the system.\n\n",
            studentApp.getCurrentUser().getName());
  }
  
  public void instantiateNewSystemMaintenanceStaffApp() {
    systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(managementSystem);
    systemMaintenanceStaffApp.createSystemMaintenanceStaff();
    System.out.printf("%s has been registered to the system.\n\n",
            systemMaintenanceStaffApp.getCurrentUser().getName());
  }
  
  public Map<String, String> getNewUserDetails() {
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
  
  public void loginAs() {
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
  
  private void loginAsEmployer() {
    scanner.nextLine();
    Map<String, String> userDetails = new HashMap<>();
    System.out.println("What is your company name?");
    String employerName = scanner.nextLine();
    userDetails.put(EMPLOYER_NAME, employerName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    userDetails.put(PASSWORD, password);
    try {
      employerApp = new EmployerApp(userDetails.get(EMPLOYER_NAME), userDetails.get(PASSWORD), managementSystem);
    } catch (EntityDoesNotExistException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  private void loginAsStudent() {
    Map<String, String> userDetails = getNewPersonDetails();
    try {
      studentApp = new StudentApp(userDetails.get(FIRST_NAME), userDetails.get(LAST_NAME), userDetails.get(PASSWORD), managementSystem);
    } catch (EntityDoesNotExistException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  private void loginAsSystemMaintenanceStaff() {
    Map<String, String> userDetails = getNewPersonDetails();
    try {
      systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(userDetails.get(FIRST_NAME), userDetails.get(LAST_NAME), userDetails.get(PASSWORD), managementSystem);
    } catch (EntityDoesNotExistException e) {
      e.printStackTrace();
    } catch (PasswordMissmatchException e) {
      e.printStackTrace();
    }
  }
  
  private Map<String, String> getNewPersonDetails() {
    scanner.nextLine();
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
