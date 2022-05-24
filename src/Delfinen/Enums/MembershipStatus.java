package Delfinen.Enums;

public enum MembershipStatus {
  ACTIVE ("Active"),
  PASSIVE ("Passive");

  private final String displayName;

  MembershipStatus ( String displayName ) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
