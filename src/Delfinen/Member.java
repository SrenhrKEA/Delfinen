package Delfinen;

import java.util.ArrayList;

public class Member {
  //For all swimmers
  private int age;
  private String name;
  private String dateRegistration;
  private String id;
  //private String dateWithdrawal;
  private boolean genderMale;
  private boolean membershipActive;
  private boolean membershipJunior;
  private boolean membershipCompetitive;

  //only for competitive swimmers
  private String nameTrainer;
  private ArrayList<Result> results;
  private ArrayList<Discipline> disciplines;

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDateRegistration() {
    return dateRegistration;
  }

  public void setDateRegistration(String dateRegistration) {
    this.dateRegistration = dateRegistration;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isGenderMale() {
    return genderMale;
  }

  public void setGenderMale(boolean genderMale) {
    this.genderMale = genderMale;
  }

  public boolean isMembershipActive() {
    return membershipActive;
  }

  public void setMembershipActive(boolean membershipActive) {
    this.membershipActive = membershipActive;
  }

  public boolean isMembershipJunior() {
    return membershipJunior;
  }

  public void setMembershipJunior(boolean membershipJunior) {
    this.membershipJunior = membershipJunior;
  }

  public boolean isMembershipCompetitive() {
    return membershipCompetitive;
  }

  public void setMembershipCompetitive(boolean membershipCompetitive) {
    this.membershipCompetitive = membershipCompetitive;
  }

  public String getNameTrainer() {
    return nameTrainer;
  }

  public void setNameTrainer(String nameTrainer) {
    this.nameTrainer = nameTrainer;
  }

  public ArrayList<Result> getResults() {
    return results;
  }

  public ArrayList<Discipline> getDisciplines() {
    return disciplines;
  }

  public Member(int age, String name, String dateRegistration, String id, boolean genderMale, boolean membershipActive, boolean membershipJunior, boolean membershipCompetitive) {
    this.age = age;
    this.name = name;
    this.dateRegistration = dateRegistration;
    this.id = id;
    this.genderMale = genderMale;
    this.membershipActive = membershipActive;
    this.membershipJunior = membershipJunior;
    this.membershipCompetitive = membershipCompetitive;
  }

  @Override
  public String toString() {
    return "Member{" +
        "age=" + age +
        ", name='" + name + '\'' +
        ", dateRegistration='" + dateRegistration + '\'' +
        ", ID='" + id + '\'' +
        ", genderMale=" + genderMale +
        ", membershipActive=" + membershipActive +
        ", membershipJunior=" + membershipJunior +
        ", membershipCompetitive=" + membershipCompetitive +
        ", nameTrainer='" + nameTrainer + '\'' +
        ", results=" + results +
        ", disciplines=" + disciplines +
        '}';
  }
}
