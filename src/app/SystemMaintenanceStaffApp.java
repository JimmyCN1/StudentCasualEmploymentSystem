package app;

import exceptions.EntityNotFoundException;
import model.system.ManagementSystem;
import exceptions.PasswordMissmatchException;
import model.staff.SystemMaintenanceStaff;

import java.util.Map;

public class SystemMaintenanceStaffApp extends App {
  private SystemMaintenanceStaff currentUser;
  
  public SystemMaintenanceStaffApp(String firstName, String lastName, String password, ManagementSystem managementSystem)
          throws EntityNotFoundException, PasswordMissmatchException {
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
  
  private void verifyUser(String firstName, String lastName, String password)
          throws EntityNotFoundException, PasswordMissmatchException {
    SystemMaintenanceStaff systemMaintenanceStaff = managementSystem.getSystemMaintenanceByName(firstName.toLowerCase() + lastName.toLowerCase());
    if (systemMaintenanceStaff == null) {
      throw new EntityNotFoundException();
    } else {
      if (!systemMaintenanceStaff.verifyPassword(password)) {
        throw new PasswordMissmatchException();
      } else {
        setCurrentUser(systemMaintenanceStaff);
        System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
      }
    }
  }
}
