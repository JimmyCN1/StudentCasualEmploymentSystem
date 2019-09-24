package app;

import model.position.Position;
import model.system.ManagementSystem;
import model.user.employer.Employer;

public class PositionApp extends App {
  private Position position;
  private Employer currentUser;
  
  
  public PositionApp(Position position, Employer currentUser, ManagementSystem managementSystem) {
    super(managementSystem);
    this.position = position;
    this.currentUser = currentUser;
  }
  
  public Position getPosition() {
    return position;
  }
  
  public void setPosition(Position position) {
    this.position = position;
  }
  
  public void displayMainMenu() {
  }
}
