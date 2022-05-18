package Delfinen.Comparators;

import Delfinen.Member;

import java.util.Comparator;

public class MemberIdComparator implements Comparator<Member> {
  @Override
  public int compare(Member o1, Member o2) {
    return o1.getId().compareTo(o2.getId());
  }
}
