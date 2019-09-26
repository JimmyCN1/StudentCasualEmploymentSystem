package tests;

import org.junit.Test;

import enumerators.PositionType;
import enumerators.UserStatus;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import exceptions.*;
import model.position.Position;
import model.user.applicant.utilities.Notification;
import model.user.employer.Employer;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicantTest {

	Applicant a;
	PositionType avl;
	ManagementSystem mgsys;
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
	public void setUp() throws Exception {
		a = new LocalStudent("Emmanuel", "Nwordu", "password", avl, mgsys);

		managementSystem = new ManagementSystem();

		// Initialize all the employers
		//
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

		// Initialize all the positions
		position1 = new Position("Sales Rep", PositionType.PART_TIME, 21.60, 10, 50, employer1, managementSystem);
		position2 = new Position("Developer", PositionType.FULL_TIME, 45.5, 30, 60, employer10, managementSystem);
		position3 = new Position("Security Admin", PositionType.FULL_TIME, 30, 20, 60, employer9, managementSystem);
		position4 = new Position("Sales Rep", PositionType.PART_TIME, 25.1, 10, 60, employer2, managementSystem);
		position5 = new Position("Sales Rep", PositionType.PART_TIME, 30, 10, 60, employer3, managementSystem);
		position6 = new Position("Sales Rep", PositionType.PART_TIME, 30, 10, 60, employer4, managementSystem);
		position7 = new Position("Sales Rep", PositionType.PART_TIME, 30, 10, 60, employer5, managementSystem);

		// Initialize all the applicants (students)
		// Also tests that Applicant object and children are initialized
		applicants = new ArrayList<>(
				Arrays.asList(new LocalStudent("Xavier", "Boughton", "1234", PositionType.PART_TIME, managementSystem),
						new InternationalStudent("Stephen", "Ristic", "1234", managementSystem),
						new LocalStudent("Jerry", "Rigs", "1234", PositionType.FULL_TIME, managementSystem),
						new LocalStudent("Marshall", "Mathers", "1234", PositionType.FULL_TIME, managementSystem)));

		managementSystem.registerUser(applicants.get(0));
		managementSystem.registerUser(applicants.get(1));
		managementSystem.registerUser(applicants.get(2));
		managementSystem.registerUser(applicants.get(3));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldAccessJobPreferennce() throws InvalidJobCategoryException {
		applicants.get(0).addJobPreference("finance");

		List<String> jobCategory = applicants.get(0).getJobPreferences();

		assertEquals(jobCategory.get(0), "FINANCE");
	}

	@Test
	public void shouldAccessAvailability()
			throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
		applicants.get(2).addAvailability("part_time");

		List<PositionType> availability = applicants.get(2).getAvailabilities();

		assertEquals(availability.get(1), PositionType.PART_TIME);
	}

	@Test
	public void shouldThrowInternationalStudentAvailabilityException()
			throws InternationalStudentAvailabilityException, PositionTypeNotFoundException {
		try {
			applicants.get(1).addAvailability("full_time");

		} catch (InternationalStudentAvailabilityException e) {
			e.printStackTrace();
		}
	}

	// this tests a precondition as applicants are instantiated with a
	// null jobOffer property
	@Test
	public void shouldReceiveOffer() throws UserBlacklistedException, PositionNotFoundException {
		employer1.offerJob(applicants.get(0), employer1.getPositionByTitle(position1.getTitle()));

		assertNotNull(applicants.get(0).jobOfferToString());

	}

	@Test
	public void shouldAcceptOffer() throws UserBlacklistedException, UserNotFoundException, NoJobOfferException {
		try {
			employer1.offerJob(applicants.get(0),  employer1.getPositionByTitle(position1.getTitle()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			applicants.get(0).acceptOffer();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(UserStatus.EMPLOYED, applicants.get(0).getStatus());
	}

}