package Delfinen;

import Delfinen.Enums.MembershipStatus;
import Delfinen.Enums.MembershipType;


public abstract class Member {
  private MembershipType type;
  private MembershipStatus status;
  private final MasterData masterData;

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

  public MasterData getMasterData() {
    return masterData;
  }

  public Member(MasterData masterData, MembershipType type, MembershipStatus status) {
    this.masterData = masterData;
    this.type = type;
    this.status = status;
  }

  public DTO convertToDTO() {
    return new DTO(this.masterData,this.type,this.status);
  }
}
