package app;

import entities.staff.SystemMaintenanceStaff;

import java.util.List;

public class SystemMaintenanceStaffApp extends App {
  private SystemMaintenanceStaff currentUser;
  
  public SystemMaintenanceStaffApp(String firstName, String lastName, String password) {
  
  }
  
  public SystemMaintenanceStaffApp() {
  }
  
  public void createSystemMaintenanceStaff() {
    List<String> systemMaintenanceStaffDetails = createUser();
    managementSystem.registerSystemMaintenanceStaff(
            new SystemMaintenanceStaff(
                    systemMaintenanceStaffDetails.get(0),
                    systemMaintenanceStaffDetails.get(1),
                    systemMaintenanceStaffDetails.get(2),
                    managementSystem));
  }
}
