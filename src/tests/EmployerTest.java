import enumerators.ApplicantStatus;
import enumerators.PositionType;
import exceptions.InvalidJobCategoryException;
import exceptions.PositionNotFoundException;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.system.ManagementSystem;
import model.user.employer.Employer;
import model.position.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployerTest {
  private ManagementSystem managementSystem;
  
  private Employer employer1;
  private Employer employer2;
  private Employer employer3;
  private Employer employer4;
  private Employer employer5;
  private Employer employer6;
  private Employer employer7;
  private Employer employer8;
  private Employer employer9;
  private Employer employer10;
  
  private Position position1;
  private Position position2;
  private Position position3;
  private Position position4;
  private Position position5;
  private Position position6;
  private Position position7;
  
  private List<Applicant> applicants;
  
  
  @Before
  public void setup() {
    managementSystem = new ManagementSystem();
    
    // Initialise all the employers
    employer1 = new Employer("Bunnings", "hammer", managementSystem);
    employer2 = new Employer("Vans", "kool", managementSystem);
    employer3 = new Employer("Platypus", "shoes", managementSystem);
    employer4 = new Employer("Apple", "steve", managementSystem);
    employer5 = new Employer("Samsung", "android", managementSystem);
    employer6 = new Employer("Google", "data", managementSystem);
    employer7 = new Employer("JB Hi-Fi", "tech", managementSystem);
    employer8 = new Employer("Converse", "style", managementSystem);
    employer9 = new Employer("Oracle", "java", managementSystem);
    employer10 = new Employer("Riot", "ritopls", managementSystem);
    
    // Initialise all the positions
    position1 = new Position(
            "Sales Rep",
            PositionType.PART_TIME,
            21.60,
            10,
            50,
            employer1,
            managementSystem);
    position2 = new Position(
            "Developer",
            PositionType.FULL_TIME,
            45.5,
            30,
            60,
            employer10,
            managementSystem);
    position3 = new Position(
            "Security Admin",
            PositionType.FULL_TIME,
            30,
            20,
            60,
            employer9,
            managementSystem);
    position4 = new Position(
            "Sales Rep",
            PositionType.PART_TIME,
            25.1,
            10,
            60,
            employer2,
            managementSystem);
    position5 = new Position(
            "Sales Rep",
            PositionType.PART_TIME,
            30,
            10,
            60,
            employer3,
            managementSystem);
    position6 = new Position(
            "Sales Rep",
            PositionType.PART_TIME,
            30,
            10,
            60,
            employer4,
            managementSystem);
    position7 = new Position(
            "Sales Rep",
            PositionType.PART_TIME,
            30,
            10,
            60,
            employer5,
            managementSystem);
    
    
    // Initialise all the applicants (students)
    applicants = new ArrayList<>(Arrays.asList(new LocalStudent(
                    "Xavier",
                    "Boughton",
                    "1234",
                    PositionType.PART_TIME,
                    managementSystem),
            new InternationalStudent(
                    "Stephen",
                    "Ristic",
                    "1234",
                    managementSystem),
            new LocalStudent(
                    "Jerry",
                    "Rigs",
                    "1234",
                    PositionType.FULL_TIME,
                    managementSystem),
            new LocalStudent(
                    "Marshall",
                    "Mathers",
                    "1234",
                    PositionType.FULL_TIME,
                    managementSystem)
    ));
    
    // Adding job categories for the positions
    try {
      position1.addApplicableJobCategory("retail");
      position2.addApplicableJobCategory("technology");
      position2.addApplicableJobCategory("engineering");
      position3.addApplicableJobCategory("technology");
      position4.addApplicableJobCategory("retail");
      position5.addApplicableJobCategory("retail");
      position6.addApplicableJobCategory("retail");
      position7.addApplicableJobCategory("retail");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    
    // Adding positions into the employers class
    employer1.addPosition(position1);
    employer2.addPosition(position4);
    employer3.addPosition(position5);
    employer4.addPosition(position6);
    employer5.addPosition(position7);
    employer9.addPosition(position3);
    employer10.addPosition(position2);
    
    // Adding preferences for the applicants
    try {
      applicants.get(0).addJobPreference("technology");
      applicants.get(0).addJobPreference("retail");
      applicants.get(1).addJobPreference("retail");
      applicants.get(2).addJobPreference("engineering");
      applicants.get(2).addJobPreference("technology");
      applicants.get(3).addJobPreference("retail");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    
    // Adding applicants to the positions
    position1.addApplicantToAppliedApplicants(applicants.get(0));
    position1.addApplicantToAppliedApplicants(applicants.get(3));
    position2.addApplicantToAppliedApplicants(applicants.get(1));
    position2.addApplicantToAppliedApplicants(applicants.get(2));
    position3.addApplicantToAppliedApplicants(applicants.get(1));
    position3.addApplicantToAppliedApplicants(applicants.get(2));
    position4.addApplicantToAppliedApplicants(applicants.get(0));
    position4.addApplicantToAppliedApplicants(applicants.get(3));
  }
  
  @Test
  public void checkForTruePasswordMatch() {
    assertTrue(employer1.verifyPassword("hammer"));
  }
  
  @Test
  public void checkForIncorrectPassword() {
    assertFalse(employer1.verifyPassword("plaster"));
  }
  
  @Test
  public void searchForMatchingApplicantTests() {
    Applicant findingApplicant = new LocalStudent("Xavier",
            "Boughton",
            "1234",
            PositionType.PART_TIME,
            managementSystem);
    
    boolean found = false;
    
    for (Applicant app : applicants) {
      if (app.equals(findingApplicant)) {
        found = true;
        break;
      }
    }
    
    assertTrue(found);
  }
  
  // TODO: implement this test with the updateApplicant method in the Employer class
  @Test
  public void updateApplicantTests() {
    assertTrue(false);
  }
  
  @Test
  public void notifyApplicantsTests() {
    String notification = "Hello, you are being considered for this job";
    List<Applicant> applicantsToEmployer1 = new ArrayList<>(Arrays.asList(applicants.get(0), applicants.get(3)));
    employer1.notifyApplicants(applicantsToEmployer1, notification);
    
    for (Applicant app : applicantsToEmployer1) {
      assertEquals(notification, app.getNotification(0));
    }
  }
  
  @Test
  public void notifyApplicantTests() {
    String notification = "Hello, you are being considered for this job";
    employer10.notifyApplicant(applicants.get(1), notification);
    
    assertEquals(notification, applicants.get(1).getNotification(0));
  }
  
  @Test
  public void offerJobTests() {
    List<Applicant> offeredApplicants = null;
    
    try {
      employer1.offerJob(applicants.get(0), employer1.getPositionByTitle(position1.getPositionTitle()));
      employer1.offerJob(applicants.get(3), employer1.getPositionByTitle(position1.getPositionTitle()));
      offeredApplicants = employer1.getPositionByTitle(position1.getPositionTitle()).getApplicantsJobOfferedTo();
    } catch (PositionNotFoundException e) {
      e.printStackTrace();
    }
    
    assertEquals(ApplicantStatus.PENDING, applicants.get(0).getStatus());
    assertEquals(applicants.get(0), offeredApplicants.get(0));
    assertEquals(applicants.get(3), offeredApplicants.get(1));
  }
  
  @Test
  public void handleUnsuccessfulApplicantsTests() {
    List<Applicant> unsuccessfulApplicants = null;
    
    employer6.addPosition("Dev", PositionType.FULL_TIME, 35.5, 20, 30);
    
    try {
      employer6.getPositionByTitle("Dev").addApplicantToAppliedApplicants(applicants.get(1));
      employer6.getPositionByTitle("Dev").addApplicantToAppliedApplicants(applicants.get(2));
      
      // This was to test it would fail.
      //employer6.offerJob(applicants.get(1), employer6.getPositionByTitle("Dev"));
      
      employer6.handleUnsuccessfulApplicants(employer6.getPositionByTitle("Dev"));
      unsuccessfulApplicants = employer6.getPositionByTitle("Dev").getUnsuccessfullApplicants();
    } catch (PositionNotFoundException e) {
      e.printStackTrace();
    }
    
    assertEquals(applicants.get(1), unsuccessfulApplicants.get(0));
    assertEquals(applicants.get(2), unsuccessfulApplicants.get(1));
  }
  
  @Test
  public void lodgeComplaintOnApplicantTests() {
    String complaint = "Unprofessional";
    String complaint2 = "Did not show up to interview";
    
    employer3.lodgeComplaint(applicants.get(0), complaint);
    employer4.lodgeComplaint(applicants.get(1), complaint2);
    
    List<String> complaintsForApplicant1 = applicants.get(0).getComplaints();
    List<String> complaintsForApplicant2 = applicants.get(1).getComplaints();
    
    assertEquals(complaint, complaintsForApplicant1.get(0));
    assertEquals(complaint2, complaintsForApplicant2.get(0));
  }
  
  @Test
  public void equalsTests() {
    assertTrue(employer1.equals(employer1));
  }
  
  
}