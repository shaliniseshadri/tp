package seedu.gamebook.logic.parser;

import static seedu.gamebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gamebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.gamebook.logic.commands.AddCommand;
import seedu.gamebook.logic.commands.ClearCommand;
import seedu.gamebook.logic.commands.Command;
import seedu.gamebook.logic.commands.DeleteCommand;
import seedu.gamebook.logic.commands.EditCommand;
import seedu.gamebook.logic.commands.ExitCommand;
import seedu.gamebook.logic.commands.FindCommand;
import seedu.gamebook.logic.commands.HelpCommand;
import seedu.gamebook.logic.commands.ListCommand;
import seedu.gamebook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class GameBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.INVALID_COMMAND_MESSAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into command for execution after checking constraints (in this case, if game entry list is
     * visible).
     *
     * @param userInput              full user input string
     * @param isGameEntryListVisible boolean that shows whether the game list is currently displayed.
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, boolean isGameEntryListVisible) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.INVALID_COMMAND_MESSAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        //These commands are not accepted when game list is not displayed.
        if (!isGameEntryListVisible) {
            if (commandWord.equals(EditCommand.COMMAND_WORD)) {
                throw new ParseException(EditCommand.MESSAGE_FAILURE_WITHOUT_GAME_LIST);
            }
            if (commandWord.equals(DeleteCommand.COMMAND_WORD)) {
                throw new ParseException(DeleteCommand.MESSAGE_FAILURE_WITHOUT_GAME_LIST);
            }
        }

        return this.parseCommand(userInput);
    }

}
