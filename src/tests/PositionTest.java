import driver.ManagementSystem;
import exceptions.ScheduleMultipleInterviewsWithSameApplicantException;
import exceptions.TakenInterviewSlotException;
import model.applicant.Applicant;
import model.applicant.InternationalStudent;
import model.applicant.LocalStudent;
import model.job.Position;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PositionTest {
  ManagementSystem managementSystem;
  Position position;
  Applicant applicant1;
  Applicant applicant2;
  Applicant applicant3;
  
  @Before
  public void setUp() {
    managementSystem = new ManagementSystem();
    position = new Position("developer",
            "full-time",
            33.5,
            40,
            60, managementSystem
    );
    applicant1 = new LocalStudent("john",
            "smith",
            "password",
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
    position.addApplicantToAppliedApplicants(applicant1);
    position.addApplicantToAppliedApplicants(applicant2);
  }
  
  
  @Test
  public void setApplicantToPending() {
    position.getApplicantById(applicant1, position.getAppliedApplicants()).setPending();
    assertEquals("pending", applicant1.getStatus());
  }
  
  @Test
  public void setApplicantPendingWhenAlreadyPending() {
    position.getApplicantById(applicant1, position.getAppliedApplicants()).setPending();
    position.getApplicantById(applicant1, position.getAppliedApplicants()).setPending();
    assertEquals("pending", applicant1.getStatus());
  }
  
  @Test
  public void addFirstInterview() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
    assertEquals(1, position.getInterviewSlots().size());
    assertEquals(LocalDate.of(2019, 10, 10),
            position.getInterviewSlots().get(0).getDate()
    );
  }
  
  @Test
  public void addSecondInterviewAfterFirst() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      position.addInterview(LocalDate.of(2019, 10, 11),
              LocalTime.of(12, 0, 0),
              applicant2);
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
    assertEquals(2, position.getInterviewSlots().size());
    assertEquals(LocalDate.of(2019, 10, 11),
            position.getInterviewSlots().get(1).getDate()
    );
  }
  
  @Test
  public void addSecondInterviewBeforeFirst() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      position.addInterview(LocalDate.of(2019, 10, 9),
              LocalTime.of(12, 0, 0),
              applicant2);
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
    assertEquals(2, position.getInterviewSlots().size());
    assertEquals(LocalDate.of(2019, 10, 9),
            position.getInterviewSlots().get(0).getDate()
    );
  }
  
  @Test
  public void addThirdInterviewBetweenFirstAndSecond() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      position.addInterview(LocalDate.of(2019, 10, 12),
              LocalTime.of(12, 0, 0),
              applicant2);
      position.addInterview(LocalDate.of(2019, 10, 11),
              LocalTime.of(12, 0, 0),
              applicant3);
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
    assertEquals(3, position.getInterviewSlots().size());
    assertEquals(LocalDate.of(2019, 10, 11),
            position.getInterviewSlots().get(1).getDate()
    );
  }
  
  @Test
  public void throwExceptionIfInterviewSlotTaken() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant2);
      fail("TakenInterviewSlotException not caught");
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void throwExceptionIfSchedulingSameApplicantTwice() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant1);
      fail("ScheduleMultipleInterviewsWithSameApplicantException not caught");
    } catch (TakenInterviewSlotException e) {
      e.printStackTrace();
    } catch (ScheduleMultipleInterviewsWithSameApplicantException e) {
      e.printStackTrace();
    }
  }
}