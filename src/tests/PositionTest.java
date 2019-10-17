package tests;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.InterviewSlotClashException;
import exceptions.InvalidJobCategoryException;
import exceptions.UserNotFoundException;
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

import static org.junit.Assert.assertEquals;

public class PositionTest {
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
  
  // TODO: test for negative filtering
  // that filtered in applicants are there and that non filtered in applicants are not there
  
  @Test
  public void noFreeInterviewSlots() {
    List<InterviewSlot> freeSlots = position.getFreeInterviewSlots();
    assertEquals(7, freeSlots.size());
  }
  
  @Test
  public void getExistingApplicant() {
    Applicant a = null;
    try {
      a = position.getApplicant(applicant1, position.getAppliedApplicants());
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals("john smith", a.getName());
  }
  
  @Test(expected = UserNotFoundException.class)
  public void getNonExistingApplicant() throws UserNotFoundException {
    Applicant newApplicant = new LocalStudent("tim", "jones", "def", PositionType.PART_TIME, managementSystem);
    Applicant a = null;
    a = position.getApplicant(newApplicant, position.getAppliedApplicants());
  }
  
  @Test
  public void addValidCategoryException() {
    try {
      position.addApplicableJobCategory("ENGINEERING");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    assertEquals("ENGINEERING", position.getApplicableJobCategories().get(0));
  }
  
  @Test(expected = InvalidJobCategoryException.class)
  public void addInvalidCategoryException() throws InvalidJobCategoryException {
    position.addApplicableJobCategory("RESEARCH");
  }
  
  @Test
  public void filterTest() {
    try {
      applicant6.addJobPreference("LOGISTICS");
      applicant7.addJobPreference("hospitality");
      applicant8.addJobPreference("finance");
      applicant9.addJobPreference("retail");
      applicant10.addJobPreference("engineering");
      applicant11.addJobPreference("technology");
      applicant12.addJobPreference("trade");
      applicant12.addJobPreference("logistics");
      applicant13.addJobPreference("engineering");
      applicant13.addJobPreference("technology");
      applicant13.addJobPreference("trade");
      applicant13.addJobPreference("logistics");
      applicant13.addJobPreference("hospitality");
      applicant14.addJobPreference("logistics");
      applicant14.addJobPreference("retail");
      applicant14.addJobPreference("technology");
      applicant14.addJobPreference("finance");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
    
    position.addApplicantToAppliedApplicants(applicant6);
    position.addApplicantToAppliedApplicants(applicant7);
    position.addApplicantToAppliedApplicants(applicant8);
    position.addApplicantToAppliedApplicants(applicant9);
    position.addApplicantToAppliedApplicants(applicant10);
    position.addApplicantToAppliedApplicants(applicant11);
    position.addApplicantToAppliedApplicants(applicant12);
    position.addApplicantToAppliedApplicants(applicant13);
    position.addApplicantToAppliedApplicants(applicant14);
    
    position.filterApplicants();
    assertEquals(applicant13.getId(),
            position.getHighRankingApplicants().get(0).getId());
    assertEquals(applicant12.getId(),
            position.getHighRankingApplicants().get(1).getId());
    assertEquals(applicant14.getId(),
            position.getHighRankingApplicants().get(2).getId());
    assertEquals(applicant6.getId(),
            position.getHighRankingApplicants().get(3).getId());
    assertEquals(applicant10.getId(),
            position.getHighRankingApplicants().get(4).getId());
  }
  
  @Test
  public void setApplicantToPending() {
    try {
      position.getApplicant(applicant1, position.getAppliedApplicants()).setStatus(UserStatus.PENDING);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(UserStatus.PENDING, applicant1.getStatus());
  }
  
  @Test
  public void setApplicantPendingWhenAlreadyPending() {
    try {
      position.getApplicant(applicant1, position.getAppliedApplicants()).setStatus(UserStatus.PENDING);
      position.getApplicant(applicant1, position.getAppliedApplicants()).setStatus(UserStatus.PENDING);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(UserStatus.PENDING, applicant1.getStatus());
  }
  
  @Test
  public void addInterview() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
    assertEquals(LocalDate.of(2019, 10, 10),
            position.getInterviewSlots().get(0).getDate()
    );
  }
  
  @Test(expected = InterviewSlotClashException.class)
  public void throwExceptionIfInterviewSlotTaken() throws InterviewSlotClashException {
    position.addInterview(LocalDate.of(2019, 10, 10),
            LocalTime.of(12, 0, 0));
    position.addInterview(LocalDate.of(2019, 10, 10),
            LocalTime.of(12, 0, 0));
  }
}