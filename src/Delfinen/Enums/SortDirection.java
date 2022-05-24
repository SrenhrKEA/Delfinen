package Delfinen.Enums;

public enum SortDirection {
    ASC ("Ascending"),
    DESC ("Descending"),
    TOGGLE ("Toggle");

    private final String displayName;

    SortDirection ( String displayName ) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
