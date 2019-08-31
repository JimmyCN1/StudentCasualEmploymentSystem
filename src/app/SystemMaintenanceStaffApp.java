package app;

import driver.ManagementSystem;
import exceptions.EntityDoesNotExistException;
import exceptions.PasswordMissmatchException;
import interfaces.staff.SystemMaintenanceStaff;

import java.util.Map;

public class SystemMaintenanceStaffApp extends App {
  private SystemMaintenanceStaff currentUser;
  
  public SystemMaintenanceStaffApp(String firstName, String lastName, String password, ManagementSystem managementSystem)
          throws EntityDoesNotExistException, PasswordMissmatchException {
    super(managementSystem);
    verifyUser(firstName, lastName, password);
  }
  
  public SystemMaintenanceStaffApp(ManagementSystem managementSystem) {
    super(managementSystem);
  }
  
  
  public SystemMaintenanceStaff getCurrentUser() {
    return currentUser;
  }
  
  public void setCurrentUser(SystemMaintenanceStaff staff) {
    currentUser = staff;
  }
  
  public void createSystemMaintenanceStaff() {
    Map<String, String> systemMaintenanceStaffDetails = createUser();
    managementSystem.registerSystemMaintenanceStaff(
            new SystemMaintenanceStaff(
                    systemMaintenanceStaffDetails.get(FIRST_NAME),
                    systemMaintenanceStaffDetails.get(LAST_NAME),
                    systemMaintenanceStaffDetails.get(PASSWORD),
                    managementSystem));
    setCurrentUser(managementSystem.getSystemMaintenanceByName(
            systemMaintenanceStaffDetails.get(FIRST_NAME),
            systemMaintenanceStaffDetails.get(LAST_NAME)
    ));
  }
  
  private void verifyUser(String firstName, String lastName, String password) throws EntityDoesNotExistException, PasswordMissmatchException {
    SystemMaintenanceStaff systemMaintenanceStaff = managementSystem.getSystemMaintenanceByName(firstName, lastName);
    if (systemMaintenanceStaff == null) {
      throw new EntityDoesNotExistException();
    } else {
      if (!systemMaintenanceStaff.isPasswordMatch(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(systemMaintenanceStaff);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
}