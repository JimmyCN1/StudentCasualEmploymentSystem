package app;

import driver.ManagementSystem;
import employer.Employer;

import java.util.List;

public class SystemMaintenanceStaffApp extends App {
//  private ManagementSystem managementSystem;
  
  public SystemMaintenanceStaffApp(ManagementSystem managementSystem) {
//    this.managementSystem = managementSystem;
  }
  
  public void createSystemMaintenanceStaff() {
    List<String> systemMaintenanceStaffDetails = createUser();
    managementSystem.registerEmployer(new Employer(systemMaintenanceStaffDetails.get(0),
            systemMaintenanceStaffDetails.get(1),
            systemMaintenanceStaffDetails.get(2),
            managementSystem));
  }
}
