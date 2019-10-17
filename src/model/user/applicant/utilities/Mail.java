package model.user.applicant.utilities;

import model.user.employer.Employer;

import java.io.Serializable;

public class Mail implements Serializable
{
    private static final long serialVersionUID = 13L;

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
