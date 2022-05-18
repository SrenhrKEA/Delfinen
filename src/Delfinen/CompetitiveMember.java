package Delfinen;

import Delfinen.Enums.Discipline;
import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

import java.util.ArrayList;

public class CompetitiveMember extends Member {
  //only for competitive swimmers
  private ArrayList<Result> results;
  private ArrayList<Discipline> disciplines;


  public CompetitiveMember(int age, String name, String address, String email, String telephone, String dateRegistration, String id, Gender gender, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    super(age, name, address, email, telephone, dateRegistration, id, gender, type, status);
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

  //TODO make error proof if null
  public Result getBestResult() {
    Result bestResult = null;
    //BestResultInSeconds is set to max value so that the first result is always lower
    Double bestResultInSeconds = Double.MAX_VALUE;
    for (Result result : results) {
      //Only change bestResult if the new result is lower in seconds than previous bestResult
      if (result.getTimeInSeconds() < bestResultInSeconds) {
        bestResult = result;
        bestResultInSeconds = result.getTimeInSeconds();
      }
    }
    return bestResult;
  }

}
