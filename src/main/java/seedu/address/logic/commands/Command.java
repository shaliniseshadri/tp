package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private static final List<Command> COMMANDS = new ArrayList<Command>(
        Arrays.asList(AddCommand.DUMMY, EditCommand.DUMMY, DeleteCommand.DUMMY, HelpCommand.DUMMY, ClearCommand.DUMMY,
            ExitCommand.DUMMY)
    );
    public static final ObservableList<Command> COMMAND_OBSERVABLE_LIST = FXCollections.observableArrayList(COMMANDS);




    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract String getCommandWord();
    public abstract String getCommandUsage();
}
