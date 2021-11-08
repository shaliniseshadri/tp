package seedu.gamebook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.commons.util.StringUtil;
import seedu.gamebook.model.gameentry.Amount;
import seedu.gamebook.model.gameentry.DatePlayed;
import seedu.gamebook.model.gameentry.Duration;
import seedu.gamebook.model.gameentry.EndAmount;
import seedu.gamebook.model.gameentry.GameType;
import seedu.gamebook.model.gameentry.Location;
import seedu.gamebook.model.gameentry.StartAmount;
import seedu.gamebook.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_START_AMOUNT = Amount.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_END_AMOUNT = Amount.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_PROFIT = Amount.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_DATE = DatePlayed.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_DURATION = Duration.MESSAGE_CONSTRAINTS;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalArgumentException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalArgumentException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String gameType} by trimming the white spaces around it.
     *
     */

    public static GameType parseGameType(String gameType) {
        requireNonNull(gameType);
        if (!GameType.isValidGameType(gameType)) {
            throw new IllegalArgumentException(GameType.MESSAGE_CONSTRAINTS);
        }
        return new GameType(gameType);

    }

    /**
     * Parses a {@code String startAmount} into a Double.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the given {@code startAmount} is invalid.
     */
    public static StartAmount parseStartAmount(String startAmount) {
        requireNonNull(startAmount);
        if (!StartAmount.isValidAmount(startAmount)) {
            throw new IllegalArgumentException(StartAmount.MESSAGE_CONSTRAINTS);
        }
        return new StartAmount(startAmount);
    }

    /**
     * Parses a {@code String endAmount} or {@code String profit} into a Double.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the given {@code endAmount} or {@code profit} is invalid.
     */

    public static EndAmount parseEndAmount(String endAmount) {
        requireNonNull(endAmount);
        if (!EndAmount.isValidAmount(endAmount)) {
            throw new IllegalArgumentException(EndAmount.MESSAGE_CONSTRAINTS);
        }
        return new EndAmount(endAmount);
    }

    /**
     * Parses a {@code String datePlayed} into a {@code DatePlayed}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the given {@code datePlayed} is invalid.
     */
    public static DatePlayed parseDate(String datePlayed) {
        requireNonNull(datePlayed);
        if (!DatePlayed.isValidDatePlayed(datePlayed)) {
            throw new IllegalArgumentException(MESSAGE_INVALID_DATE);
        }
        return new DatePlayed(datePlayed);
    }

    /**
     * Parses a {@code String duration} into an Integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the given {@code duration} is invalid.
     */
    public static Duration parseDuration(String duration) {
        requireNonNull(duration);
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalArgumentException(Duration.MESSAGE_CONSTRAINTS);
        }
        return new Duration(duration);
    }

    /**
     * Throws IllegalValueException if fieldValue is negative.
     * @param field Name of the field to be used for generating the IllegalValueException message
     * @param fieldValue The Integer to check for
     * @throws IllegalArgumentException if fieldValue is negative
     */
    private static void requireIntegerNonNegative(String field, Integer fieldValue) {
        if (fieldValue < 0) {
            throw new IllegalArgumentException(field + " must be a non-negative integer");
        }
    }

    /**
     * Parses a {@code String location} and trim the whitespaces around it.
     *
     * @throws IllegalArgumentException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) {
        requireNonNull(location);
        if (!Location.isValidLocation(location)) {
            throw new IllegalArgumentException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(location);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalArgumentException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalArgumentException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tag : tags) {
            tagSet.add(parseTag(tag));
        }
        return tagSet;
    }

    /**
     * Parses {@code String tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(String tags) {
        requireNonNull(tags);
        return Tag.parseTagList(tags);
    }

}
