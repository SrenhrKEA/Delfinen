package Delfinen.Enums;

public enum Discipline {
  BUTTERFLY ("Butterfly"),
  CRAWL ("Crawl"),
  RYGCRAWL ("Rygcrawl"),
  BRYSTSVØMNING ("Brystsvømning" );

  private final String displayName;

  Discipline ( String displayName ) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
