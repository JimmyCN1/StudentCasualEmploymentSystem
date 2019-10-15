package app;

import exceptions.UserBlacklistedException;
import model.position.InterviewSlot;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.applicant.Applicant;
import model.user.applicant.utilities.*;
import model.user.employer.Employer;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class PositionApp extends AbstractApp {
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
        boolean goBack = false;
        int response;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Search For Matching Candidates\n" +
                        "2. Shortlist Applicants\n" +
                        "3. View Shortlisted Applicants\n" +
                        "4. Rank Applicants\n" +
                        "5. Invite Applicant for Interview\n" +
                        "6. Update Candidate's Application\n" +
                        "7. Offer Job\n" +
                        "8. Mail Applicants\n" +
                        "9. View Position details\n" +
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
                        viewShortlistedApplicants();
                        break;
                    case (4):
                        rankApplicants();
                        break;
                    case (5):
                        inviteApplicantForInterview();
                        break;
                    case (6):
                        updateCandidatesApplication();
                    case (7):
                        offerJobToApplicant();
                        break;
                    case (8):
                        mailApplicants();
                        break;
                    case (9):
                        viewPositionDetails();
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
        if(position.getAppliedApplicants().size() > 0)
        {
            while (!validResponse) {
                try {
                    System.out.println("Which applicant would you like to shortlist?\n");
                    System.out.println(position.applicantListToStringAsNumberedList(position.getAppliedApplicants()));
                    response = scanner.nextInt();
                    scanner.nextLine();
                    applicant = position.getAppliedApplicants().get(response - 1);
                    validResponse = true;
                    currentUser.shortlistApplicant(applicant, position);
                    System.out.printf("%s was successfully shortlisted\n\n", applicant.getName());
                } catch (InputMismatchException e) {
                    printInputMismatchMessage();
                } catch (ArrayIndexOutOfBoundsException e) {
                    printInputMismatchMessage();
                }
            }
        }
        else
        {
            printNoApplicants();
        }

    }

    private void viewShortlistedApplicants() {
        List<Applicant> totalShortlistedApplicants = getTotalShortlistedApplicants();

        System.out.printf("These are all the shortlisted applicants for the position '%s'..\n\n",
                position.getTitle());
        System.out.println(position.applicantListToStringAsNumberedList(totalShortlistedApplicants));
    }

    // build new listed of applicants shortlisted by the system and by the employer
    private List<Applicant> getTotalShortlistedApplicants() {
        List<Applicant> totalShortlistedApplicants = new ArrayList<>();
        for (Applicant a : position.getShortListedApplicants()) {
            if (!totalShortlistedApplicants.contains(a)) {
                totalShortlistedApplicants.add(a);
            }
        }
        for (Applicant a : position.getHighRankingApplicants()) {
            if (!totalShortlistedApplicants.contains(a)) {
                totalShortlistedApplicants.add(a);
            }
        }
        return totalShortlistedApplicants;
    }

    private void rankApplicants() {
        System.out.println("Ranking applicants...\n");
        currentUser.rankApplicants(position);
        System.out.println("The applicants have been automatically ranked by the system..\n");
    }

    private void inviteApplicantForInterview() {
        List<Applicant> totalShortlistedApplicants = getTotalShortlistedApplicants();
        boolean validResponse = false;
        Applicant applicant = null;
        int response;
        while (!validResponse) {
            try {
                System.out.println("Which applicant would you like to invite for an interview?\n");
                System.out.println(position.applicantListToStringAsNumberedList(totalShortlistedApplicants));
                response = scanner.nextInt();
                scanner.nextLine();
                applicant = totalShortlistedApplicants.get(response - 1);
                validResponse = true;
                currentUser.offerInterview(applicant, position);
                System.out.printf("%s has been successfully invited for an interview\n\n", applicant.getName());
            } catch (InputMismatchException e) {
                printInputMismatchMessage();
            } catch (ArrayIndexOutOfBoundsException e) {
                printInputMismatchMessage();
            } catch (UserBlacklistedException e) {
                System.out.printf("Sorry, Cannot invite %s for an interview as they have been blacklisted..", applicant.getName());
            }
        }

    }

    private void updateCandidatesApplication() {
        boolean validResponse = false;
        Applicant applicant;
        int response;
        while (!validResponse) {
            try {
                System.out.println("Which candidate's application would you like to update?\n");
                System.out.println(position.applicantListToStringAsNumberedList(position.getInterviewedApplicants()));
                response = scanner.nextInt();
                applicant = position.getInterviewedApplicants().get(response - 1);
                validResponse = true;
                updateCandidate(applicant);
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
                printInputMismatchMessage();
            }
        }
    }

    private void updateCandidate(Applicant applicant) {
        boolean goBack = false;
        int response;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Update Candidate's Reference Check\n" +
                        "2. Leave A Note'\n\n" +
                        "0. Go Back\n\n");
                response = scanner.nextInt();
                scanner.nextLine();
                switch (response) {
                    case (1):
                        updateCandidatesReferenceCheck(applicant);
                        break;
                    case (2):
                        leaveANote(applicant);
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

    private void updateCandidatesReferenceCheck(Applicant applicant) {
        System.out.println("Did the candidate's references checkout?\n");
        for (InterviewSlot i : applicant.getInterviewSlots()) {
            if (i.getPosition().getTitle().equals(position.getTitle())) {
                boolean goBack = false;
                int response;
                while (!goBack) {
                    try {
                        System.out.printf(
                                "1. Yes\n" +
                                        "2. No'\n\n" +
                                        "0. Go Back\n\n");
                        response = scanner.nextInt();
                        scanner.nextLine();
                        switch (response) {
                            case (1):
                                i.getInterviewResult().setReferenceValidity(true);
                                break;
                            case (2):
                                i.getInterviewResult().setReferenceValidity(false);
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
        }
    }

    private void leaveANote(Applicant applicant) {
        System.out.println("What would you like to say about the candidate\n");
        String note = scanner.nextLine();
        for (InterviewSlot i : applicant.getInterviewSlots()) {
            if (i.getPosition().getTitle().equals(position.getTitle())) {
                i.getInterviewResult().setInterviewNotes(note);
                System.out.println("Note was successfully left..\n");
            }
        }
    }

    private void offerJobToApplicant() {
        boolean validResponse = false;
        Applicant applicant = null;
        int response;
        while (!validResponse) {
            try {
                System.out.println("Which applicant would you like to offer a job to?\n");
                System.out.println(position.applicantListToStringAsNumberedList(position.getHighRankingApplicants()));
                response = scanner.nextInt();
                scanner.nextLine();
                applicant = position.getHighRankingApplicants().get(response - 1);
                validResponse = true;
                currentUser.offerJob(applicant, position);
                System.out.printf("Job successfully offered to %s", applicant.getName());
            } catch (InputMismatchException e) {
                printInputMismatchMessage();
            } catch (ArrayIndexOutOfBoundsException e) {
                printInputMismatchMessage();
            } catch (UserBlacklistedException e) {
                System.out.printf("Sorry, Cannot offer job to %s as they have been blacklisted..", applicant.getName());
            }
        }
    }

    private void mailApplicants()
    {
        String message;
        String title;
        Notification notification = new Notification("Message from " + currentUser.getName(), currentUser);

        if(position.getAppliedApplicants().size() > 0)
        {
            System.out.println("Title of the message?\n");
            title = scanner.next();
            scanner.nextLine();

            System.out.println("What is your message to be sent to the applicants?\n");
            message = scanner.next();
            scanner.nextLine();

            for (Applicant app : position.getAppliedApplicants()) {
                app.addMail(currentUser, title, message);
                app.addNotification(notification);
            }

            System.out.println("\nMessage has been sent to applicants!");
        }
        else
        {
            printNoApplicants();
        }
    }

    private void viewPositionDetails() {
        System.out.println(String.format("%s\n", position.toStringVerbose()));
    }
}
