package app;

import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;
import exceptions.PasswordMissmatchException;
import exceptions.SystemMaintenanceStaffNotFoundException;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import model.user.User;
import model.user.staff.SystemMaintenanceStaff;

import java.util.InputMismatchException;
import java.util.Map;

public class SystemMaintenanceStaffApp extends App implements AppInterface {
  private SystemMaintenanceStaff currentUser;
  
  public SystemMaintenanceStaffApp(String username,
                                   String password,
                                   ManagementSystem managementSystem)
          throws UserNotFoundException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(username, password);
  }
  
  public SystemMaintenanceStaffApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  @Override
  public SystemMaintenanceStaff getCurrentUser() {
    return currentUser;
  }
  
  // set the current user logged in
  @Override
  public void setCurrentUser(User staff) {
    super.setCurrentUser(staff);
    currentUser = (SystemMaintenanceStaff) staff;
  }
  
  public void createSystemMaintenanceStaff() {
    Map<String, String> systemMaintenanceStaffDetails = getPersonalDetails();
    SystemMaintenanceStaff newSystemMaintenanceStaff = new SystemMaintenanceStaff(
            systemMaintenanceStaffDetails.get(FIRST_NAME),
            systemMaintenanceStaffDetails.get(LAST_NAME),
            systemMaintenanceStaffDetails.get(PASSWORD),
            managementSystem);
    newSystemMaintenanceStaff.setUsername(systemMaintenanceStaffDetails.get(USERNAME));
    managementSystem.registerSystemMaintenanceStaff(newSystemMaintenanceStaff);
    try {
      setCurrentUser(managementSystem.getSystemMaintenanceByUsername(
              systemMaintenanceStaffDetails.get(USERNAME).toLowerCase()
      ));
    } catch (SystemMaintenanceStaffNotFoundException e) {
      System.out.println("Error creating system staff. Please try again..");
    }
  }
  
  // determines whether the login details provided are provided
  private void verifyUser(String username, String password)
          throws UserNotFoundException, PasswordMissmatchException {
    SystemMaintenanceStaff systemMaintenanceStaff = managementSystem.getSystemMaintenanceByUsername(username);
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
                "3. Add A New Job Category\n" +
                "4. View Current Job Categories\n" +
                "5. Blacklist A User\n" +
                "7. View Current BlackListed Users\n" +
                "7. Lodge A Complaint Against A User\n\n" +
                "0. Logout\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            displayEmployers();
            break;
          case (2):
            displayApplicants();
            break;
          case (3):
            addNewJobCategory();
            break;
          case (4):
            viewCurrentJobCategories();
            break;
          case (5):
            blackListAUser();
            break;
          case (6):
            viewBlackListedUsers();
            break;
          case (7):
            lodgeAComplaint();
          case (0):
            isLoggedIn = false;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
  }
  
  private void addNewJobCategory() {
    System.out.println("Type the job category you would like to add to the system..");
    String response = scanner.nextLine();
    System.out.println();
    currentUser.addNewJobCategory(response);
  }
  
  private void viewCurrentJobCategories() {
    System.out.println("The current job categories loaded in the system are..");
    for (String jc : managementSystem.getJobCategories()) {
      System.out.printf("%s ", jc.toUpperCase());
    }
    System.out.println("\n");
  }
  
  private void blackListAUser() {
    System.out.println("These are the current users in the system..\n");
    for (int i = 0; i < managementSystem.getUsersAsList().size(); i++) {
      System.out.printf("%d. %s", i + 1, managementSystem.getUsersAsList().get(i).toString());
    }
    User user;
    boolean goBack = false;
    while (!goBack) {
      try {
        System.out.printf("Which entity would you like to lodge a complaint against?\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        if (response == 0) {
          goBack = true;
        } else {
          user = managementSystem.getUsersAsList().get(response - 1);
          currentUser.blacklistUser(user);
          System.out.println("User was successfully blacklisted..");
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      } catch (InvalidUserStatusException e) {
        System.out.println("Sorry, there was an error in completing this task.\n" +
                "Please try again..\n");
      }
    }
  }
  
  private void viewBlackListedUsers() {
    System.out.println("These are the current users on the blacklist..");
    Map<String, User> blacklist = managementSystem.getBlacklistedUsers();
    for (String key : blacklist.keySet()) {
      User user = blacklist.get(key);
      if (user.equals(UserStatus.BLACKLISTED)) {
        System.out.println(user.toString());
      }
    }
    System.out.println();
  }
  
  public void lodgeAComplaint() {
    boolean goBack = false;
    while (!goBack) {
      try {
        System.out.printf("Which entity would you like to lodge a complaint against?\n\n" +
                "1. Employer\n" +
                "2. Student\n\n" +
                "0. Go Back\n\n");
        int response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (EMPLOYER):
            super.lodgeComplaintAgainstEmployer();
            break;
          case (STUDENT):
            super.lodgeComplaintAgainstStudent();
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
  }
  
  
}
