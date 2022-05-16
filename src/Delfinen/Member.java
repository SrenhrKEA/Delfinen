package Delfinen;

import java.util.ArrayList;

public class Member {
  //For all swimmers
  private int age;
  private String name;
  private String dateRegistration;
  private String id;
  private String email;
  private String phone;
  //private String dateWithdrawal;
  private boolean genderMale;
  //private boolean membershipActive;
  //private boolean membershipJunior;
  //private boolean membershipCompetitive;



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

/*
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

   */

  public Member(int age, String name, String dateRegistration, String id, boolean genderMale) {
    this.age = age;
    this.name = name;
    this.dateRegistration = dateRegistration;
    this.id = id;
    this.genderMale = genderMale;
  }

  @Override
  public String toString() {
    return "Member{" +
        "age=" + age +
        ", name='" + name + '\'' +
        ", dateRegistration='" + dateRegistration + '\'' +
        ", ID='" + id + '\'' +
        ", genderMale=" + genderMale +
        '}';
  }
}
