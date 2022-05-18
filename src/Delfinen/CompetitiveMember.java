package Delfinen;

import Delfinen.Enums.Discipline;
import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

import java.util.ArrayList;

public class CompetitiveMember extends Member {
  //only for competitive swimmers
  //private ArrayList<Result> results;
  //private ArrayList<Discipline> disciplines;

  public CompetitiveMember(int age, String name, String address, String email, String telephone, String dateRegistration, String id, Gender gender, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    super(age, name, address, email, telephone, dateRegistration, id, gender, type, status);
    //this.results = results;
    //this.disciplines = disciplines;
  }

/*
  public ArrayList<Result> getResults() {
    return results;
  }

  public void addResult(Result result) {
    this.results.add(result);
  }
  public ArrayList<Discipline> getDisciplines() {
    return disciplines;
  }

 */
}
