package Delfinen;

import java.util.ArrayList;

public class Team {
  private String name;
  private ArrayList<Member> members = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public Team(String name, ArrayList<Member> members) {
    this.name = name;
    this.members = members;
  }
}
