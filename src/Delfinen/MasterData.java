package Delfinen;

import Delfinen.Enums.Gender;

public class MasterData {
  private int age;
  private String name;
  private String id;
  private String email;
  private String telephone;
  private String address;
  private String dateRegistration;
  private Gender gender;

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

  public String getDateRegistration() {
    return dateRegistration;
  }

  public void setDateRegistration(String dateRegistration) {
    this.dateRegistration = dateRegistration;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public MasterData(int age, String name, String id, String email, String telephone, String address, String dateRegistration, Gender gender) {
    this.age = age;
    this.name = name;
    this.id = id;
    this.email = email;
    this.telephone = telephone;
    this.address = address;
    this.dateRegistration = dateRegistration;
    this.gender = gender;
  }
}

