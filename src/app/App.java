package app;

import driver.ManagementSystem;
import interfaces.Entity;
import interfaces.staff.SystemMaintenanceStaff;

import java.util.*;

public class App {
  final String FIRST_NAME = "firstName";
  final String LAST_NAME = "lastName";
  final String PASSWORD = "password";
  private EmployerApp employerApp;
  private StudentApp studentApp;
  private SystemMaintenanceStaffApp systemMaintenanceStaffApp;
  ManagementSystem managementSystem;
  public Scanner scanner = new Scanner(System.in);
  
  public App() {
    this.managementSystem = new ManagementSystem();
  }
  
  public void instantiateNewEmployerApp() {
    employerApp = new EmployerApp();
    employerApp.createEmployer();
    System.out.printf("%s has been registered to the system.\n\n",
            employerApp.getCurrentUser().getName());
  }
  
  public void instantiateNewStudentApp() {
    studentApp = new StudentApp();
    studentApp.selectStudentType();
    System.out.printf("%s has been registered to the system.\n\n",
            studentApp.getCurrentUser().getName());
  }
  
  public void instantiateNewSystemMaintenanceStaffApp() {
    systemMaintenanceStaffApp = new SystemMaintenanceStaffApp();
    systemMaintenanceStaffApp.createSystemMaintenanceStaff();
    System.out.printf("%s has been registered to the system.\n\n",
            systemMaintenanceStaffApp.getCurrentUser().getName());
  }
  
  public Map<String, String> createUser() {
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
