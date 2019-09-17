package tests;

import org.junit.Test;

import enumerators.PositionType;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.LocalStudent;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;



public class ApplicantTest {
  @Test
  public void dummy() {
    assertTrue(true);
  }
  
   Applicant a;
   PositionType avl;
   ManagementSystem mgsys;

	@Before
	public void setUp() throws Exception {
		a = new LocalStudent("Emmanuel", "Nwordu", "password", avl, mgsys);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		assertEquals(a.getName(), "Emmanuel Nwordu");
	}
  
}