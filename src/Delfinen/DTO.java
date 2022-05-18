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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public MembershipType getType() {
    return type;
  }

  public void setType(MembershipType type) {
    this.type = type;
  }

  public MembershipStatus getStatus() {
    return status;
  }

  public void setStatus(MembershipStatus status) {
    this.status = status;
  }

  public ArrayList<Result> getResults() {
    return results;
  }

  public void setResults(ArrayList<Result> results) {
    this.results = results;
  }

  public ArrayList<Discipline> getDisciplines() {
    return disciplines;
  }

  public void setDisciplines(ArrayList<Discipline> disciplines) {
    this.disciplines = disciplines;
  }
}
