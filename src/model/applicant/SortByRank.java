package model.applicant;

import java.util.Comparator;

public class SortByRank implements Comparator<ApplicantRanking> {
  @Override
  public int compare(ApplicantRanking a1, ApplicantRanking a2) {
    return a2.getRanking() - a1.getRanking();
  }
}
