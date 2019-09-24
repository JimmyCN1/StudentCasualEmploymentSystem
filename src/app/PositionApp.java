package app;

import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
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
    //TODO: updating candidate based on interview and reference check
    boolean goBack = false;
    int response;
    while (!goBack) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. Search For Matching Candidates\n" +
                "2. Shortlist Applicants\n" +
                "3. Rank Applicants\n" +
                "4. Mail Applicants\n" +
                "5. Set Interview Times\n" +
                "6. Offer Job\n\n" +
                "0. Go Back\n\n");
        response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            searchForMatchingCandidates();
            break;
          case (2):
            shortlistApplicants();
            break;
          case (3):
            rankApplicants();
            break;
          case (4):
            mailApplicants();
            break;
          case (5):
            setInterviewTimes();
            break;
          case (6):
            offerJobToApplicant();
            break;
          case (0):
            goBack = true;
            break;
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      }
    }
  }
  
  private void searchForMatchingCandidates() {
  
  }
  
  private void shortlistApplicants() {
    boolean validResponse = false;
    Applicant applicant;
    int response = 0;
    while (!validResponse) {
      try {
        System.out.println("Which applicant would you like to shortlist?\n");
        System.out.println(position.listToStringAsOrderedList(position.getAppliedApplicants()));
        response = scanner.nextInt();
        scanner.nextLine();
        applicant = position.getAppliedApplicants().get(response - 1);
        position.addApplicantToShortlist(applicant);
        System.out.printf("%s was successfully shortlisted\n\n", applicant.getName());
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      } catch (ArrayIndexOutOfBoundsException e) {
        printInputMismatchMessage();
      }
    }
  }
  
  private void rankApplicants() {
    System.out.println("Ranking applicants...\n");
    position.filterApplicants();
    System.out.println("The applicants have been automatically ranked by the system..\n");
  }
  
  private void mailApplicants() {
  
  }
  
  private void setInterviewTimes() {
  }
  
  
  private void offerJobToApplicant() {
    boolean validResponse = false;
    Applicant applicant;
    int response = 0;
    while (!validResponse) {
      try {
        System.out.println("Which applicant would you like to offer a job to?\n");
        System.out.println(position.listToStringAsOrderedList(position.getHighRankingApplicants()));
        response = scanner.nextInt();
        scanner.nextLine();
        applicant = position.getHighRankingApplicants().get(response - 1);
        position.addApplicantToJobOffered(applicant);
        System.out.printf("Job successfully offered to %s", applicant.getName());
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      } catch (ArrayIndexOutOfBoundsException e) {
        printInputMismatchMessage();
      }
    }
  }
}
