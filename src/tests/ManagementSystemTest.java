import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import model.position.Position;
import model.user.User;
import model.system.ManagementSystem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

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

    @Before
    public void setup() {
        managementSystem = new ManagementSystem();

        //Initialise all the applicants
        employer1 = new Employer("Bunnings", "hammer", managementSystem);
        employer2 = new Employer("Vans", "kool", managementSystem);
        employer3 = new Employer("Platypus", "shoes", managementSystem);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    //Test equal
    public void testRegisterEmployer() {
        Map<String, Employer> employers = new HashMap<>();
        employers.put("Bunnings", employer1);
        employers.put("Vans", employer2);
        employers.put("Platypus", employer3);

        Map<String, Employer> registeredEmployers = new HashMap<>();
        registeredEmployers.put("Vans", employer2);
        registeredEmployers.put("Platypus", employer3);
        registeredEmployers.put("Bunnings", employer1);


        assertThat(employers, is(registeredEmployers));

    }

    @Test
    //Test number of registered employers
    public void testNumOfEmployer() {
        Map<String, Employer> employers = new HashMap<>();
        employers.put("Bunnings", employer1);
        employers.put("Vans", employer2);
        employers.put("Platypus", employer3);

        assertThat(employers.size(), is(3));
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
}
