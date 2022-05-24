package Delfinen.Comparators;

import Delfinen.Member;

import java.util.Comparator;

public class MemberAgeComparator implements Comparator<Member> {
  @Override
  public int compare(Member o1, Member o2) {
    return Integer.compare(o1.getMasterData().getAge(), o2.getMasterData().getAge());
  }
}
