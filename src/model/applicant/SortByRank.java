package model.applicant;

import java.util.Comparator;

public class SortByRank implements Comparator<ApplicantRanking> {
  @Override
  public int compare(ApplicantRanking a1, ApplicantRanking a2) {
    return a1.getRanking() - a2.getRanking();
  }
}
