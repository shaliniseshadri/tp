package seedu.gamebook.model.gameentry;

import static java.util.Objects.requireNonNull;

public class GameType {
    public static final String MESSAGE_CONSTRAINTS = "Input cannot only contain whitespaces";
    private static final GameType EMPTY = new GameType();
    private final String gameType;

    /**
     * Private constructor for empty GameType.
     */
    private GameType() {
        gameType = "";
    }

    /**
     * Constructs GameType.
     *
     * @param gameType
     * @throws IllegalArgumentException
     */
    public GameType(String gameType) {
        requireNonNull(gameType);
        if (!isValidGameType(gameType)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        String strippedGameType = gameType.strip();
        String[] tmp = strippedGameType.split(" ");
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = fixCases(tmp[i]);
        }
        this.gameType = String.join(" ", tmp);
    }

    private static String fixCases(String str) {
        if (str.length() == 0) {
            return str;
        }
        String firstChar = str.substring(0, 1).toUpperCase();
        String trailingChars = str.substring(1).toLowerCase();
        return firstChar + trailingChars;
    }

    /**
     * Checks if given string is valid gameType.
     *
     * @param gameType
     * @return Whether input string is valid gameType.
     */
    public static boolean isValidGameType(String gameType) {
        String strippedGameType = gameType.strip();
        return strippedGameType.length() > 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof GameType) {
            GameType tmp = (GameType) other;
            return gameType.equals(tmp.gameType);
        }
        return false;
    }

    @Override
    public String toString() {
        return gameType;
    }

    /**
     * Formats it the way it was received for testing purposes.
     */
    public String toCommandString() {
        return toString();
    }

    @Override
    public int hashCode() {
        return gameType.hashCode();
    }

    public static GameType empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
