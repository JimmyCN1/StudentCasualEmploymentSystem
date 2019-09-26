package tests;

import enumerators.PositionType;
import exceptions.InvalidJobCategoryException;
import model.position.InterviewSlot;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.employer.Employer;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PositionTest2 {
  private ManagementSystem managementSystem;
  private Employer employer1;
  private Position position;
  private Applicant applicant1;
  private Applicant applicant2;
  private Applicant applicant3;
  private Applicant applicant6;
  private Applicant applicant7;
  private Applicant applicant8;
  private Applicant applicant9;
  private Applicant applicant10;
  private Applicant applicant11;
  private Applicant applicant12;
  private Applicant applicant13;
  private Applicant applicant14;
  private InterviewSlot interviewSlot1;
  private InterviewSlot interviewSlot2;
  private InterviewSlot interviewSlot3;
  private InterviewSlot interviewSlot4;
  private InterviewSlot interviewSlot5;
  private List<InterviewSlot> interviewSlots = new ArrayList<>();
  
  @Before
  public void setUp() {
    managementSystem = new ManagementSystem();
    employer1 = new Employer("employer1", "abc", managementSystem);
    position = new Position("developer",
            PositionType.FULL_TIME,
            33.5,
            40,
            60,
            employer1,
            managementSystem
    );
    try {
      position.addApplicableJobCategory("engineering");
      position.addApplicableJobCategory("technology");
      position.addApplicableJobCategory("trade");
      position.addApplicableJobCategory("logistics");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    applicant1 = new LocalStudent("john",
            "smith",
            "password",
            PositionType.INTERNSHIP,
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
    applicant6 = new LocalStudent(
            "Tank",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant7 = new LocalStudent(
            "Zack",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant8 = new LocalStudent(
            "Jeb",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant9 = new LocalStudent(
            "Scott",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant10 = new LocalStudent(
            "Jeff",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant11 = new LocalStudent(
            "Moses",
            "Jeffreis",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant12 = new LocalStudent(
            "Scott",
            "Jilly",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant13 = new LocalStudent(
            "Jeff",
            "Jameson",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    applicant14 = new LocalStudent(
            "Moses",
            "Hardy",
            "abc",
            PositionType.FULL_TIME,
            managementSystem
    );
    
    try {
      applicant1.addJobPreference("engineering");
      applicant2.addJobPreference("technology");
      applicant3.addJobPreference("trade");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    
    position.addApplicantToAppliedApplicants(applicant1);
    position.addApplicantToAppliedApplicants(applicant2);
    position.addApplicantToAppliedApplicants(applicant3);
    
    interviewSlot1 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(12, 00), applicant1, position, employer1);
    interviewSlot2 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(13, 00), applicant2, position, employer1);
    interviewSlot3 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(14, 00), applicant3, position, employer1);
    interviewSlot4 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(11, 00), position, employer1);
    interviewSlot5 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(15, 00), position, employer1);
//    try {
//      position.addInterview(interviewSlot1.getDate(), interviewSlot1.getTime(), applicant1);
//      position.addInterview(interviewSlot2.getDate(), interviewSlot2.getTime(), applicant2);
//      position.addInterview(interviewSlot3.getDate(), interviewSlot3.getTime(), applicant3);
//    } catch (InterviewSlotClashException e) {
//      e.printStackTrace();
//    } catch (ApplicantAlreadyBookedException e) {
//      e.printStackTrace();
//    }
  }
  
  @Test
  public void fluff() {
    assertTrue(true);
  }
}