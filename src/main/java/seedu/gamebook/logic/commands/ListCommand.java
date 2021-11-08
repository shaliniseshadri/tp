package seedu.gamebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.model.Model.PREDICATE_SHOW_ALL_GAME_ENTRIES;

import seedu.gamebook.model.Model;

/**
 * Lists all game entries in the game book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all games";
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_FORMAT;
    public static final String COMMAND_SUMMARY = "Lists all game entries.\n\nFormat:\n" + COMMAND_FORMAT;
    public static final ListCommand DUMMY = new ListCommand();

    @Override
    public String getCommandWord() {
        return ListCommand.COMMAND_WORD;
    }

    @Override
    public String getCommandSummary() {
        return ListCommand.COMMAND_SUMMARY;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGameEntryList(PREDICATE_SHOW_ALL_GAME_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
