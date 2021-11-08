package seedu.gamebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.gameentry.GameEntry;


/**
 * Deletes a game entry identified using it's displayed index from the game book.
 */
public class DeleteCommand extends Command {

    public static final DeleteCommand DUMMY = new DeleteCommand();
    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_SPECIFICATION = "INDEX must be a positive integer and cannot be bigger than the "
        + "number of entries in the displayed game list.";
    public static final String COMMAND_EXAMPLE = "Assume that there is at least one game entry in GameBook now.\n"
        + COMMAND_WORD + " 1";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " INDEX";
    public static final String COMMAND_SUMMARY = "Deletes the game entry identified by the given index number. (Index "
        + "number is obtained from the displayed game list.)\n\n"
        + "Format: "
        + COMMAND_FORMAT + "\n\n"
        + "Example:\n" + COMMAND_EXAMPLE;

    public static final String MESSAGE_USAGE = COMMAND_FORMAT + "\n" + COMMAND_SPECIFICATION;

    public static final String MESSAGE_DELETE_GAME_ENTRY_SUCCESS = "Deleted game entry: \n%1$s";
    public static final String COMMAND_NOT_ACCEPTED_WITHOUT_GAME_LIST = "This command can only be used when a game list"
        + " is shown. Please use \"list\" to show all game entries.";
    public static final String MESSAGE_FAILURE_WITHOUT_GAME_LIST = COMMAND_FORMAT + "\n"
        + COMMAND_NOT_ACCEPTED_WITHOUT_GAME_LIST;

    private final Index targetIndex;

    private DeleteCommand() {
        targetIndex = null;
    }

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GameEntry> lastShownList = model.getFilteredGameEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        GameEntry gameEntryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteGameEntry(gameEntryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_GAME_ENTRY_SUCCESS, gameEntryToDelete));
    }

    @Override
    public String getCommandWord() {
        return DeleteCommand.COMMAND_WORD;
    }

    @Override
    public String getCommandSummary() {
        return DeleteCommand.COMMAND_SUMMARY;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
