package Delfinen;

import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;


public abstract class Member {
  //Replace attributes with MasterData object!
  private int age;
  private String name;
  private String dateRegistration;
  private String id;
  private String email;
  private String telephone;
  private String address;
  private Gender gender;

  //Keep as attributes
  private MembershipType type;
  private MembershipStatus status;

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
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


  public Member(int age, String name, String address, String email, String telephone, String dateRegistration, String id, Gender gender, MembershipType type, MembershipStatus status) {
    this.age = age;
    this.name = name;
    this.address = address;
    this.email = email;
    this.telephone = telephone;
    this.dateRegistration = dateRegistration;
    this.id = id;
    this.gender = gender;
    this.type = type;
    this.status = status;
  }

  @Override
  public String toString() {
    return "Member{" +
        "age=" + age +
        ", name='" + name + '\'' +
        ", dateRegistration='" + dateRegistration + '\'' +
        ", id='" + id + '\'' +
        ", email='" + email + '\'' +
        ", telephone='" + telephone + '\'' +
        ", address='" + address + '\'' +
        ", gender=" + gender +
        ", type=" + type +
        ", status=" + status +
        '}';
  }

  public DTO convertToDTO() {
    return new DTO(this.age,this.name,this.dateRegistration,this.id,this.email,this.telephone,this.address,this.gender,this.type,this.status);
  }
}
