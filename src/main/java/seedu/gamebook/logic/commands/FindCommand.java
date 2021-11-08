package seedu.gamebook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.gamebook.commons.core.Messages;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.gameentry.GameEntryContainsKeywordPredicate;

/**
 * Finds and lists all game entries in game book whose contents matches
 * any given argument keyword. Matches game type, location, or tags.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final FindCommand DUMMY = new FindCommand();
    public static final String COMMAND_FORMAT = COMMAND_WORD + " KEYWORDS";
    public static final String COMMAND_NOTE = "Multiple keywords are allowed. Each keyword should be separated by a "
        + "whitespace.";
    public static final String MESSAGE_USAGE = COMMAND_FORMAT + "\n" + COMMAND_NOTE;
    public static final String COMMAND_EXAMPLE = COMMAND_WORD + " friends mbs";
    public static final String COMMAND_SUMMARY = "Finds all game entries whose contents contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n\n"
        + "Format:\n"
        + COMMAND_FORMAT + "\n\n"
        + COMMAND_NOTE + "\n\n"
        + "Example:\n" + COMMAND_EXAMPLE;

    private final GameEntryContainsKeywordPredicate predicate;

    private FindCommand() {
        this.predicate = null;
    }

    public FindCommand(GameEntryContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public String getCommandWord() {
        return FindCommand.COMMAND_WORD;
    }

    @Override
    public String getCommandSummary() {
        return FindCommand.COMMAND_SUMMARY;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGameEntryList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_GAME_ENTRIES_LISTED_OVERVIEW, model.getFilteredGameEntryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
