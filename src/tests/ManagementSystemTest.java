package tests;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import model.position.Position;
import model.user.User;
import model.system.ManagementSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;


public class ManagementSystemTest {

    private ManagementSystem managementSystem;
    private Applicant applicant1;
    private Applicant applicant2;
    private Applicant applicant3;
    private Employer employer1;
    private Employer employer2;
    private Employer employer3;
    private Employer employer4;
    private Employer employer5;
    private SystemMaintenanceStaff staff1;
    private SystemMaintenanceStaff staff2;


    @Before
    public void setup() {
        managementSystem = new ManagementSystem();

        //Initialise all the applicants
        employer1 = new Employer("Bunnings", "hammer", managementSystem);
        employer2 = new Employer("Vans", "kool", managementSystem);
        employer3 = new Employer("Platypus", "shoes", managementSystem);
        staff1 = new SystemMaintenanceStaff("Jack", "Daniel", "xyz", managementSystem);
        staff2 = new SystemMaintenanceStaff("Lemon", "Cherry", "xyz", managementSystem);
    }

    @After
    public void tearDown() throws Exception {
    }


  

    @Test
    //test add employer to blacklist
    public void testBlacklistUsers() throws UserBlacklistedException {
        employer4 = new Employer(
                "Bunnings", "hammer", managementSystem
        );
        managementSystem.addUserToBlacklist(employer4);
        assertTrue(managementSystem.getBlacklistedAsList().contains(employer4));
    }

    @Test
    //Test JobCategories
    public void testJobCategories() {
        assertEquals(managementSystem.getJobCategories(), Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE"));
    }

    @Test
    public void addNewJobCategoryTest() {
        try {
            staff1.addNewJobCategory("INSURANCE AGENT");
        } catch (JobCategoryAlreadyExistsException e) {
            e.printStackTrace();
        }
        String jobCategory = "INSURANCE AGENT";
        assertEquals(managementSystem.getJobCategories(), Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE", "INSURANCE AGENT"));
    }

}

