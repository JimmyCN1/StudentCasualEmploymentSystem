package app;

import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import model.system.ManagementSystem;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class App {
  protected final int EMPLOYER = 1;
  protected final int STUDENT = 2;
  protected final int SYSTEM_MAINTENANCE_STAFF = 3;
  final String EMPLOYER_NAME = "employerName";
  final String FIRST_NAME = "firstName";
  final String LAST_NAME = "lastName";
  final String PASSWORD = "password";
  public boolean goBack = false;
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
    while (!goBack) {
      try {
        System.out.println("Login as a:");
        System.out.println("1. Employer\n2. Student\n3. System Maintenance Staff\n\n0. Go Back");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (EMPLOYER):
            loginAsEmployer();
            break;
          case (STUDENT):
            loginAsStudent();
            break;
          case (SYSTEM_MAINTENANCE_STAFF):
            loginAsSystemMaintenanceStaff();
            break;
          case (0):
            goBack = true;
          default:
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
    goBack = false;
  }
  
  private void loginAsEmployer() {
    Map<String, String> userDetails = getEmployerDetails();
    try {
      employerApp = new EmployerApp(userDetails.get(EMPLOYER_NAME),
              userDetails.get(PASSWORD), managementSystem);
      employerApp.displayMainMenu();
    } catch (UserNotFoundException e) {
      printLoginMismatchMessage();
    } catch (PasswordMissmatchException e) {
      printLoginMismatchMessage();
    }
  }
  
  private void loginAsStudent() {
    Map<String, String> userDetails = getPersonalDetails();
    try {
      studentApp = new StudentApp(userDetails.get(FIRST_NAME),
              userDetails.get(LAST_NAME),
              userDetails.get(PASSWORD), managementSystem);
      studentApp.displayMainMenu();
    } catch (UserNotFoundException e) {
      printLoginMismatchMessage();
    } catch (PasswordMissmatchException e) {
      printLoginMismatchMessage();
    }
  }
  
  private void loginAsSystemMaintenanceStaff() {
    Map<String, String> userDetails = getPersonalDetails();
    try {
      systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(userDetails.get(FIRST_NAME),
              userDetails.get(LAST_NAME),
              userDetails.get(PASSWORD), managementSystem);
      systemMaintenanceStaffApp.displayMainMenu();
    } catch (UserNotFoundException e) {
      printLoginMismatchMessage();
    } catch (PasswordMissmatchException e) {
      printLoginMismatchMessage();
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
  
  // print this message in the catch block whenever the user does not
  // correctly select an option from a menu
  public void printInputMismatchMessage() {
    scanner.nextLine();
    System.out.println("Please type a number from the list..\n\n");
    scanner.next();
  }
  
  public void printLoginMismatchMessage() {
    System.out.println("\n!! Wrong user name or password !!\n");
  }
}
