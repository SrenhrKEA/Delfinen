package Delfinen;

import Delfinen.Enums.Discipline;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

import java.util.ArrayList;

public class DTO {
  private MasterData masterData;
  private MembershipType type;
  private MembershipStatus status;
  private ArrayList<Result> results;
  private ArrayList<Discipline> disciplines;

  public DTO() {
  }

  public DTO(MasterData masterData, MembershipType type, MembershipStatus status) {
    this.masterData = masterData;
    this.type = type;
    this.status = status;
  }

  public DTO(MasterData masterData, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    this.masterData = masterData;
    this.type = type;
    this.status = status;
    this.results = results;
    this.disciplines = disciplines;
  }

  public MembershipType getType() {
    return type;
  }

  public MembershipStatus getStatus() {
    return status;
  }

  public ArrayList<Result> getResults() {
    return results;
  }

  public ArrayList<Discipline> getDisciplines() {
    return disciplines;
  }

  public MasterData getMasterData() {
    return masterData;
  }
}
