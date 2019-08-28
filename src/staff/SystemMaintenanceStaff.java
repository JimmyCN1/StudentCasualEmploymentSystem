import java.util.Collection;

import driver.ManagementSystem;
import model.interfaces.Employer;
import model.interfaces.Applicant;

public class SystemMaintenanceStaff {

	private int id;
	private String firstName;
	private String lastName;
	private String password;

	public SystemMaintenanceStaff(int id, String firstName, String lastName, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public Collection<Employer> getEmployerRecords(int id) {

		Employer returnEmployer = null;
		for (Employer employer : employers) {
			if (Employer.getEmployerId() == id) {
				returnEmployer = employer;
			}
		}
		return returnEmployer;
	}

	public Collection<Applicant> getApplicantRecords(int id) {
		Applicant returnApplicant = null;
		for (Applicant applicant : Applicants) {
			if (Applicant.getApplicantId() == id) {
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
