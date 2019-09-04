import enumerators.PositionType;
import model.applicant.Applicant;
import model.applicant.InternationalStudent;
import model.applicant.LocalStudent;
import model.system.ManagementSystem;
import model.employer.Employer;
import model.position.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployerTest {
  private Employer employer1;
  private Employer employer2;
  private Position position1;
  private Position position2;
  private Applicant applicant1;
  private Applicant applicant2;
  private Applicant applicant3;
  private ManagementSystem managementSystem = new ManagementSystem();
  
  
  @Before
  public void setup() {
    employer1 = new Employer("employer1", "abc", managementSystem);
    employer2 = new Employer("employer2", "cba", managementSystem);
    position1 = new Position(
            "developer",
            PositionType.FULL_TIME,
            33.5,
            40,
            60,
            employer1,
            managementSystem
    );
    position2 = new Position(
            "engineer",
            PositionType.INTERNSHIP,
            25.0,
            20,
            40,
            employer1,
            managementSystem
    );
    applicant1 = new LocalStudent("john",
            "smith",
            "password",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant2 = new InternationalStudent(
            "Jack",
            "Nyguen",
            "aaa",
            managementSystem
    );
    applicant3 = new InternationalStudent(
            "John",
            "Jeffreis",
            "abc",
            managementSystem
    );
    position1.addApplicantToAppliedApplicants(applicant1);
    position1.addApplicantToAppliedApplicants(applicant2);
    employer1.addPosition(position1);
    employer1.addPosition(position2);
    position2.addApplicantToAppliedApplicants(applicant3);
    employer2.addPosition(position2);
  }
  
  @Test
  public void checkForTruePasswordMatch() {
    assertTrue(employer1.verifyPassword("abc"));
  }
  
  @Test
  public void checkForIncorrectPassword() {
    assertFalse(employer1.verifyPassword("ab"));
  }
  
  @Test
  public void searchForMatchingApplicantTests() {
    assertTrue(false);
  }
  
  @Test
  public void updateApplicantTests() {
    assertTrue(false);
  }
  
  @Test
  public void notifyApplicantsTests() {
    assertTrue(false);
  }
  
  @Test
  public void offerJobTests() {
    assertTrue(false);
  }
  
  @Test
  public void handleUnsuccessfulApplicantsTests() {
    assertTrue(false);
  }
  
  @Test
  public void lodgeComplaintOnApplicantTests() {
    assertTrue(false);
  }
  
  @Test
  public void equalsTests() {
    assertTrue(employer1.equals(employer1));
  }
  
  
}