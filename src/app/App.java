package app;

import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import model.system.ManagementSystem;
import model.user.User;

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
  final String USERNAME = "userName";
  final String PASSWORD = "password";
  
  private EmployerApp employerApp;
  private StudentApp studentApp;
  private SystemMaintenanceStaffApp systemMaintenanceStaffApp;
  
  private User currentUser;
  
  ManagementSystem managementSystem;
  public Scanner scanner = new Scanner(System.in);
  
  public App(ManagementSystem managementSystem) {
    this.managementSystem = managementSystem;
  }
  
  public User getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
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
    boolean goBack = false;
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
  }
  
  private void loginAsEmployer() {
    Map<String, String> userDetails = getLoginDetails();
    try {
      employerApp = new EmployerApp(userDetails.get(USERNAME),
              userDetails.get(PASSWORD), managementSystem);
      employerApp.displayMainMenu();
    } catch (UserNotFoundException e) {
      printLoginMismatchMessage();
    } catch (PasswordMissmatchException e) {
      printLoginMismatchMessage();
    }
  }
  
  private void loginAsStudent() {
    Map<String, String> userDetails = getLoginDetails();
    try {
      studentApp = new StudentApp(userDetails.get(USERNAME),
              userDetails.get(PASSWORD), managementSystem);
      studentApp.displayMainMenu();
    } catch (UserNotFoundException e) {
      printLoginMismatchMessage();
    } catch (PasswordMissmatchException e) {
      printLoginMismatchMessage();
    }
  }
  
  private void loginAsSystemMaintenanceStaff() {
    Map<String, String> userDetails = getLoginDetails();
    try {
      systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(userDetails.get(USERNAME),
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
    Map<String, String> loginDetails = getLoginDetails();
    userDetails.put(USERNAME, loginDetails.get(USERNAME));
    userDetails.put(PASSWORD, loginDetails.get(PASSWORD));
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
    Map<String, String> loginDetails = getLoginDetails();
    userDetails.put(USERNAME, loginDetails.get(USERNAME));
    userDetails.put(PASSWORD, loginDetails.get(PASSWORD));
    return userDetails;
  }
  
  public Map<String, String> getLoginDetails() {
    Map<String, String> loginDetails = new HashMap<>();
    System.out.println("What is your username?");
    String userName = scanner.nextLine();
    loginDetails.put(USERNAME, userName);
    System.out.println("What is your password?");
    String password = scanner.nextLine();
    loginDetails.put(PASSWORD, password);
    return loginDetails;
  }
  
  public void changeLoginDetails() {
    boolean goBack = false;
    System.out.println("Please type your password..");
    String password = scanner.nextLine();
    if (currentUser.verifyPassword(password)) {
      while (!goBack) {
        try {
          System.out.printf("What would you like to change?\n\n" +
                  "1. User Name\n" +
                  "2. Password\n\n" +
                  "0. Go Back");
          int response = scanner.nextInt();
          scanner.nextLine();
          switch (response) {
            case (1):
              changeUserName();
              break;
            case (2):
              changePassword();
              break;
            case (0):
              goBack = true;
          }
        } catch (InputMismatchException e) {
          printInputMismatchMessage();
        }
      }
    }
    printLoginMismatchMessage();
  }
  
  private void changeUserName() {
    System.out.println("What would you like to change your user name to?");
    String newUserName = scanner.nextLine();
    currentUser.setUsername(newUserName);
    System.out.println("Your user name has successfully been updated.\n");
  }
  
  private void changePassword() {
    System.out.println("What would you like to change your password to?");
    String password = scanner.nextLine();
    currentUser.setPassword(password);
    System.out.println("Your user name has successfully been updated.\n");
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
