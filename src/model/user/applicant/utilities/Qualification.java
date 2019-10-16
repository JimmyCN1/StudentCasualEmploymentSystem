package model.user.applicant.utilities;

import java.time.LocalDate;

public class Qualification {
  private String course;
  private String orginsation;
  private LocalDate beginDate;
  private LocalDate endDate;
  
  public Qualification(String course, String organisation, LocalDate beginDate, LocalDate endDate) {
    this.course = course;
    this.orginsation = organisation;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }
  
  public String getCourse() {
    return course;
  }
  
  public String getOrganisation() {
    return orginsation;
  }
  
  public LocalDate getBeginDate() {
    return beginDate;
  }
  
  public LocalDate getEndDate() {
    return endDate;
  }
  
  public void setCourse(String course) {
    this.course = course;
  }
  
  public void setOrginsation(String orginsation) {
    this.orginsation = orginsation;
  }
  
  public void setBeginDate(LocalDate beginDate) {
    this.beginDate = beginDate;
  }
  
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String toString()
  {
    return "Course: " + course + "\nOrganisation: " + orginsation + "\nDate started: " + beginDate.toString() + "\nDate ended: " + endDate.toString();
  }
}
