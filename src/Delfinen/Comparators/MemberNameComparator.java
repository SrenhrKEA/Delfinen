package Delfinen.Comparators;

import Delfinen.Member;

import java.util.Comparator;

public class MemberNameComparator implements Comparator<Member> {
  @Override
  public int compare(Member o1, Member o2) {
    return o1.getMasterData().getName().compareTo(o2.getMasterData().getName());
  }
}
