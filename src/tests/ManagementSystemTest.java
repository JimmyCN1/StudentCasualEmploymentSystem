package tests;

import model.system.ManagementSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ManagementSystemTest {
  private ManagementSystem managementSystem;
  
  @Before
  public void setup() {
    managementSystem = new ManagementSystem();
  }
  
  @Test
  public void dummy() {
    assertTrue(true);
  }
  
}