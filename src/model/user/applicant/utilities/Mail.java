package model.user.applicant.utilities;

import model.user.employer.Employer;

public class Mail
{
    private Employer employer;
    private String title;
    private String message;

    public Mail(Employer employer, String title, String message)
    {
        this.employer = employer;
        this.title = title;
        this.message = message;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return message;
    }

    public Employer getEmployer()
    {
        return employer;
    }
}
