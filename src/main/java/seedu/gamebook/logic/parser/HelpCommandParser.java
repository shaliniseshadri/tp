package seedu.gamebook.logic.parser;

import seedu.gamebook.logic.commands.HelpCommand;
import seedu.gamebook.logic.parser.exceptions.ParseException;

public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) {
        assert args != null : "args must be a string";
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("")) {
            return new HelpCommand();
        }
        return new HelpCommand(trimmedArgs);
    }

}
