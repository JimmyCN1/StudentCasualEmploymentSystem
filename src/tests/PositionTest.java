import driver.ManagementSystem;
import interfaces.applicant.Applicant;
import interfaces.applicant.LocalStudent;
import job.InterviewSlot;
import job.Position;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class PositionTest {
  ManagementSystem managementSystem;
  Position position;
  Applicant applicant;
  
  @Before
  public void setUp() {
    managementSystem = new ManagementSystem();
    position = new Position("developer",
            "full-time",
            33.5,
            40,
            60, managementSystem);
    applicant = new LocalStudent("john",
            "smith",
            "password",
            managementSystem);
    position.addApplicantToAppliedApplicants(applicant);
  }
  
  
  @Test
  public void setApplicantToPending() {
    position.getApplicantById(applicant, position.getAppliedApplicants()).setPending();
    assert (applicant.getStatus().equals("pending"));
  }
  
  @Test
  public void setApplicantPendingWhenAlreadyPending() {
    position.getApplicantById(applicant, position.getAppliedApplicants()).setPending();
    position.getApplicantById(applicant, position.getAppliedApplicants()).setPending();
    assert (applicant.getStatus().equals("pending"));
  }
  
  @Test
  public void addFirstInterview() {
    position.addInterview(LocalDate.of(2019, 10, 10),
            LocalTime.of(12, 0, 0),
            applicant);
    assert (position.getInterviewSlots().size() == 1);
  }

//  @Test
//  public void
  
}