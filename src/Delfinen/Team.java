package Delfinen;

import java.util.ArrayList;

public class Team {
  private String name;
  private Integer minAge;
  private Integer maxAge;
  private ArrayList<Member> members;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getMinAge() {
    return minAge;
  }

  public void setMinAge(Integer minAge) {
    this.minAge = minAge;
  }

  public Integer getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(Integer maxAge) {
    this.maxAge = maxAge;
  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public Team(String name, Integer minAge, Integer maxAge, ArrayList<Member> members) {
    this.name = name;
    this.minAge = minAge;
    this.maxAge = maxAge;
    this.members = members;
  }
}
