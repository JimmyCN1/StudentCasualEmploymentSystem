import enumerators.UserStatus;
import enumerators.PositionType;
import exceptions.UserNotFoundException;
import exceptions.InterviewSlotClashException;
import exceptions.InvalidJobCategoryException;
import model.system.ManagementSystem;
import exceptions.ApplicantAlreadyBookedException;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.employer.Employer;
import model.position.InterviewSlot;
import model.position.Position;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
  private List<InterviewSlot> interviewSlots;
  
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
            LocalTime.of(12, 00), applicant1);
    interviewSlot2 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(13, 00), applicant2);
    interviewSlot3 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(14, 00), applicant3);
    interviewSlot4 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(11, 00));
    interviewSlot5 = new InterviewSlot(LocalDate.of(2019, 11, 1),
            LocalTime.of(15, 00));
    try {
      position.addInterview(interviewSlot1.getDate(), interviewSlot1.getTime(), applicant1);
      position.addInterview(interviewSlot2.getDate(), interviewSlot2.getTime(), applicant2);
      position.addInterview(interviewSlot3.getDate(), interviewSlot3.getTime(), applicant3);
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    } catch (ApplicantAlreadyBookedException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void getTheTwoFreeInterviewSlots() {
    try {
      position.addInterview(interviewSlot4.getDate(), interviewSlot4.getTime());
      position.addInterview(interviewSlot5.getDate(), interviewSlot5.getTime());
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
    List<InterviewSlot> freeSlots = position.getFreeInterviewSlots();
    List<InterviewSlot> slots = new ArrayList<>();
    slots.add(interviewSlot4);
    slots.add(interviewSlot5);
    assertEquals(2, freeSlots.size());
    assertEquals(slots.get(0).getDate(), freeSlots.get(0).getDate());
    assertEquals(slots.get(0).getTime(), freeSlots.get(0).getTime());
    assertEquals(slots.get(1).getDate(), freeSlots.get(1).getDate());
    assertEquals(slots.get(1).getTime(), freeSlots.get(1).getTime());
  }
  
  @Test
  public void noFreeInterviewSlots() {
    List<InterviewSlot> freeSlots = position.getFreeInterviewSlots();
    assertEquals(0, freeSlots.size());
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
  
  @Test
  public void getNonExistingApplicant() {
    Applicant newApplicant = new LocalStudent("tim", "jones", "def", PositionType.PART_TIME, managementSystem);
    Applicant a = null;
    try {
      a = position.getApplicant(newApplicant, position.getAppliedApplicants());
      fail("UserNotFoundException not caught");
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
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
  
  @Test
  public void addInvalidCategoryException() {
    try {
      position.addApplicableJobCategory("RESEARCH");
      fail("InvalidJobCategoryException not caught");
    } catch (InvalidJobCategoryException e) {
      e.printStackTrace();
    }
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
  
  @Test
  public void addSecondInterviewAfterFirst() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
      position.addInterview(LocalDate.of(2019, 10, 11),
              LocalTime.of(12, 0, 0));
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
    assertEquals(LocalDate.of(2019, 10, 11),
            position.getInterviewSlots().get(1).getDate()
    );
  }
  
  @Test
  public void addSecondInterviewBeforeFirst() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
      position.addInterview(LocalDate.of(2019, 10, 9),
              LocalTime.of(12, 0, 0));
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
    assertEquals(LocalDate.of(2019, 10, 9),
            position.getInterviewSlots().get(0).getDate()
    );
  }
  
  @Test
  public void addThirdInterviewBetweenFirstAndSecond() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
      position.addInterview(LocalDate.of(2019, 10, 12),
              LocalTime.of(12, 0, 0));
      position.addInterview(LocalDate.of(2019, 10, 11),
              LocalTime.of(12, 0, 0));
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
    assertEquals(LocalDate.of(2019, 10, 11),
            position.getInterviewSlots().get(1).getDate()
    );
  }
  
  @Test
  public void throwExceptionIfInterviewSlotTaken() {
    try {
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0));
      fail("InterviewSlotClashException not caught");
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void throwExceptionIfSchedulingSameApplicantTwice() {
    Applicant applicant4 = new InternationalStudent(
            "John",
            "Jeffreis",
            "abc",
            managementSystem
    );
    try {
      position.addInterview(LocalDate.of(2019, 10, 11),
              LocalTime.of(13, 0, 0),
              applicant4);
      position.addInterview(LocalDate.of(2019, 10, 10),
              LocalTime.of(12, 0, 0),
              applicant4);
      fail("ApplicantAlreadyBookedException not caught");
    } catch (InterviewSlotClashException e) {
      e.printStackTrace();
    } catch (ApplicantAlreadyBookedException e) {
      e.printStackTrace();
    }
  }
}