package staff;

import java.util.ArrayList;
import java.util.Collection;

import applicant.Applicant;
import driver.ManagementSystem;
import employer.Employer;

public class SystemMaintenanceStaff {
	private static int systemMaintenanceStaffCount = 0;
	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private ManagementSystem managementSystem;

	public SystemMaintenanceStaff(String firstName, String lastName, String password, ManagementSystem managementSystem) {
		systemMaintenanceStaffCount++;
		this.id = systemMaintenanceStaffCount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public Employer getEmployerRecords(int id) {
		Employer returnEmployer = null;
		for (Employer employer : managementSystem.getEmployers()) {
			if (employer.getEmployerId() == id) {
				returnEmployer = employer;
			}
		}
		return returnEmployer;
	}

	public Applicant getApplicantRecords(int id) {
		Applicant returnApplicant = null;
		for (Applicant applicant : managementSystem.getApplicants()) {
			if (applicant.getApplicantId() == id) {
				returnApplicant = applicant;
			}
		}
		return returnApplicant;
	}

	public void blackListApplicant(Applicant applicant) {
		((Collection<Applicant>) ManagementSystem.applicant).add(applicant);
	}

	public void addNewJobCategory(String jobCategory) {
		((Collection<JobCategories>) ManagementSystem.jobCategories).add(jobCategory);
	}
}
