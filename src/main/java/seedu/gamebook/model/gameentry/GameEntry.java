package seedu.gamebook.model.gameentry;

import static seedu.gamebook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.gamebook.model.tag.Tag;

public class GameEntry implements Comparable<GameEntry> {
    private static final String MESSAGE_MISSING_ARGUMENTS = "GameType and EndAmount must both be present.";
    private final GameType gameType;
    private final StartAmount startAmount;
    private final EndAmount endAmount;
    private final DatePlayed date;
    private final Duration durationMinutes;
    private final Location location;
    private final Set<Tag> tags;

    /**
     * Constructs GameEntry.
     *
     * @param gameType
     * @param startAmount
     * @param endAmount
     * @param datePlayed
     * @param duration
     * @param location
     * @param tags
     */
    public GameEntry(GameType gameType, StartAmount startAmount, EndAmount endAmount, DatePlayed datePlayed,
                     Duration duration, Location location, Set<Tag> tags) {
        requireAllNonNull(gameType, startAmount, endAmount, datePlayed, duration, location, tags);
        if (gameType.isEmpty() || endAmount.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_MISSING_ARGUMENTS);
        }
        this.gameType = gameType;
        this.startAmount = startAmount;
        this.endAmount = endAmount;
        this.date = datePlayed;
        this.durationMinutes = duration;
        this.location = location;
        this.tags = tags;
    }

    /**
     * Constructs GameEntry.
     *
     * @param gameType
     * @param startAmount
     * @param endAmount
     * @param datePlayed
     * @param duration
     * @param location
     * @param tags
     */
    public GameEntry(String gameType, String startAmount, String endAmount, String datePlayed, String duration,
                     String location, String tags) {
        this.gameType = new GameType(gameType);
        this.startAmount = new StartAmount(startAmount);
        this.endAmount = new EndAmount(endAmount);
        this.date = new DatePlayed(datePlayed);
        this.durationMinutes = new Duration(duration);
        this.location = new Location(location);
        this.tags = Tag.parseTagList(tags);
    }

    /**
     * Gets the game type of this game entry.
     *
     * @return Game type.
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Gets the start amount of this game entry.
     *
     * @return Start amount.
     */
    public StartAmount getStartAmount() {
        return startAmount;
    }

    /**
     * Gets the end amount of this game entry.
     *
     * @return End amount.
     */
    public EndAmount getEndAmount() {
        return endAmount;
    }

    /**
     * Gets the date on which this game was played.
     *
     * @return Date the game was played.
     */
    public DatePlayed getDate() {
        return date;
    }

    /**
     * Gets the duration of the game. Negative values indicate that this field is
     * empty.
     *
     * @return Duration of game in minutes.
     */
    public Duration getDuration() {
        return durationMinutes;
    }

    /**
     * Gets the location where the game was played. Empty string indicates that
     * the field is empty.
     *
     * @return Location where the game was played.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean containsTag(Tag tag) {
        return tags.contains(tag);
    }

    public Double getDifference() {
        return (this.endAmount.minus(this.startAmount)).getAmount();
    }
    /**
     * Returns a boolean indicating whether there are any tags.
     */
    public boolean hasTags() {
        return !tags.isEmpty();
    }

    /**
     * Returns true if {@code otherGameEntry} is considered the same. Two game entries are considered the same if both
     * have the same game type and date.
     *
     * @param otherGameEntry Other object to compare with.
     * @return Whether the game entry is considered the same.
     */
    public boolean isSameGameEntry(GameEntry otherGameEntry) {
        if (otherGameEntry == null) {
            return false;
        }
        return equals(otherGameEntry)
                || (gameType.equals(otherGameEntry.gameType)
                        && date.equals(otherGameEntry.date));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof GameEntry) {
            GameEntry tmp = (GameEntry) other;
            return gameType.equals(tmp.gameType)
                    && getDifference().equals(tmp.getDifference())
                    && date.equals(tmp.date)
                    && durationMinutes.equals(tmp.durationMinutes)
                    && location.equals(tmp.location)
                    && tags.equals(tmp.tags);
        }
        return false;
    }

    /**
     * Compares the GameEntry with another GameEntry by date.
     *
     * @param otherGameEntry Other GameEntry to be compared to
     * @return A negative integer, zero, or a positive integer if the date of this GameEntry is earlier than, same as
     * or later than date of otherGameEntry respectively. Note that if a GameEntry does not have a time, the time will
     * be taken as 12am.
     */
    @Override
    public int compareTo(GameEntry otherGameEntry) {
        if (this == otherGameEntry || this.equals(otherGameEntry)) {
            return 0;
        } else {
            DatePlayed thisDate = this.getDate();
            DatePlayed otherDate = otherGameEntry.getDate();
            return thisDate.compareTo(otherDate);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameType, startAmount, endAmount, date, durationMinutes, location, tags);
    }

    @Override
    public String toString() {
        String output = String.format(
                "Game type: %s; Profit: %s; Date played: %s",
                gameType,
                endAmount.minus(startAmount),
                date);
        if (!durationMinutes.toString().equals("")) {
            output += "; Game duration: " + durationMinutes.toString();
        }
        if (!location.toString().equals("")) {
            output += "; Location: " + location.toString();
        }
        if (tags.size() > 0) {
            output += "; Tags: " + tags.toString();
        }
        return output;
    }

    /**
     * Compiles meaningful text together to be used as a search/find operand.
     *
     * @return The compiled text.
     */
    public String getSearchableCorpus() {
        // Maybe SLAP the tags thing to another method somewhere.
        return getGameType().toString() + " " + getLocation().toString()
                + getTags()
                .stream()
                .map(x -> x.toRawString())
                .reduce("", (x, y) -> x + " " + y);
    }

}
