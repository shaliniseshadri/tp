package seedu.gamebook.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.gamebook.model.gameentry.DatePlayed;
import seedu.gamebook.model.gameentry.Duration;
import seedu.gamebook.model.gameentry.EndAmount;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.model.gameentry.GameType;
import seedu.gamebook.model.gameentry.Location;
import seedu.gamebook.model.gameentry.StartAmount;
import seedu.gamebook.model.tag.Tag;
import seedu.gamebook.model.util.SampleDataUtil;

/**
 * A utility class to help with building GameEntry objects.
 */
public class GameEntryBuilder {


    public static final GameType DEFAULT_GAMETYPE = new GameType("Poker");
    public static final DatePlayed DEFAULT_DATE = new DatePlayed("2021-01-01 10:00");
    public static final Duration DEFAULT_DURATION = new Duration("10");
    public static final EndAmount DEFAULT_ENDAMOUNT = new EndAmount("100.0");
    public static final Location DEFAULT_LOCATION = new Location("Sentosa");
    public static final StartAmount DEFAULT_STARTAMOUNT = new StartAmount("0.0");
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private GameType gameType;
    private StartAmount startAmount;
    private EndAmount endAmount;
    private DatePlayed date;
    private Duration duration;
    private Location location;
    private Set<Tag> tags;

    /**
     * Creates a {@code GameEntryBuilder} with the default details.
     */
    public GameEntryBuilder() {
        gameType = DEFAULT_GAMETYPE;
        startAmount = DEFAULT_STARTAMOUNT;
        endAmount = DEFAULT_ENDAMOUNT;
        date = DEFAULT_DATE;
        duration = DEFAULT_DURATION;
        location = DEFAULT_LOCATION;
        tags = DEFAULT_TAGS;
    }

    /**
     * Initializes the GameEntryBuilder with the data of {@code gameEntryToCopy}.
     */
    public GameEntryBuilder(GameEntry gameEntryToCopy) {
        gameType = gameEntryToCopy.getGameType();
        startAmount = gameEntryToCopy.getStartAmount();
        endAmount = gameEntryToCopy.getEndAmount();
        date = gameEntryToCopy.getDate();
        duration = gameEntryToCopy.getDuration();
        location = gameEntryToCopy.getLocation();
        tags = new HashSet<>(gameEntryToCopy.getTags());
    }

    /**
     * Sets the {@code GameType} of the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withGameType(String gameType) {
        if (gameType.equals("")) {
            this.gameType = GameType.empty();
            return this;
        }
        String trimmedGameType = gameType.trim();
        this.gameType = new GameType(trimmedGameType);
        return this;
    }

    /**
     * Sets the start amount of the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withStartAmount(String startAmount) {
        if (startAmount.equals("")) {
            this.startAmount = StartAmount.empty();
            return this;
        }
        String trimmedStartAmount = startAmount.trim();
        Double amount;
        try {
            amount = Double.parseDouble(trimmedStartAmount);
            this.startAmount = new StartAmount(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the end amount of the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withEndAmount(String endAmount) {
        if (endAmount.equals("")) {
            this.endAmount = EndAmount.empty();
            return this;
        }
        String trimmedEndAmount = endAmount.trim();
        Double amount;
        try {
            amount = Double.parseDouble(trimmedEndAmount);
            this.endAmount = new EndAmount(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code DatePlayed} of the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withDatePlayed (String datePlayed) {
        if (datePlayed.equals("")) {
            this.date = DatePlayed.empty();
            return this;
        }
        try {
            this.date = new DatePlayed(datePlayed);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the duration of the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withDuration(String duration) {
        if (duration.equals("")) {
            this.duration = Duration.empty();
            return this;
        }
        String trimmedDuration = duration.trim();
        Integer durationInMinutes;
        try {
            durationInMinutes = Integer.parseInt(trimmedDuration);
            this.duration = new Duration(durationInMinutes);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the location of the {@code GameEntry} that we are building.
     *
     */
    public GameEntryBuilder withLocation(String location) {
        if (location.equals("")) {
            this.location = Location.empty();
            return this;
        }
        String trimmedLocation = location.trim();
        this.location = new Location(trimmedLocation);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code GameEntry} that we are building.
     */
    public GameEntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Builds a default GameEntry.
     *
     * @return Default GameEntry,
     */
    public GameEntry build() {
        return new GameEntry(gameType, startAmount, endAmount, date, duration, location, tags);
    }

}
