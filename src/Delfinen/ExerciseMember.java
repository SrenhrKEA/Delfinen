package Delfinen;

import Delfinen.Enums.Gender;
import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

public class ExerciseMember extends Member {

  public ExerciseMember(int age, String name, String address, String email, String telephone, String dateRegistration, String id, Gender gender, MembershipType type, MembershipStatus status) {
    super(age, name, address, email, telephone, dateRegistration, id, gender, type, status);
  }

  @Override
  public DTO convertToDTO() {
    return new DTO(super.getAge(),super.getName(),super.getDateRegistration(),super.getId(),super.getEmail(),super.getTelephone(),super.getAddress(),super.getGender(),super.getType(),super.getStatus());
  }
}
