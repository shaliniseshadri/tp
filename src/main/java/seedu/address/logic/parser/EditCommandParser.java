package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditGameEntryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.TokenizerException;
import seedu.address.model.gameentry.StartAmount;
import seedu.address.model.tag.Tag;

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
                    ArgumentTokenizer.tokenize(args, PREFIX_GAMETYPE, PREFIX_STARTAMOUNT, PREFIX_ENDAMOUNT, PREFIX_DATE,
                            PREFIX_DURATION, PREFIX_LOCATION, PREFIX_TAG);
        } catch (TokenizerException te) {
            // TODO - add warning
            throw new ParseException(te.getMessage());
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditGameEntryDescriptor editGameEntryDescriptor = new EditGameEntryDescriptor();
        setEditGameEntryDescriptor(argMultimap, editGameEntryDescriptor);

        if (!editGameEntryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editGameEntryDescriptor);
    }

    private void setEditGameEntryDescriptor(ArgumentMultimap argMultimap,
                                            EditGameEntryDescriptor editGameEntryDescriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_GAMETYPE).isPresent()) {
            editGameEntryDescriptor.setGameType(
                    ParserUtil.parseGameType(argMultimap.getValue(PREFIX_GAMETYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_STARTAMOUNT).isPresent()) {
            editGameEntryDescriptor
                    .setStartAmount(ParserUtil.parseStartAmount(argMultimap.getValue(PREFIX_STARTAMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_ENDAMOUNT).isPresent()) {
            editGameEntryDescriptor
                    .setEndAmount(ParserUtil.parseEndAmount(argMultimap.getValue(PREFIX_ENDAMOUNT).get(), ""));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editGameEntryDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editGameEntryDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            editGameEntryDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(PREFIX_PROFIT).isPresent()) {
            editGameEntryDescriptor
                .setEndAmount(ParserUtil.parseEndAmount(argMultimap.getValue(PREFIX_PROFIT).get(), ""));
            editGameEntryDescriptor
                .setStartAmount(new StartAmount("0"));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editGameEntryDescriptor::setTags);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;
        assert tags.size() <= 1; // In our implementation, we only allow 1 flag with multiple tags

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
