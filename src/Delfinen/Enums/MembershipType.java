package Delfinen.Enums;

public enum MembershipType {
  EXERCISE("Exercise"),
  COMPETITIVE("Competitive");

  private final String displayName;

  MembershipType(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
