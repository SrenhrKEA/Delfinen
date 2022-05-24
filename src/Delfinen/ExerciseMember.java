package Delfinen;

import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;

public class ExerciseMember extends Member {

  public ExerciseMember(MasterData masterData, MembershipType type, MembershipStatus status) {
    super(masterData, type, status);
  }

  @Override
  public DTO convertToDTO() {
    return new DTO(super.getMasterData(),super.getType(),super.getStatus());
  }
}
