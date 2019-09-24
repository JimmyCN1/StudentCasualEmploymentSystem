package model.user.staff;

import enumerators.UserStatus;
import exceptions.InvalidUserStatusException;
import model.user.User;
import model.user.applicant.Applicant;
import model.system.ManagementSystem;
import model.user.employer.Employer;
import model.user.Person;

public class SystemMaintenanceStaff extends Person {
    private static int systemMaintenanceStaffCount = 0;
    private int id;
    private String password;

    public SystemMaintenanceStaff(String firstName, String lastName, String password, ManagementSystem managementSystem) {
        super(firstName, lastName, managementSystem);
        systemMaintenanceStaffCount++;
        this.id = systemMaintenanceStaffCount;
        setFirstName(firstName);
        setLastName(lastName);
        this.password = password;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(UserStatus status) {
    }

    @Override
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public Employer getEmployerRecords(int id) {
        Employer returnEmployer = null;
        for (Employer employer : getManagementSystem().getEmployersAsList()) {
            if (employer.getId() == id) {
                returnEmployer = employer;
            }
        }
        return returnEmployer;
    }

    public Applicant getApplicantRecords(int id) {
        Applicant returnApplicant = null;
        for (Applicant applicant : getManagementSystem().getApplicantsAsList()) {
            if (applicant.getId() == id) {
                returnApplicant = applicant;
            }
        }
        return returnApplicant;
    }

    //Blacklist a user
    public void blacklistUser(User user) throws InvalidUserStatusException {
        getManagementSystem().addUserToBlacklist(user);
        user.setStatus(UserStatus.BLACKLISTED);
    }

    //Create New Job Category
    public void addNewJobCategory(String jobCategory) {
        getManagementSystem().addJobCategory(jobCategory);
    }

    @Override
    public String statusToString() {
        return null;
    }
}
