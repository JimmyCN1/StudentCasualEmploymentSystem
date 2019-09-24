package app;

import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.utilities.License;
import model.user.applicant.utilities.PastJob;
import model.user.applicant.utilities.Qualification;
import model.user.applicant.utilities.Reference;
import model.user.employer.Employer;

import java.util.InputMismatchException;
import java.util.List;

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
                "6. Update Candidate's Applicantion\n" +
                "7. Offer Job\n\n" +
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
            updateCandidatesApplication();
          case (7):
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
    System.out.println("These are the current applicants that have applied for this position..");
    for (int i = 0; i < position.getAppliedApplicants().size(); i++) {
      System.out.printf("Applicant %d\n\n%s\n\n", i + 1, position.getAppliedApplicants().get(i).toString());
    }
    boolean goBack = false;
    int response = 0;
    while (!goBack) {
      try {
        System.out.println("Select which candidate you would like to see in further detail..\n\n" +
                "Press 0 to go back..");
        response = scanner.nextInt();
        scanner.nextLine();
        if (response == 0) {
          goBack = true;
        } else {
          zoomInOnStudent(position.getAppliedApplicants().get(response - 1));
        }
      } catch (InputMismatchException e) {
        printInputMismatchMessage();
      } catch (ArrayIndexOutOfBoundsException e) {
        printInputMismatchMessage();
      }
    }
  }
  
  private void zoomInOnStudent(Applicant applicant) {
    boolean goBack = false;
    int response;
    while (!goBack) {
      try {
        System.out.printf("What would you like to do?\n\n" +
                "1. View Applicant's Licences\n" +
                "2. View Applicant's Past Jobs\n" +
                "3. View Applicant's Qualifications\n" +
                "4. View Applicant's References\n\n" +
                "0. Go Back\n\n");
        response = scanner.nextInt();
        scanner.nextLine();
        switch (response) {
          case (1):
            showApplicantsPastLicenses(applicant);
            break;
          case (2):
            showApplicantsPastJobs(applicant);
            break;
          case (3):
            showApplicantsQualifications(applicant);
            break;
          case (4):
            showApplicantsReferences(applicant);
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
  
  private void showApplicantsPastLicenses(Applicant applicant) {
    List<License> licenses = applicant.getLicenses();
    for (License l : licenses) {
      System.out.println(l.toString());
    }
  }
  
  private void showApplicantsPastJobs(Applicant applicant) {
    List<PastJob> pastJobs = applicant.getPastJobs();
    for (PastJob pj : pastJobs) {
      System.out.println(pj.toString());
    }
  }
  
  private void showApplicantsQualifications(Applicant applicant) {
    List<Qualification> qualifications = applicant.getQualifications();
    for (Qualification q : qualifications) {
      System.out.println(q.toString());
    }
  }
  
  private void showApplicantsReferences(Applicant applicant) {
    List<Reference> references = applicant.getReferences();
    for (Reference r : references) {
      System.out.println(r.toString());
    }
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
  
  private void updateCandidatesApplication() {
  
  }
  
  
  private void offerJobToApplicant() {
    boolean validResponse = false;
    Applicant applicant;
    int response;
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
