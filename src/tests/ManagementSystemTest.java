import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;
import model.user.User;
import model.system.ManagementSystem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;


public class ManagementSystemTest {
    private ManagementSystem managementSystem;
    private User user1;
    private Employer employer1;
    private Employer employer2;
    private Applicant applicant1;
    private SystemMaintenanceStaff staff1;
    private SystemMaintenanceStaff staff2;


    @Before
    public void setup() {
        managementSystem = new ManagementSystem();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testManagementSystem() {
        assertTrue(true);
    }


    @Test
    public void testGetUserAsList() {
//        List<User> userList = Arrays.asList(
//                user1 = new User("jess", managementSystem)
//        );
    }

    @Test
    public void testGetEmployersAsList() {

        List<Employer> employers = Arrays.asList(
                employer1 = new Employer("Jack", "abc", managementSystem),
                employer2 = new Employer("Back", "abc", managementSystem)
        );
    }

    @Test
    public void testGetApplicantsAsList() {
//        List<Applicant> applicantList = Arrays.asList(
//                applicant1 = new Applicant("Alex","S","abc","a",managementSystem)
//        );
    }


    @Test
    public void testGetSystemMaintenanceStaffAsList() {
        List<SystemMaintenanceStaff> systemMaintenanceStaff = Arrays.asList(
                staff1 = new SystemMaintenanceStaff("Zack", "Q", "abc", managementSystem),
                staff2 = new SystemMaintenanceStaff("Xavier", "W", "abc", managementSystem)
        );
    }

    @Test
    public void testGetBlacklistedAsList() {
        List<User> blacklisted = Arrays.asList(
                staff1 = new SystemMaintenanceStaff("Back", "E", "abc", managementSystem),
                staff2 = new SystemMaintenanceStaff("Clown", "R", "abc", managementSystem)
        );
    }

    @Test
    public void testJobCategories() {
        assertEquals(managementSystem.getJobCategories(), Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE"));
    }

    @Test
    public void testGetSecurity() {
        assertEquals(managementSystem.getSecurity(), null);
    }

    @Test
    public void testGetUserByName() {
        assertEquals(managementSystem.getUserByName("null"), null);
    }

    @Test
    public void testGetEmployerByName() {
        assertEquals(managementSystem.getEmployerByName("null"), null);
    }

    @Test
    public void testGetApplicantByName() {
        assertEquals(managementSystem.getApplicantByName("null"), null);
    }

    @Test
    public void testGetSystemMaintenanceByName() {
        assertEquals(managementSystem.getSystemMaintenanceByName("null"), null);
    }


}