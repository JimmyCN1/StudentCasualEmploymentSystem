package model.serialisation;

import model.system.ManagementSystem;
import model.user.User;
import model.user.applicant.Applicant;
import model.user.employer.Employer;
import model.user.staff.SystemMaintenanceStaff;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SaveState implements Serializable {
    private static final long serialVersionUID = 5L;

    private ManagementSystem managementSystem;



    public SaveState(ManagementSystem managementSystem)
    {
        this.managementSystem = managementSystem;
    }

    public void recoverUsers(int size, Map<Integer, User> users, String userFileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(userFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for (int i = 0; i < size; i++) {
            User user = (User) in.readObject();
            users.put(user.getUserId(), user);
        }

        in.close();
        file.close();
    }

    public void recoverApplicants(int size, Map<String, Applicant> applicants, String appFileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(appFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for (int i = 0; i < size; i++) {
            Applicant app = (Applicant) in.readObject();
            applicants.put(app.getHashMapKey(), app);
        }

        in.close();
        file.close();
    }

    public void recoverEmployers(int size, Map<String, Employer> employers, String empFileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(empFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for (int i = 0; i < size; i++) {
            Employer emp = (Employer) in.readObject();
            employers.put(emp.getHashMapKey(), emp);
        }

        in.close();
        file.close();
    }

    public void recoverSystemMaintenance(int size, Map<String, SystemMaintenanceStaff> systemMaintenanceStaff, String smFileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(smFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for (int i = 0; i < size; i++) {
            SystemMaintenanceStaff sm = (SystemMaintenanceStaff) in.readObject();
            systemMaintenanceStaff.put(sm.getHashMapKey(), sm);
        }

        in.close();
        file.close();
    }

    public void recoverBlacklisted(int size, Map<String, User> blacklistedUsers, String blFileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(blFileName);
        ObjectInputStream in = new ObjectInputStream(file);

        for (int i = 0; i < size; i++) {
            User bl = (User) in.readObject();
            blacklistedUsers.put(bl.getHashMapKey(), bl);
        }

        in.close();
        file.close();
    }


    // Saves the sizes of all the hash maps in a txt file
    public void saveSizes(String sizeFileName, int uSize, int aSize, int eSize, int sSize, int bSize) throws IOException {
        FileWriter writer = new FileWriter(new File(sizeFileName));
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("uSize:" + uSize + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("aSize:" + aSize + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("eSize:" + eSize + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("sSize:" + sSize + ":");
        bufferedWriter.newLine();
        bufferedWriter.write("bSize:" + bSize + ":");

        bufferedWriter.close();
    }

    // Saves all of the users into users.ser
    public void saveUsers(String userFileName) throws IOException {
        FileOutputStream file = new FileOutputStream(userFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User user;
        List<User> list = managementSystem.getUsersAsList();

        for (int i = 0; i < list.size(); i++) {
            user = list.get(i);

            out.writeObject(user);
        }

        out.close();
        file.close();
    }

    // Saves all of the applicants into applicants.ser
    public void saveApplicants(String appFileName) throws IOException {
        FileOutputStream file = new FileOutputStream(appFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        Applicant app;
        List<Applicant> list = managementSystem.getApplicantsAsList();

        for (int i = 0; i < list.size(); i++) {
            app = list.get(i);

            out.writeObject(app);
        }

        out.close();
        file.close();
    }

    // Saves all of the employers into employers.ser
    public void saveEmployers(String empFileName) throws IOException {
        FileOutputStream file = new FileOutputStream(empFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User emp;
        List<Employer> list = managementSystem.getEmployersAsList();

        for (int i = 0; i < list.size(); i++) {
            emp = list.get(i);

            out.writeObject(emp);
        }

        out.close();
        file.close();
    }

    // Saves all of the systemMaintenanceStaff into systemMaintenance.ser
    public void saveSystemMaintenanceStaff(String smFileName) throws IOException {
        FileOutputStream file = new FileOutputStream(smFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User sm;
        List<SystemMaintenanceStaff> list = managementSystem.getSystemMaintenanceStaffAsList();

        for (int i = 0; i < list.size(); i++) {
            sm = list.get(i);

            out.writeObject(sm);
        }

        out.close();
        file.close();
    }

    // Saves all of the blacklisted users into blacklisted.ser
    public void saveBlacklisted(String blFileName) throws IOException {
        FileOutputStream file = new FileOutputStream(blFileName);
        ObjectOutputStream out = new ObjectOutputStream(file);

        User bl;
        List<User> list = managementSystem.getBlacklistedAsList();

        for (int i = 0; i < list.size(); i++) {
            bl = list.get(i);

            out.writeObject(bl);
        }

        out.close();
        file.close();
    }


}
