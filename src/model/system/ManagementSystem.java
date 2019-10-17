package model.system;

import exceptions.ApplicantNotFoundException;
import exceptions.EmployerNotFoundException;
import exceptions.SystemMaintenanceStaffNotFoundException;
import interfaces.UserInterface;
import model.serialisation.SaveState;
//import model.system.utilities.Security;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.*;

public class ManagementSystem implements Serializable {
    private static final long serialVersionUID = 6L;

    private Map<Integer, User> users = new HashMap<>();
    private Map<String, Employer> employers = new HashMap<>();
    private Map<String, Applicant> applicants = new HashMap<>();
    private Map<String, SystemMaintenanceStaff> systemMaintenanceStaff = new HashMap<>();
    private Map<String, User> blacklistedUsers = new HashMap<>();
    private List<String> jobCategories = new ArrayList<>(
            Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE")
    );

    private String dir = Paths.get("").toAbsolutePath().toString();

    // File names for serialisation
    private String userFileName = dir + "/saves/users.ser";
    private String appFileName = dir + "/saves/applicants.ser";
    private String empFileName = dir + "/saves/employers.ser";
    private String smFileName = dir + "/saves/systemMaintenance.ser";
    private String blFileName = dir + "/saves/blacklisted.ser";
    private String sizeFileName = dir + "/saves/sizes.txt";

    // TESTING PURPOSES -- File names for serialisation to input data into the program
    private String TESTuserFileName = dir + "/saves/test/TESTusers.ser";
    private String TESTappFileName = dir + "/saves/test/TESTapplicants.ser";
    private String TESTempFileName = dir + "/saves/test/TESTemployers.ser";
    private String TESTsmFileName = dir + "/saves/test/TESTsystemMaintenance.ser";
    private String TESTblFileName = dir + "/saves/test/TESTblacklisted.ser";
    private String TESTsizeFileName = dir + "/saves/test/TESTsizes.txt";

    private SaveState saveState = new SaveState(this);

    public ManagementSystem() {
    }

    public List<User> getUsersAsList() {
        List<User> users = new ArrayList<>();
        for (Integer userId : this.users.keySet()) {
            users.add(this.users.get(userId));
        }
        return users;
    }

    public List<Employer> getEmployersAsList() {
        List<Employer> employers = new ArrayList<>();
        for (String e : this.employers.keySet()) {
            employers.add(this.employers.get(e));
        }
        return employers;
    }

    public List<Applicant> getApplicantsAsList() {
        List<Applicant> applicants = new ArrayList<>();
        for (String a : this.applicants.keySet()) {
            applicants.add(this.applicants.get(a));
        }
        return applicants;
    }

    public List<SystemMaintenanceStaff> getSystemMaintenanceStaffAsList() {
        List<SystemMaintenanceStaff> systemMaintenanceStaff = new ArrayList<>();
        for (String s : this.systemMaintenanceStaff.keySet()) {
            systemMaintenanceStaff.add(this.systemMaintenanceStaff.get(s));
        }
        return systemMaintenanceStaff;
    }

    public List<User> getBlacklistedAsList() {
        List<User> blacklisted = new ArrayList<>();
        for (String b : blacklistedUsers.keySet()) {
            blacklisted.add(blacklistedUsers.get(b));
        }

        return blacklisted;
    }


    public Map<String, User> getBlacklistedUsers() {
        return blacklistedUsers;
    }

    public List<String> getJobCategories() {
        return jobCategories;
    }

    public UserInterface getUserByName(String keyName) {
        return users.get(keyName);
    }

    public Employer getEmployerByUsername(String keyName)
            throws EmployerNotFoundException {
        if (employers.containsKey(keyName)) {
            return employers.get(keyName);
        } else {
            throw new EmployerNotFoundException();
        }
    }

    public Applicant getApplicantByUsername(String keyName)
            throws ApplicantNotFoundException {
        if (applicants.containsKey(keyName)) {
            return applicants.get(keyName);

        } else {
            throw new ApplicantNotFoundException();
        }
    }

