package Delfinen;

import java.util.ArrayList;

public class CompetitiveMember extends Member {
  //only for competitive swimmers
  private ArrayList<Result> results;
  private ArrayList<Discipline> disciplines;

  public CompetitiveMember(int age, String name, String address, String email, String telephone, String dateRegistration, String id, boolean genderMale, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    super(age, name, address, email, telephone, dateRegistration, id, genderMale);
    this.results = results;
    this.disciplines = disciplines;
  }


  public ArrayList<Result> getResults() {
    return results;
  }

  public void addResult(Result result) {
    this.results.add(result);
  }
  public ArrayList<Discipline> getDisciplines() {
    return disciplines;
  }
}
