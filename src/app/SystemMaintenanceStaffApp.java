package app;

import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import exceptions.PasswordMissmatchException;
import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;

import java.util.InputMismatchException;
import java.util.Map;

public class SystemMaintenanceStaffApp extends App implements AppInterface {
  private SystemMaintenanceStaff currentUser;
  
  public SystemMaintenanceStaffApp(String firstName,
                                   String lastName,
                                   String password,
                                   ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(firstName, lastName, password);
  }
  
  public SystemMaintenanceStaffApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  
  public SystemMaintenanceStaff getCurrentUser() {
    return currentUser;
  }
  
  // set the current user logged in
  public void setCurrentUser(SystemMaintenanceStaff staff) {
    currentUser = staff;
  }
  
  public void createSystemMaintenanceStaff() {
    Map<String, String> systemMaintenanceStaffDetails = getPersonalDetails();
    managementSystem.registerSystemMaintenanceStaff(
            new SystemMaintenanceStaff(
                    systemMaintenanceStaffDetails.get(FIRST_NAME),
                    systemMaintenanceStaffDetails.get(LAST_NAME),
                    systemMaintenanceStaffDetails.get(PASSWORD),
                    managementSystem));
    setCurrentUser(managementSystem.getSystemMaintenanceByName(
            systemMaintenanceStaffDetails.get(FIRST_NAME).toLowerCase() +
                    systemMaintenanceStaffDetails.get(LAST_NAME).toLowerCase()
    ));
  }
  
  // determines whether the login details provided are provided
  private void verifyUser(String firstName, String lastName, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    SystemMaintenanceStaff systemMaintenanceStaff = managementSystem.getSystemMaintenanceByName(firstName.toLowerCase() + lastName.toLowerCase());
    if (systemMaintenanceStaff == null) {
      throw new UserNotFoundException();
    } else {
      if (!systemMaintenanceStaff.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(systemMaintenanceStaff);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
  
  @Override
  public void displayMainMenu() {
    boolean isLoggedIn = true;
    while (isLoggedIn) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. View Employer Records\n" +
                "2. View Applicant Records\n" +
                "3. Add A New Job Category\n\n" +
                "0. Logout\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            displayEmployerRecords();
            break;
          case (2):
            displayApplicantRecords();
            break;
          case (3):
            break;
          case (0):
            isLoggedIn = false;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Please try again..\n\n");
        scanner.next();
      }
    }
  }
  
  // TODO: flesh out, impl. toString methods or provide more specific functionality
  public void displayEmployerRecords() {
    for (Employer e : managementSystem.getEmployers()) {
      System.out.println(e.toString());
    }
  }
  
  public void displayApplicantRecords() {
    for (Applicant a : managementSystem.getApplicants()) {
      System.out.println(a.toString());
    }
  }
}
