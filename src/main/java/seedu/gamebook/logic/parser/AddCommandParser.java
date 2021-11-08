package seedu.gamebook.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_PROFIT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.gamebook.logic.commands.AddCommand;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.logic.parser.exceptions.TokenizerException;
import seedu.gamebook.model.gameentry.DatePlayed;
import seedu.gamebook.model.gameentry.Duration;
import seedu.gamebook.model.gameentry.EndAmount;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.model.gameentry.GameType;
import seedu.gamebook.model.gameentry.Location;
import seedu.gamebook.model.gameentry.StartAmount;
import seedu.gamebook.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap;
        GameEntry gameEntry;

        try {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_GAMETYPE, PREFIX_STARTAMOUNT, PREFIX_ENDAMOUNT, PREFIX_DATE,
                            PREFIX_PROFIT, PREFIX_DURATION, PREFIX_LOCATION, PREFIX_TAG);

        } catch (TokenizerException te) {
            throw new ParseException(te.getMessage());
        }
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            GameType gameType = argMultimap.getValue(PREFIX_GAMETYPE)
                    .map(ParserUtil::parseGameType)
                    .orElse(GameType.empty());


            StartAmount startAmount;
            EndAmount endAmount;
            boolean hasStart = argMultimap.getValue(PREFIX_STARTAMOUNT).isPresent();
            boolean hasEnd = argMultimap.getValue(PREFIX_ENDAMOUNT).isPresent();
            boolean hasProfit = argMultimap.getValue(PREFIX_PROFIT).isPresent();
            if (hasStart && hasEnd && !hasProfit) {
                startAmount = argMultimap.getValue(PREFIX_STARTAMOUNT)
                        .map(ParserUtil::parseStartAmount)
                        .get();
                endAmount = argMultimap.getValue(PREFIX_ENDAMOUNT)
                        .map(ParserUtil::parseEndAmount)
                        .get();
            } else if (!hasStart && !hasEnd && hasProfit) {
                startAmount = new StartAmount("0");
                endAmount = argMultimap.getValue(PREFIX_PROFIT)
                        .map(ParserUtil::parseEndAmount)
                        .get();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            DatePlayed date = argMultimap.getValue(PREFIX_DATE)
                    .map(ParserUtil::parseDate)
                    .orElse(new DatePlayed());

            Duration durationMinutes = argMultimap.getValue(PREFIX_DURATION)
                    .map(ParserUtil::parseDuration)
                    .orElse(Duration.empty());

            Location location = argMultimap.getValue(PREFIX_LOCATION)
                    .map(ParserUtil::parseLocation)
                    .orElse(Location.empty());

            Set<Tag> tagList = argMultimap.getValue(PREFIX_TAG)
                    .map(ParserUtil::parseTags)
                    .orElse(Tag.empty());

            gameEntry = new GameEntry(gameType, startAmount, endAmount, date, durationMinutes, location, tagList);

        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        return new AddCommand(gameEntry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
