package Delfinen.Comparators;

import Delfinen.Result;

import java.util.Comparator;

public class ResultTimeComparator implements Comparator<Result> {
  @Override
  public int compare(Result o1, Result o2) {
    return Double.compare(o1.getTimeInSeconds(), o2.getTimeInSeconds());
  }
}
