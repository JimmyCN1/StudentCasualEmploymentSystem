package model.system;

import interfaces.UserInterface;
import model.user.applicant.Applicant;
import model.system.utilities.Security;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;
import model.user.User;

import java.io.*;
import java.util.*;

public class ManagementSystem implements Serializable{
    private Map<Integer, User> users = new HashMap<>();
    private Map<String, Employer> employers = new HashMap<>();
    private Map<String, Applicant> applicants = new HashMap<>();
    private Map<String, SystemMaintenanceStaff> systemMaintenanceStaff = new HashMap<>();
    private Map<String, User> blacklistedUsers = new HashMap<>();
    private List<String> jobCategories = new ArrayList<>(
            Arrays.asList("ENGINEERING", "TECHNOLOGY", "HOSPITALITY", "TRADE", "LOGISTICS", "RETAIL", "FINANCE")
    );
    private Security security;

    //For serialisation DO NOT TOUCH
    private String userFileName = "users.ser";
    private String appFileName = "applicants.ser";
    private String empFileName = "employers.ser";
    private String smFileName = "systemMaintenance.ser";
    private String blFileName = "blacklisted.ser";
    //-------------------------------

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

    public List<User> getBlacklistedAsList()
    {
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

    public Security getSecurity() {
        return security;
    }

    public UserInterface getUserByName(String keyName) {
        return users.get(keyName);
    }

    public Employer getEmployerByName(String keyName) {
        return employers.get(keyName);
    }

    public Applicant getApplicantByName(String keyName) {
        return applicants.get(keyName);
    }

    public SystemMaintenanceStaff getSystemMaintenanceByName(String keyName) {
        return systemMaintenanceStaff.get(keyName);
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



    // Serialisation (Saving object states)
    public void recoverState()
    {
        try
        {
            Scanner read = new Scanner(new File("sizes.txt")).useDelimiter(":");

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

            recoverUsers(sizeU);
            recoverApplicants(sizeA);
            recoverEmployers(sizeE);
            recoverSystemMaintenance(sizeS);
            recoverBlacklisted(sizeB);
        }
        catch(IOException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
    }

    public void recoverUsers(int size) throws IOException, ClassNotFoundException
    {
        FileInputStream file = new FileInputStream(userFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for(int i = 0; i < size; i++)
        {
            User user = (User)in.readObject();
            users.put(user.getUserId(), user);
        }

        in.close();
        file.close();
    }

    public void recoverApplicants(int size) throws IOException, ClassNotFoundException
    {
        FileInputStream file = new FileInputStream(appFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for(int i = 0; i < size; i++)
        {
            Applicant app = (Applicant) in.readObject();
            applicants.put(app.getHashMapKey(), app);
        }

        in.close();
        file.close();
    }

    public void recoverEmployers(int size) throws IOException, ClassNotFoundException
    {
        FileInputStream file = new FileInputStream(empFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for(int i = 0; i < size; i++)
        {
            Employer emp = (Employer) in.readObject();
            employers.put(emp.getHashMapKey(), emp);
        }

        in.close();
        file.close();
    }

    public void recoverSystemMaintenance(int size) throws IOException, ClassNotFoundException
    {
        FileInputStream file = new FileInputStream(smFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for(int i = 0; i < size; i++)
        {
            SystemMaintenanceStaff sm = (SystemMaintenanceStaff) in.readObject();
            systemMaintenanceStaff.put(sm.getHashMapKey(), sm);
        }

        in.close();
        file.close();
    }

    public void recoverBlacklisted(int size) throws IOException, ClassNotFoundException
    {
        FileInputStream file = new FileInputStream(blFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for(int i = 0; i < size; i++)
        {
            User bl = (User) in.readObject();
            blacklistedUsers.put(bl.getHashMapKey(), bl);
        }

        in.close();
        file.close();
    }


    // Saves the state of all the objects so program can be closed and then reopened in same state
    public void saveState()
    {
        try
        {
            saveUsers();
            saveApplicants();
            saveEmployers();
            saveSystemMaintenanceStaff();
            saveBlacklisted();
            saveSizes();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    // Saves the sizes of all the hash maps in a txt file
    public void saveSizes() throws IOException
    {
        FileWriter writer = new FileWriter("sizes.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("uSize:" + users.size() + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("aSize:" + applicants.size() + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("eSize:" + employers.size() + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("sSize:" + systemMaintenanceStaff.size() + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("bSize:" + blacklistedUsers.size() + ":");

        bufferedWriter.close();
    }

    // Saves all of the users into users.ser
    public void saveUsers() throws IOException
    {
        FileOutputStream file = new FileOutputStream(userFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User user;
        List<User> list = getUsersAsList();

        for (int i = 0; i < list.size(); i++)
        {
            user = list.get(i);

            out.writeObject(user);
        }

        out.close();
        file.close();
    }

    // Saves all of the applicants into applicants.ser
    public void saveApplicants() throws IOException
    {
        FileOutputStream file = new FileOutputStream(appFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        Applicant app;
        List <Applicant> list = getApplicantsAsList();

        for (int i = 0; i < list.size(); i++)
        {
            app = list.get(i);

            out.writeObject(app);
        }

        out.close();
        file.close();
    }

    // Saves all of the employers into employers.ser
    public void saveEmployers() throws IOException
    {
        FileOutputStream file = new FileOutputStream(empFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User emp;
        List <Employer> list = getEmployersAsList();

        for (int i = 0; i < list.size(); i++)
        {
            emp = list.get(i);

            out.writeObject(emp);
        }

        out.close();
        file.close();
    }

    // Saves all of the systemMaintenanceStaff into systemMaintenance.ser
    public void saveSystemMaintenanceStaff() throws IOException
    {
        FileOutputStream file = new FileOutputStream(smFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User sm;
        List <SystemMaintenanceStaff> list = getSystemMaintenanceStaffAsList();

        for (int i = 0; i < list.size(); i++)
        {
            sm = list.get(i);

            out.writeObject(sm);
        }

        out.close();
        file.close();
    }

    // Saves all of the blacklisted users into blacklisted.ser
    public void saveBlacklisted() throws IOException
    {
        FileOutputStream file = new FileOutputStream(blFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User bl;
        List <User> list = getBlacklistedAsList();

        for (int i = 0; i < list.size(); i++)
        {
            bl = list.get(i);

            out.writeObject(bl);
        }

        out.close();
        file.close();
    }



}
