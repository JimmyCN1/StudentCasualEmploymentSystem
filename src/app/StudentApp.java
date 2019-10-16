package app;

import enumerators.PositionType;
import enumerators.UserStatus;
import exceptions.*;
import model.position.Position;
import model.system.ManagementSystem;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.applicant.InternationalStudent;
import model.user.applicant.LocalStudent;
import model.user.applicant.utilities.License;
import model.user.applicant.utilities.PastJob;
import model.user.applicant.utilities.Qualification;
import model.user.applicant.utilities.Reference;
import model.user.employer.Employer;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class StudentApp extends AbstractApp {
    private final int LOCAL = 1;
    private final int INTERNATIONAL = 2;

    private Applicant currentUser;

    public StudentApp(String username,
                      String password,
                      ManagementSystem managementSystem)
            throws UserNotFoundException, PasswordMissmatchException {
        super(managementSystem);
        verifyUser(username, password);
    }

    public StudentApp(ManagementSystem managementSystem) {
        super(managementSystem);
    }

    // set the current user logged in
    @Override
    public void setCurrentUser(User applicant) {
        super.setCurrentUser(applicant);
        currentUser = (Applicant) applicant;
    }

    public void selectStudentType() {
        boolean validResponse = false;
        while (!validResponse) {
            System.out.println("Which type of student are you?");
            System.out.println("1. Local\n2. International");
            int response = scanner.nextInt();
            scanner.nextLine();
            switch (response) {
                case (LOCAL):
                    validResponse = true;
                    createLocalStudent();
                    break;
                case (INTERNATIONAL):
                    validResponse = true;
                    createInternationalStudent();
                    break;
                default:
                    break;
            }
        }
    }

    // creates a new local student in the application
    private void createLocalStudent() {
        Map<String, String> studentDetails = getPersonalDetails();
        PositionType positionType = getStudentAvailability();
        LocalStudent newLocalStudent = new LocalStudent(
                studentDetails.get(FIRST_NAME),
                studentDetails.get(LAST_NAME),
                studentDetails.get(PASSWORD),
                positionType,
                managementSystem);
        newLocalStudent.setUsername(studentDetails.get(USERNAME));
        managementSystem.registerApplicant(newLocalStudent);
        try {
            setCurrentUser(managementSystem.getApplicantByUsername(
                    studentDetails.get(USERNAME).toLowerCase()
            ));
        } catch (ApplicantNotFoundException e) {
            System.out.println("Error creating applicant. Please try again");
        }
    }

    // creates a new international student in the application
    private void createInternationalStudent() {
        Map<String, String> studentDetails = getPersonalDetails();
        managementSystem.registerApplicant(new InternationalStudent(
                studentDetails.get(FIRST_NAME),
                studentDetails.get(LAST_NAME),
                studentDetails.get(PASSWORD),
                managementSystem));
        try {
            setCurrentUser(managementSystem.getApplicantByUsername(
                    studentDetails.get(USERNAME).toLowerCase()
            ));
        } catch (ApplicantNotFoundException e) {
            System.out.println("Error creating applicant. Please try again");
        }
    }

    // returns the selected availability
    private PositionType getStudentAvailability() {
        PositionType type = null;
        boolean goBack = false;
        while (!goBack) {
            System.out.println("What is your availability?");
            System.out.printf("1. Part-Time\n2. Full-Time\n3. Internship\n\n");
            switch (scanner.nextInt()) {
                case (1):
                    goBack = true;
                    type = PositionType.PART_TIME;
                    break;
                case (2):
                    goBack = true;
                    type = PositionType.FULL_TIME;
                    break;
                case (3):
                    goBack = true;
                    type = PositionType.INTERNSHIP;
                    break;
            }
        }
        return type;
    }

    // determines whether the login details provided are provided
    private void verifyUser(String username, String password)
            throws UserNotFoundException, PasswordMissmatchException {
        Applicant applicant = managementSystem.getApplicantByUsername(username);
        if (applicant == null) {
            throw new UserNotFoundException();
        } else {
            if (!applicant.verifyPassword(password)) {
                throw new PasswordMissmatchException();
            } else {
                setCurrentUser(applicant);
                System.out.printf("Welcome back %s!\n\n", getCurrentUser().getName());
            }
        }
    }

    // if blacklisted, display blacklisted applicant message, else display the main menu
    public void displayMainMenu() {
        //TODO: selecting interview slot/time
        //TODO: accept/reject job offers
        boolean isLoggedIn = true;
        int response;
        while (isLoggedIn) {
            if (currentUser.getStatus().equals(UserStatus.BLACKLISTED)) {
                isLoggedIn = showBlacklistedScreen();
            } else {
                try {
                    System.out.printf("What would you like to do?\n\n" +
                            "1. Update Your Job Preferences\n" +
                            "2. Update Your Availabilities\n" +
                            "3. Update Your Employment Records\n" +
                            "4. Update CV\n" +
                            "5. View All Currently Posted Jobs\n" +
                            "6. Apply For A Job\n" +
                            "7. View Jobs Shortlisted For\n" +
                            "8. View Job Offer\n" +
                            "9. View Emails\n" +
                            "10. Change Login Details\n\n" +
                            "0. Logout\n\n");
                    response = scanner.nextInt();
                    scanner.nextLine();
                    switch (response) {
                        case (1):
                            updateJobPreferences();
                            break;
                        case (2):
                            updateAvailabilities();
                            break;
                        case (3):
                            updateEmploymentRecords();
                            break;
                        case (4):
                            updateCV();
                            break;
                        case (5):
                            viewAllCurrentlyPostedJobs();
                            break;
                        case (6):
                            applyForAJob();
                            break;
                        case (7):
                            viewJobsShortlistedFor();
                            break;
                        case (8):
                            viewJobOffer();
                            break;
                        case (9):
                            viewEmails();
                            break;
                        case (10):
                            changeLoginDetails();
                        case (0):
                            isLoggedIn = false;
                            break;
                    }
                } catch (InputMismatchException e) {
                    printInputMismatchMessage();
                }
            }

        }
    }

    private void updateJobPreferences() {
        boolean goBack = false;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Add A Job Preference\n" +
                        "2. Remove A Job Preference\n" +
                        "3. View Preferences\n" +
                        "4. Lodge A Complaint\n\n" +
                        "0. Go back\n\n");
                int response = scanner.nextInt();
                scanner.nextLine();
                switch (response) {
                    case (1):
                        addJobPreference();
                        break;
                    case (2):
                        removeJobPreference();
                        break;
                    case (3):
                        viewPreferences();
                        break;
                    case (4):
                        lodgeComplaintAgainstEmployer();
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

    private void addJobPreference() {
        System.out.println("Please type one of these job preferences to add to your selected preferences\n");
        for (String jc : managementSystem.getJobCategories()) {
            System.out.println(jc);
        }
        System.out.println("\n");
        String response = scanner.nextLine();
        try {
            currentUser.addJobPreference(response);
            viewPreferences();
        } catch (InvalidJobCategoryException e) {
            System.out.printf("%s does not exist..\n\n", response);
        }
    }

    private void removeJobPreference() {
        System.out.println("Please type one of these job preferences to removed to your selected preferences\n");
        for (String jp : currentUser.getJobPreferences()) {
            System.out.println(jp);
        }
        System.out.println("\n");
        String response = scanner.nextLine();
        try {
            currentUser.removeJobPreference(response);
            viewPreferences();
        } catch (JobCategoryNotFoundException e) {
            System.out.printf("%s was not found..\n\n", response);
        }
    }

    private void viewPreferences() {
        System.out.println("Your job preferences are now..");
        for (String jp : currentUser.getJobPreferences()) {
            System.out.println(jp);
        }
        System.out.println("\n");
    }

    private void updateAvailabilities() {
        boolean goBack = false;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Add An Availability\n" +
                        "2. Remove An Availability\n" +
                        "3. View Availabilities\n" +
                        "0. Go back\n\n");
                int response = scanner.nextInt();
                scanner.nextLine();
                switch (response) {
                    case (1):
                        addAvailability();
                        break;
                    case (2):
                        removeAvailability();
                        break;
                    case (3):
                        viewAvailabilities();
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

    private void viewAllCurrentlyPostedJobs() {
        List<Position> allPositions = new ArrayList<>();
        for (Employer e : managementSystem.getEmployersAsList()) {
            allPositions.addAll(e.getPositions());
        }
        System.out.printf("Here are all the currently posted positions in the system..\n\n");
        for (int i = 0; i < allPositions.size(); i++) {
            System.out.printf("%d. %s", i + 1, allPositions.get(i).toString());
        }
    }

    private void applyForAJob() {
        List<Position> allPositions = new ArrayList<>();
        for (Employer e : managementSystem.getEmployersAsList()) {
            allPositions.addAll(e.getPositions());
        }
        System.out.printf("Which position would you like to apply to?\n");
        for (int i = 0; i < allPositions.size(); i++) {
            System.out.printf("%d. %s", i + 1, allPositions.get(i).toString());
        }
        boolean validResponse = false;
        Position appliedPosition = null;
        int response = 0;
        while (!validResponse) {
            try {
                response = scanner.nextInt();
                scanner.nextLine();
                appliedPosition = allPositions.get(response - 1);
                currentUser.applyToPosition(appliedPosition);
                System.out.printf("You have successfully applied to the position %s", appliedPosition.toString());
                validResponse = true;
            } catch (InputMismatchException e) {
                printInputMismatchMessage();
            } catch (ArrayIndexOutOfBoundsException e) {
                printInputMismatchMessage();
            }
        }
    }

    private void addAvailability() {
        System.out.println("Please type one of these availabilities to add to your selected availabilities\n");
        for (PositionType availability : PositionType.values()) {
            System.out.printf("%s ", availability);
        }
        System.out.println("\n");
        String response = scanner.nextLine();
        try {
            currentUser.addAvailability(response);
            viewAvailabilities();
        } catch (PositionTypeNotFoundException e) {
            System.out.println("Sorry, this is not a valid availability..\n");
        } catch (InternationalStudentAvailabilityException e) {
            System.out.println("Sorry, as an international student, you can only work part time..\n");
        }
    }

    private void removeAvailability() {
        System.out.println("Please type one of these availabilities to removed to your selected availabilities\n");
        for (PositionType availability : currentUser.getAvailabilities()) {
            System.out.printf("%s ", availability);
        }
        System.out.println("\n");
        String response = scanner.nextLine();
        try {
            currentUser.removeAvailability(response);
            viewAvailabilities();
        } catch (PositionTypeNotFoundException e) {
            System.out.println("Sorry, this is not a valid availability..\n");
        } catch (InternationalStudentAvailabilityException e) {
            System.out.println("Sorry, as an international student, you can only work part time..\n");
        }
    }

    private void viewAvailabilities() {
        System.out.println("Your availabilities are now..");
        for (PositionType availability : currentUser.getAvailabilities()) {
            System.out.printf("%s ", availability);
        }
        System.out.println("\n");
    }

    private void viewJobsShortlistedFor() {
        for (Position pos : currentUser.getShortlisted()) {
            System.out.printf("%s \n", pos.toString());
        }
        System.out.println("\n");
    }

    private void viewEmails()
    {
        currentUser.viewMailList();

        if(currentUser.getMailSize() > 0)
        {
            boolean validResponse = false;
            int response;

            while(!validResponse)
            {
                try
                {
                    response = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(currentUser.getMail(response - 1));
                    validResponse = true;
                }
                catch(InputMismatchException e)
                {
                    printInputMismatchMessage();
                }
            }
        }
    }

    private void updateEmploymentRecords()
    {
        boolean goBack = false;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Add A Past Job\n" +
                        "2. Remove A Past Job\n" +
                        "3. View Past Jobs\n" +
                        "4. Add A Licence\n" +
                        "5. Remove A Licence\n" +
                        "6. View Licences\n" +
                        "7. Add a Qualification\n" +
                        "8. Remove a Qualification\n" +
                        "9. View Qualifications\n" +
                        "10. Add a Reference\n" +
                        "11. Remove a Reference\n" +
                        "12. View References\n" +
                        "13. Lodge A Complaint\n\n" +
                        "0. Go back\n\n");
                int response = scanner.nextInt();
                scanner.nextLine();
                switch (response) {
                    case (1):
                        addPastJob();
                        break;
                    case (2):
                        removePastJob();
                        break;
                    case (3):
                        viewPastJobs();
                        break;
                    case (4):
                        addLicence();
                        break;
                    case (5):
                        removeLicence();
                        break;
                    case (6):
                        viewLicence();
                        break;
                    case (7):
                        addQualification();
                        break;
                    case (8):
                        removeQualification();
                        break;
                    case (9):
                        viewQualifications();
                        break;
                    case (10):
                        addReference();
                        break;
                    case (11):
                        removeReference();
                        break;
                    case (12):
                        viewReferences();
                        break;
                    case (13):
                        lodgeComplaintAgainstEmployer();
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

    private void addPastJob() {
        boolean validInput = false;

        while(!validInput)
        {
            try
            {
                System.out.println("For what company?");
                String company = scanner.nextLine();

                System.out.println("What was your title?");
                String title = scanner.nextLine();

                System.out.println("When did you start? (Format: DD-MM-YYYY)");
                String startDateStr = scanner.nextLine();
                Scanner s = new Scanner(startDateStr).useDelimiter("-");
                int day = s.nextInt();
                int month = s.nextInt();
                int year = s.nextInt();
                LocalDate startDate = LocalDate.of(year,month,day);

                System.out.println("When did you end? (Format: DD-MM-YYYY)");
                String endDateStr = scanner.nextLine();
                Scanner s2 = new Scanner(endDateStr).useDelimiter("-");
                int day2 = s2.nextInt();
                int month2 = s2.nextInt();
                int year2 = s2.nextInt();
                LocalDate endDate = LocalDate.of(year2,month2,day2);

                System.out.println("What where your responsibilities? (Enter each one then press enter! type 0 to stop)");
                String input = "";
                List<String> respList = new ArrayList<>();

                boolean escape = false;
                while(!escape)
                {
                    input = scanner.nextLine();

                    if(!input.equals("0"))
                    {
                        respList.add(input);
                    }
                    else
                    {
                        escape = true;
                    }
                }

                PastJob pastJob = new PastJob(company, title, startDate, endDate, respList);

                currentUser.addPastJob(pastJob);

                validInput = true;

                System.out.println("Successfully added past job");
                viewPastJobs();
            }
            catch (InputMismatchException e)
            {
                printInputMismatchMessage();
            }
        }
    }

    private void removePastJob() {
        System.out.println("Please type one of these past jobs to remove\n");
        int count = 1;
        for (PastJob pj : currentUser.getPastJobs()) {
            System.out.println(count + ". " + pj.toString());
            count++;
        }
        System.out.println("\n");
        int response = scanner.nextInt();

        currentUser.removePastJob(response - 1);
        viewPastJobs();
    }

    private void viewPastJobs() {
        System.out.println("Your past job are..");
        int count = 1;
        for (PastJob pj : currentUser.getPastJobs()) {
            System.out.println(count + ". " + pj.toString());
            count++;
        }
        System.out.println("\n");
    }

    private void addLicence() {
        boolean validInput = false;

        while(!validInput)
        {
            try
            {
                System.out.println("What Licence name?");
                String licenceName = scanner.nextLine();

                System.out.println("What is the licence number?");
                int licenceNumber = scanner.nextInt();

                System.out.println("Who issued it?");
                String issuer = scanner.nextLine();

                System.out.println("When was it issued? (Format: DD-MM-YYYY)");
                String issuedDateStr = scanner.nextLine();
                Scanner s = new Scanner(issuedDateStr).useDelimiter("-");
                int day = s.nextInt();
                int month = s.nextInt();
                int year = s.nextInt();
                LocalDate issuedDate = LocalDate.of(year,month,day);

                System.out.println("When is it valid till? (Format: DD-MM-YYYY)");
                String validDateStr = scanner.nextLine();
                Scanner s2 = new Scanner(validDateStr).useDelimiter("-");
                int day2 = s2.nextInt();
                int month2 = s2.nextInt();
                int year2 = s2.nextInt();
                LocalDate validDate = LocalDate.of(year2,month2,day2);

                License license = new License(licenceName,licenceNumber,issuer,issuedDate,validDate);

                currentUser.addLicense(license);

                validInput = true;

                System.out.println("Successfully added a licence");
                viewLicence();
            }
            catch (InputMismatchException e)
            {
                printInputMismatchMessage();
            }
        }
    }

    private void removeLicence() {
        System.out.println("Please type one of these licences to remove\n");
        int count = 1;
        for (License l : currentUser.getLicenses()) {
            System.out.println(count + ". " + l.toString());
            count++;
        }
        System.out.println("\n");
        int response = scanner.nextInt();

        currentUser.removeLicense(response - 1);
        viewLicence();
    }

    private void viewLicence() {
        System.out.println("Your licences are..");
        int count = 1;
        for (License l : currentUser.getLicenses()) {
            System.out.println(count + ". " + l.toString());
            count++;
        }
        System.out.println("\n");
    }

    private void addQualification() {
        boolean validInput = false;

        while(!validInput) {
            try {
                System.out.println("What is the course name?");
                String course = scanner.nextLine();

                System.out.println("What organisation?");
                String organisation = scanner.nextLine();

                System.out.println("When did you start? (Format: DD-MM-YYYY)");
                String issuedDateStr = scanner.nextLine();
                Scanner s = new Scanner(issuedDateStr).useDelimiter("-");
                int day = s.nextInt();
                int month = s.nextInt();
                int year = s.nextInt();
                LocalDate startDate = LocalDate.of(year, month, day);

                System.out.println("When did you end? (Format: DD-MM-YYYY)");
                String validDateStr = scanner.nextLine();
                Scanner s2 = new Scanner(validDateStr).useDelimiter("-");
                int day2 = s2.nextInt();
                int month2 = s2.nextInt();
                int year2 = s2.nextInt();
                LocalDate endDate = LocalDate.of(year2, month2, day2);

                Qualification qualification = new Qualification(course, organisation, startDate, endDate);

                currentUser.addQualification(qualification);

                validInput = true;

                System.out.println("Successfully added a qualification");
                viewReferences();
            } catch (InputMismatchException e) {
                printInputMismatchMessage();
            }
        }
    }

    private void removeQualification() {
        System.out.println("Please type one of these qualifications to remove\n");
        int count = 1;
        for (Qualification q : currentUser.getQualifications()) {
            System.out.println(count + ". " + q.toString());
            count++;
        }
        System.out.println("\n");
        int response = scanner.nextInt();

        currentUser.removeQualification(response - 1);
        viewQualifications();
    }

    private void viewQualifications() {
        System.out.println("Your qualifications are..");
        int count = 1;
        for (Qualification q : currentUser.getQualifications()) {
            System.out.println(count + ". " + q.toString());
            count++;
        }
        System.out.println("\n");
    }

    private void addReference() {
        boolean validInput = false;

        while(!validInput) {
            try {
                System.out.println("What is the reference name?");
                String name = scanner.nextLine();

                System.out.println("What is their number?");
                String number = scanner.nextLine();

                System.out.println("What is their email?");
                String email = scanner.nextLine();

                Reference reference = new Reference(name, number, email);

                currentUser.addReference(reference);

                validInput = true;

                System.out.println("Successfully added a reference");
                viewReferences();
            } catch (InputMismatchException e) {
                printInputMismatchMessage();
            }
        }
    }

    private void removeReference() {
        System.out.println("Please type one of these references to remove\n");
        int count = 1;
        for (Reference r : currentUser.getReferences()) {
            System.out.println(count + ". " + r.toString());
            count++;
        }
        System.out.println("\n");
        int response = scanner.nextInt();

        currentUser.removeReference(response - 1);
        viewReferences();
    }

    private void viewReferences() {
        System.out.println("Your references are..");
        int count = 1;
        for (Reference r : currentUser.getReferences()) {
            System.out.println(count + ". " + r.toString());
            count++;
        }
        System.out.println("\n");
    }



    private void viewJobOffer()
    {
        if(currentUser.getJobOffer() != null)
        {
            System.out.println("Job offer: \n");
            System.out.println(currentUser.getJobOffer().toStringVerbose());
        }
        else
        {
            System.out.println("No Job Offer");
        }
    }

    private void updateCV()
    {
        boolean goBack = false;
        while (!goBack) {
            try {
                System.out.printf("What would you like to do?\n\n" +
                        "1. Add CV\n" +
                        "2. Remove CV\n" +
                        "3. View CV\n" +
                        "0. Go back\n\n");
                int response = scanner.nextInt();
                scanner.nextLine();
                switch (response) {
                    case (1):
                        addCV();
                        break;
                    case (2):
                        removeCV();
                        break;
                    case (3):
                        viewCV();
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

    private void addCV()
    {
        System.out.println("Enter the file name of your CV: (Must be .txt file, but do not include in file name below)");
        String filename = scanner.nextLine();
        filename += ".txt";

        try
        {
            FileReader file = new FileReader(filename);
            BufferedReader bR = new BufferedReader(file);

            String cv = "";
            String line = null;

            while((line = bR.readLine()) != null)
            {
                cv += line + "\n";
            }

            currentUser.setCv(cv);
            System.out.println("CV has been added!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void removeCV()
    {
        currentUser.setCv("");

        System.out.println("CV has been removed!\n");
    }

    private void viewCV()
    {
        if(currentUser.getCv() != "")
        {
            System.out.println(currentUser.getCv() + "\n");
        }
        else
        {
            System.out.println("No CV has been added!\n");
        }
    }
}
