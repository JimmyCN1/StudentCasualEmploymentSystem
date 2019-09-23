package app;

import enumerators.UserStatus;
import exceptions.PasswordMissmatchException;
import exceptions.SystemMaintenanceStaffNotFoundException;
import exceptions.UserNotFoundException;
import interfaces.AppInterface;
import model.system.ManagementSystem;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;
import model.user.utilities.Complaint;

import java.util.InputMismatchException;
import java.util.List;
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
                "5. View Current BlackListed Users\n" +
                "6. Lodge A Complaint Against A User\n\n" +
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
            addNewJobCategory();
            break;
          case (4):
            viewCurrentJobCategories();
            break;
          case (5):
            viewBlackListedUsers();
            break;
          case (6):
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
  
  private void displayEmployerRecords() {
    for (Employer e : managementSystem.getEmployersAsList()) {
      System.out.println(e.toString());
    }
  }
  
  private void displayApplicantRecords() {
    for (Applicant a : managementSystem.getApplicantsAsList()) {
      System.out.println(a.toString());
    }
  }
  
  private void addNewJobCategory() {
    System.out.println("Type the job category you would like to add to the system..");
    String response = scanner.nextLine();
    System.out.println();
    managementSystem.addJobCategory(response);
    viewCurrentJobCategories();
  }
  
  private void viewCurrentJobCategories() {
    System.out.println("The current job categories loaded in the system are..");
    for (String jc : managementSystem.getJobCategories()) {
      System.out.printf("%s ", jc.toUpperCase());
    }
    System.out.println("\n");
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
  
  @Override
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
            lodgeComplaintAgainstEmployer();
            break;
          case (STUDENT):
            lodgeComplaintAgainstStudent();
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
    
  }
  
  private void lodgeComplaintAgainstEmployer() {
    System.out.println("Current Employers in the system..\n");
    displayEmployerRecords();
    System.out.println("What is the employers name?");
    String employerName = scanner.nextLine();
    List<Employer> employers = managementSystem.getEmployersAsList();
    boolean employerFound = false;
    for (Employer e : employers) {
      if (e.getName().equals(employerName)) {
        System.out.printf("Please type the complaint you would like to lodge against %s", employerName);
        String complaint = scanner.nextLine();
        currentUser.lodgeComplaint(new Complaint(complaint, e));
        System.out.printf("Complaint successfully lodged against %s\n\n", employerName);
      }
    }
    if (!employerFound) {
      System.out.println("Sorry, this employer was not found..\n");
    }
  }
  
  private void lodgeComplaintAgainstStudent() {
    System.out.println("Current Applicants in the system..\n");
    displayApplicantRecords();
    System.out.println("What is the applicants name?");
    String applicantName = scanner.nextLine();
    List<Applicant> applicants = managementSystem.getApplicantsAsList();
    boolean applicantFound = false;
    for (Applicant a : applicants) {
      if (a.getName().equals(applicantName)) {
        applicantFound = true;
        System.out.printf("Please type the complaint you would like to lodge against %s", applicantName);
        String complaint = scanner.nextLine();
        currentUser.lodgeComplaint(new Complaint(complaint, a));
        System.out.printf("Complaint successfully lodged against %s\n\n", applicantName);
      }
    }
    if (!applicantFound) {
      System.out.println("Sorry, this applicant was not found..\n");
    }
  }
  
  
}
