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

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.logic.commands.EditCommand;
import seedu.gamebook.logic.commands.EditCommand.EditGameEntryDescriptor;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.logic.parser.exceptions.TokenizerException;
import seedu.gamebook.model.gameentry.StartAmount;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = null;
        try {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_GAMETYPE, PREFIX_STARTAMOUNT, PREFIX_ENDAMOUNT,
                            PREFIX_PROFIT, PREFIX_DATE, PREFIX_DURATION, PREFIX_LOCATION, PREFIX_TAG);
        } catch (TokenizerException te) {
            // TODO - add warning
            throw new ParseException(te.getMessage());
        }

        if (argMultimap.getValue(PREFIX_STARTAMOUNT).isPresent()
            || argMultimap.getValue(PREFIX_ENDAMOUNT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index index;


        EditGameEntryDescriptor editGameEntryDescriptor = new EditGameEntryDescriptor();

        setEditGameEntryDescriptor(argMultimap, editGameEntryDescriptor);


        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), e);
        }

        return new EditCommand(index, editGameEntryDescriptor);
    }

    private void setEditGameEntryDescriptor(ArgumentMultimap argMultimap,
                                            EditGameEntryDescriptor editGameEntryDescriptor) {
        try {
            if (argMultimap.getValue(PREFIX_GAMETYPE).isPresent()) {
                editGameEntryDescriptor.setGameType(
                    ParserUtil.parseGameType(argMultimap.getValue(PREFIX_GAMETYPE).get()));
            }

            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
                editGameEntryDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            }
            if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
                editGameEntryDescriptor
                    .setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
            }
            if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
                editGameEntryDescriptor
                    .setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
            }
            if (argMultimap.getValue(PREFIX_PROFIT).isPresent()) {
                editGameEntryDescriptor
                    .setEndAmount(ParserUtil.parseEndAmount(argMultimap.getValue(PREFIX_PROFIT).get()));
                editGameEntryDescriptor
                    .setStartAmount(new StartAmount("0"));
            }
            if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
                editGameEntryDescriptor.setTags(ParserUtil.parseTags(argMultimap.getValue(PREFIX_TAG).get()));
            }
        } catch (IllegalArgumentException e) {
            editGameEntryDescriptor.setContainsInvalidParameter(true);
            editGameEntryDescriptor.setErrorMessage(e.getMessage());
        }
    }
}
