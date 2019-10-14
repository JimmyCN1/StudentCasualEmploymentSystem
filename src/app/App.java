package app;

import exceptions.JobCategoryAlreadyExistsException;
import exceptions.PasswordMissmatchException;
import exceptions.UserNotFoundException;
import model.system.ManagementSystem;

import java.util.InputMismatchException;
import java.util.Map;

public class App extends AbstractApp {
  private final String USERNAME = "userName";
  private final String PASSWORD = "password";
  
  private EmployerApp employerApp;
  private StudentApp studentApp;
  private SystemMaintenanceStaffApp systemMaintenanceStaffApp;
  
  public App(ManagementSystem managementSystem) {
    super(managementSystem);
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
  
  public void instantiateNewSystemMaintenanceStaffApp() throws JobCategoryAlreadyExistsException {
    systemMaintenanceStaffApp = new SystemMaintenanceStaffApp(managementSystem);
    systemMaintenanceStaffApp.createSystemMaintenanceStaff();
    System.out.printf("%s has been registered to the system.\n\n",
            systemMaintenanceStaffApp.getCurrentUser().getName());
    systemMaintenanceStaffApp.displayMainMenu();
  }
  
  // login as an employer, applicant or system staff
  public void loginAs() throws JobCategoryAlreadyExistsException{
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
  
  private void loginAsSystemMaintenanceStaff() throws JobCategoryAlreadyExistsException {
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
}
