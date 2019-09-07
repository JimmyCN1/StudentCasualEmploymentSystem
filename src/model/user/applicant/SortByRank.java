package model.user.applicant;

import java.util.Comparator;

public class SortByRank implements Comparator<ApplicantRanking> {
  // order applicant from those with the highest ranking to those with the lowest
  @Override
  public int compare(ApplicantRanking a1, ApplicantRanking a2) {
    return a2.getRanking() - a1.getRanking();
  }
}
