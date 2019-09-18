package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.Test;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;

import org.junit.Before;

import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.LocalStudent;
import model.user.staff.SystemMaintenanceStaff;

public class SystemMaintenanceStaffTest {
	
	private ManagementSystem managementSystem;

	  private SystemMaintenanceStaff staff1;
	  private SystemMaintenanceStaff staff2;
	  private SystemMaintenanceStaff staff3;
	  private SystemMaintenanceStaff staff4;
	  private SystemMaintenanceStaff staff5;
	  private SystemMaintenanceStaff staff6;
	  
	  private List<Applicant> applicants;

	  @Before
	  public void setup() {
		    managementSystem = new ManagementSystem();
		    
		    // Initialise all the employers
		    staff1 = new SystemMaintenanceStaff("Joshua", "Vergera", "jvs1", managementSystem);
		    staff2 = new SystemMaintenanceStaff("Allan", "Pierce", "aps2", managementSystem);
		    staff3 = new SystemMaintenanceStaff("Jayden", "Smith", "jss3", managementSystem);
		    staff4 = new SystemMaintenanceStaff("Chloe", "Decker", "cds4", managementSystem);
		    staff5 = new SystemMaintenanceStaff("Terry", "Jeffords", "tjs5", managementSystem);
		    staff6 = new SystemMaintenanceStaff("Bruce", "Wayne", "imbatman", managementSystem);
	  }
	@After
	static void tearDown() throws Exception {
	}

	@Test
	public void checkForPasswordMatch() {
	    assertTrue(staff1.verifyPassword("jvs1"));
	    assertFalse(staff1.verifyPassword("aps2"));
	  }
	
	@Test
	public void blacklistUserTest() throws InvalidUserStatusException {
		applicants = new ArrayList<>(Arrays.asList(
	            new LocalStudent(
	                    "Logan",
	                    "Howlett",
	                    "Xlogan",
	                    PositionType.PART_TIME,
	                    managementSystem)));
		
	    staff3.blacklistUser(applicants.get(0));
	    assertTrue(this.applicants.get(0).getStatus()==UserStatus.BLACKLISTED);
	}
	
	@Test
	public void addNewJobCategoryTest() {
		String jobCategory = "Insurance Agent";
		staff2.addNewJobCategory(jobCategory);
	    
		assertTrue(this.managementSystem.getJobCategories().contains(jobCategory));
	  }

}
