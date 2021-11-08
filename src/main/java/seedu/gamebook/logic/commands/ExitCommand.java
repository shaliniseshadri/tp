package seedu.gamebook.logic.commands;

import seedu.gamebook.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final ExitCommand DUMMY = new ExitCommand();
    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Game Book as requested ...";
    public static final String COMMAND_SUMMARY = "Exits GameBook.\n\nFormat:\nexit";

    @Override
    public String getCommandWord() {
        return ExitCommand.COMMAND_WORD;
    }

    @Override
    public String getCommandSummary() {
        return ExitCommand.COMMAND_SUMMARY;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
    }

}
