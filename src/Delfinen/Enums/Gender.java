package Delfinen.Enums;

public enum Gender {
  FEMALE ("Female"),
  MALE ("Male");

  private final String displayName;

  Gender ( String displayName ) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
