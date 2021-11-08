package seedu.gamebook.model.gameentry;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.commons.util.AppUtil.checkArgument;

public class Location {
    public static final String MESSAGE_CONSTRAINTS = "All strings are valid locations";
    private static final String EMPTY_LOCATION = "";
    private static final Location EMPTY = new Location();
    private final String location;

    /**
     * Constructs Location.
     *
     * @param location
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        if (!location.equals(EMPTY_LOCATION)) {
            String[] tmp = location.strip().split(" ");

            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = fixCases(tmp[i]);
            }
            this.location = String.join(" ", tmp);
        } else {
            this.location = EMPTY_LOCATION;
        }
    }

    /**
     * Private constructor for empty location.
     */
    private Location() {
        location = "";
    }

    public static Location empty() {
        return EMPTY;
    }

    private static String fixCases(String str) {
        if (str.length() == 0) {
            return str;
        }
        String firstChar = str.substring(0, 1).toUpperCase();
        String trailingChars = str.substring(1).toLowerCase();
        return firstChar + trailingChars;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    /**
     * Checks whether input string is valid location.
     *
     * @param location
     * @return Always true.
     */
    public static boolean isValidLocation(String location) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Location) {
            Location tmp = (Location) other;
            return location.equals(tmp.location);
        }
        return false;
    }

    @Override
    public String toString() {
        return location;
    }

    /**
     * Formats it the way it was received for testing purposes.
     */
    public String toCommandString() {
        return toString();
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
