package Delfinen;

import Delfinen.Enums.Discipline;
import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

import java.util.ArrayList;

public class DTO {
  private int age;
  private String name;
  private String dateRegistration;
  private String id;
  private String email;
  private String telephone;
  private String address;
  private Gender gender;
  private MembershipType type;
  private MembershipStatus status;
  private ArrayList<Result> results;
  private ArrayList<Discipline> disciplines;

  public DTO() {
  }

  public DTO(int age, String name, String dateRegistration, String id, String email, String telephone, String address, Gender gender, MembershipType type, MembershipStatus status) {
    this.age = age;
    this.name = name;
    this.dateRegistration = dateRegistration;
    this.id = id;
    this.email = email;
    this.telephone = telephone;
    this.address = address;
    this.gender = gender;
    this.type = type;
    this.status = status;
  }

  public DTO(int age, String name, String dateRegistration, String id, String email, String telephone, String address, Gender gender, MembershipType type, MembershipStatus status, ArrayList<Result> results, ArrayList<Discipline> disciplines) {
    this.age = age;
    this.name = name;
    this.dateRegistration = dateRegistration;
    this.id = id;
    this.email = email;
    this.telephone = telephone;
    this.address = address;
    this.gender = gender;
    this.type = type;
    this.status = status;
    this.results = results;
    this.disciplines = disciplines;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public String getDateRegistration() {
    return dateRegistration;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getTelephone() {
    return telephone;
  }

  public String getAddress() {
    return address;
  }

  public Gender getGender() {
    return gender;
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
}
