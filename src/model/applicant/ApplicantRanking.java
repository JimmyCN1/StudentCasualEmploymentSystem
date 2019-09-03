package model.applicant;

public class ApplicantRanking {
  private Applicant applicant;
  private int ranking;
  
  public ApplicantRanking(Applicant applicant, int ranking) {
    this.applicant = applicant;
    this.ranking = ranking;
  }
  
  public Applicant getApplicant() {
    return applicant;
  }
  
  public int getRanking() {
    return ranking;
  }
  
  public void setRanking(int ranking) {
    this.ranking = ranking;
  }
}