    public SystemMaintenanceStaff getSystemMaintenanceByUsername(String keyName)
            throws SystemMaintenanceStaffNotFoundException {
        if (systemMaintenanceStaff.containsKey(keyName)) {
            return systemMaintenanceStaff.get(keyName);
        } else {
            throw new SystemMaintenanceStaffNotFoundException();
        }
    }

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public void registerApplicant(Applicant applicant) {
        registerUser(applicant);
        applicants.put(applicant.getHashMapKey(), applicant);
    }

    public void registerEmployer(Employer employer) {
        registerUser(employer);
        employers.put(employer.getHashMapKey(), employer);
    }

    public void registerSystemMaintenanceStaff(SystemMaintenanceStaff systemMaintenanceStaff) {
        registerUser(systemMaintenanceStaff);
        this.systemMaintenanceStaff.put(
                systemMaintenanceStaff.getHashMapKey(),
                systemMaintenanceStaff);
    }

    public void addUserToBlacklist(User user) {
        blacklistedUsers.put(
                user.getHashMapKey(),
                user);
    }

    public void addJobCategory(String jobCategory) {
        jobCategories.add(jobCategory.toUpperCase());
    }


    // serialisation (Saving object states)
    public void recoverState() {
        try {
            Scanner read = new Scanner(new File(sizeFileName)).useDelimiter(":");

            read.next();
            int sizeU = read.nextInt();
            read.next();
            int sizeA = read.nextInt();
            read.next();
            int sizeE = read.nextInt();
            read.next();
            int sizeS = read.nextInt();
            read.next();
            int sizeB = read.nextInt();

            saveState.recoverUsers(sizeU, users, userFileName);
            saveState.recoverApplicants(sizeA, applicants, appFileName);
            saveState.recoverEmployers(sizeE, employers, empFileName);
            saveState.recoverSystemMaintenance(sizeS, systemMaintenanceStaff, smFileName);
            saveState.recoverBlacklisted(sizeB, blacklistedUsers, blFileName);
        } catch (IOException | ClassNotFoundException ex) {
            //System.out.println(ex);
        }
    }

    // Saves the state of all the objects so program can be closed and then reopened in same state
    public void saveState() {
        try {
            saveState.saveUsers(userFileName);
            saveState.saveApplicants(appFileName);
            saveState.saveEmployers(empFileName);
            saveState.saveSystemMaintenanceStaff(smFileName);
            saveState.saveBlacklisted(blFileName);
            saveState.saveSizes(sizeFileName, users.size(), applicants.size(), employers.size(), systemMaintenanceStaff.size(), blacklistedUsers.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void recoverTestState()
    {
        try
        {
            Scanner read = new Scanner(new File(TESTsizeFileName)).useDelimiter(":");

            // Welcome back message
            //System.out.println("~~~~~~Welcome Back~~~~~~\n");

            read.next();
            int sizeU = read.nextInt();
            read.next();
            int sizeA = read.nextInt();
            read.next();
            int sizeE = read.nextInt();
            read.next();
            int sizeS = read.nextInt();
            read.next();
            int sizeB = read.nextInt();

            saveState.recoverUsers(sizeU, users, TESTuserFileName);
            saveState.recoverApplicants(sizeA, applicants, TESTappFileName);
            saveState.recoverEmployers(sizeE, employers, TESTempFileName);
            saveState.recoverSystemMaintenance(sizeS, systemMaintenanceStaff, TESTsmFileName);
            saveState.recoverBlacklisted(sizeB, blacklistedUsers, TESTblFileName);
        } catch (IOException | ClassNotFoundException ex) {
            //System.out.println(ex);
            // A welcome message to when you first open the program.
            //System.out.println("~~~~~~Welcome to the Student Casual Employment System~~~~~~");
        }
    }

    public void saveTestState() {
        try
        {
            saveState.saveUsers(TESTuserFileName);
            saveState.saveApplicants(TESTappFileName);
            saveState.saveEmployers(TESTempFileName);
            saveState.saveSystemMaintenanceStaff(TESTsmFileName);
            saveState.saveBlacklisted(TESTblFileName);
            saveState.saveSizes(TESTsizeFileName, users.size(), applicants.size(), employers.size(), systemMaintenanceStaff.size(), blacklistedUsers.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
