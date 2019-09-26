package tests;


import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.ApplicantNotFoundException;
import exceptions.InvalidUserStatusException;
import exceptions.JobCategoryAlreadyExistsException;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.staff.SystemMaintenanceStaff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
        staff6 = new SystemMaintenanceStaff("Steve", "Jobs", "Apple", managementSystem);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void verifyMatchingPassword() {
        assertTrue(staff1.verifyPassword("jvs1"));
        assertTrue(staff2.verifyPassword("aps2"));
    }

    @Test
    public void verifyIncorrectPassword() {
        assertFalse(staff1.verifyPassword("aps2"));
        assertFalse(staff2.verifyPassword("jvs1"));
    }

    @Test
    public void getApplicantRecordTest() throws NullPointerException {
        applicants = new ArrayList<>(Arrays.asList(
                new LocalStudent(
                        "Bruce",
                        "Wayne",
                        "imBatman",
                        PositionType.PART_TIME,
                        managementSystem)));
        this.managementSystem.registerApplicant(applicants.get(0));

        Applicant applicantActual = applicants.get(0);
        Applicant applicantReturn = null;
        try {
            applicantReturn = staff5.getApplicantRecords(1);
        } catch (ApplicantNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(applicantActual.getId(), applicantReturn.getId());
    }

    @Test
    public void getNonExistingApplicantRecordTest() {
        Applicant applicants = new InternationalStudent("Betty", "Cooper", "def", this.managementSystem);
        try {
            Applicant applicant = staff6.getApplicantRecords(5);
        } catch (ApplicantNotFoundException e) {
            e.printStackTrace();
        }
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

        staff4.blacklistUser(applicants.get(0));
        assertEquals(this.applicants.get(0).getStatus(), UserStatus.BLACKLISTED);
    }

    @Test
    public void addNewJobCategoryTest() throws JobCategoryAlreadyExistsException {
        String jobCategory = "LAW";
        staff5.addNewJobCategory(jobCategory);
        assertTrue(managementSystem.getJobCategories().contains(jobCategory));

    }

    @Test
    public void addAlreadyExistingJobCategoryTest() {
        try {
            String jobCategory = "FINANCE";
            staff6.addNewJobCategory(jobCategory);
            assertTrue(managementSystem.getJobCategories().contains(jobCategory));
        }catch (JobCategoryAlreadyExistsException e){
            e.printStackTrace();
        }

    }
}
