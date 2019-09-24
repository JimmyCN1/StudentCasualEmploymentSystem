package app;

import model.position.Position;
import model.system.ManagementSystem;
import model.user.employer.Employer;

import java.util.InputMismatchException;

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
    //TODO: searching for suitable candidates
    //TODO: shortlisting and ranking candidates
    //TODO: updating candidate based on interview and reference check
    //TODO: creation of job offers
    boolean isLoggedIn = true;
    int response;
    while (isLoggedIn) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Search for matching candidates\n" +
                "2. Shortlist Applicants\n" +
                "3. Rank Applicants\n" +
                "4. Mail Applicants\n" +
                "5. Set Interview Times\n" +
                "6. Mail Applicants\n" +
                "7. Offer Job\n\n" +
                "0. Go Back\n\n");
        response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
//            searchForMatchingCandidates();
            break;
          case (2):
//            shortlistApplicants();
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
  }
}
