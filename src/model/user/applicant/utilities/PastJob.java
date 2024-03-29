package model.user.applicant.utilities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PastJob implements Serializable {
  private static final long serialVersionUID = 11L;

  private String company;
  private String title;
  private LocalDate beginDate;
  private LocalDate endDate;
  private List<String> responsibilities;
  
  public PastJob(String company, String title, LocalDate beginDate, LocalDate endDate, List<String> responsibilities) {
    this.company = company;
    this.title = title;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.responsibilities = responsibilities;
  }
  
  public String getCompany() {
    return company;
  }
  
  public String getTitle() {
    return title;
  }
  
  public LocalDate getBeginDate() {
    return beginDate;
  }
  
  public LocalDate getEndDate() {
    return endDate;
  }
  
  public List<String> getResponsibilities() {
    return responsibilities;
  }
  
  public void setCompany(String company) {
    this.company = company;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public void setBeginDate(LocalDate beginDate) {
    this.beginDate = beginDate;
  }
  
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
  
  public void setResponsibilities(List<String> responsibilities) {
    this.responsibilities = responsibilities;
  }
  
  public void addResponsibility(String responsibility) {
    this.responsibilities.add(responsibility);
  }
  
  public String removeResponsibility(int responsibilityIndex) {
    return this.responsibilities.remove(responsibilityIndex);
  }
  
  public String toString()
  {
    String rString = "Company: " + company + "\nTitle: " + title + "\nStarted: " + beginDate.toString() + "\nEnd: " + endDate.toString() + "\nResponsibilities: ";

    for(int i = 0; i < responsibilities.size(); i++)
    {
      rString += "\n\t" + (i + 1) + ". " + responsibilities.get(i);
    }

    return rString;
  }
}
