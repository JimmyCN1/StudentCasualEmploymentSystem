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
        employer1 = new Employer("John", "abc", managementSystem);
        employer2 = new Employer("Leo", "abc", managementSystem);
        employer3 = new Employer("Ray", "C", managementSystem);

    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    //1. Test equal
    public void testRegisterEmployer() {
        Map<String, Employer> employers = new HashMap<>();
        employers.put("John", employer1);
        employers.put("Leo", employer2);
        employers.put("Ray", employer3);

        Map<String, Employer> registeredEmployers = new HashMap<>();
        registeredEmployers.put("Leo", employer2);
        registeredEmployers.put("Ray", employer3);
        registeredEmployers.put("John", employer1);


        assertThat(employers, is(registeredEmployers));

    }

    @Test
    public void testNumOfEmployer() {
        Map<String, Employer> employers = new HashMap<>();
        employers.put("John", employer1);
        employers.put("Leo", employer2);
        employers.put("Ray", employer3);

        //2. Test number of registered employers
        assertThat(employers.size(), is(3));
    }

    @Test
    //3. test registered employer
    public void testRegisteredEmployer() throws EmployerAlreadyRegisteredException {
        employer4 = new Employer(
                "Leo", "abc", managementSystem
        );
        assertEquals(managementSystem.getEmployerByName("LEO"), employer4);
    }

    @Test
    //4. Test JobCategories
    public void testJobCategories() {
        assertEquals(managementSystem.getJobCategories(), Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE"));
    }
}

