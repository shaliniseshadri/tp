package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GAME_ENTRIES;

import seedu.address.model.Model;

/**
 * Lists all game entries in the game book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all games";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGameEntryList(PREDICATE_SHOW_ALL_GAME_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
