package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.Before;

import model.system.ManagementSystem;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;

public class SystemMaintenanceStaffTest {
	
	private ManagementSystem managementSystem;

	  private SystemMaintenanceStaff staff1;
	  private SystemMaintenanceStaff staff2;
	  private SystemMaintenanceStaff staff3;
	  private SystemMaintenanceStaff staff4;
	  private SystemMaintenanceStaff staff5;
	  private SystemMaintenanceStaff staff6;

	  public void setup() {
		    managementSystem = new ManagementSystem();
		    
		    // Initialise all the employers
		    staff1 = new SystemMaintenanceStaff("Joshua", "Vergera", "jvs1", managementSystem);
		    staff2 = new SystemMaintenanceStaff("Allan", "Pierce", "aps2", managementSystem);
		    staff3 = new SystemMaintenanceStaff("Jayden", "Smith", "jss3", managementSystem);
		    staff4 = new SystemMaintenanceStaff("Chloe", "Decker", "cds4", managementSystem);
		    staff5 = new SystemMaintenanceStaff("Terry", "Jeffords", "tjs5", managementSystem);
		    staff6 = new SystemMaintenanceStaff("Bruce", "Wayne", "bws6", managementSystem);
	  }
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
