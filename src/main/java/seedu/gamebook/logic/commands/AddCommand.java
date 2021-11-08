package seedu.gamebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_PROFIT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.gamebook.model.Model;
import seedu.gamebook.model.gameentry.GameEntry;


/**
 * Adds a game to the game book.
 */
public class AddCommand extends Command {

    public static final AddCommand DUMMY = new AddCommand();

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_WITH_START_AND_END_AMOUNT_EXAMPLE = COMMAND_WORD + " "
        + PREFIX_GAMETYPE + "blackjack "
        + PREFIX_STARTAMOUNT + "200 "
        + PREFIX_ENDAMOUNT + "250 "
        + PREFIX_DATE + "2021-10-10 "
        + PREFIX_DURATION + "50 "
        + PREFIX_LOCATION + "Sentosa"
        + PREFIX_TAG + "friends";

    public static final String COMMAND_WITH_PROFIT_EXAMPLE = COMMAND_WORD + " "
        + PREFIX_GAMETYPE + "blackjack "
        + PREFIX_PROFIT + "10.0";

    public static final String COMMAND_FORMAT = String.format(
            "add %sGAME_TYPE [%sINITIAL_CASH] [%sFINAL_CASH] [%sPROFIT] [%sDATE] [%sDURATION] [%sLOCATION] [%sTAGS]",
            PREFIX_GAMETYPE, PREFIX_STARTAMOUNT, PREFIX_ENDAMOUNT, PREFIX_PROFIT, PREFIX_DATE, PREFIX_DURATION,
            PREFIX_LOCATION, PREFIX_TAG
    );

    public static final String COMMAND_SPECIFICATION = String.format("Either INITIAL_CASH and FINAL_CASH"
        + " or PROFIT must be specified.", PREFIX_STARTAMOUNT, PREFIX_ENDAMOUNT, PREFIX_PROFIT);

    public static final String COMMAND_NOTE = "Multiple tags are allowed. Each tag should be separated by a comma. "
        + "Whitespaces are not allowed within a tag. Use \"-\" instead.";
    public static final String COMMAND_SUMMARY = "Adds a game to the game book. \n\n"
        + "Format:\n"
        + COMMAND_FORMAT + "\n\n"
        + COMMAND_SPECIFICATION + "\n\n"
        + COMMAND_NOTE + "\n\n"
        + "Examples:\n"
        + COMMAND_WITH_START_AND_END_AMOUNT_EXAMPLE + "\n"
        + COMMAND_WITH_PROFIT_EXAMPLE;

    public static final String MESSAGE_USAGE = COMMAND_FORMAT + "\n" + COMMAND_SPECIFICATION;
    public static final String MESSAGE_SUCCESS = "New game added: \n%1$s\n%2$s";
    public static final String MESSAGE_DUPLICATE_GAME_ENTRY = "Alert: A game entry with the same "
        + "game type and date/datetime already exists.";
    public static final String MESSAGE_GAME_OCCURS_IN_FUTURE = "Alert: The date for this game entry is in the future.";

    public final GameEntry toAdd;

    /**
     * Creates an AddCommand dummy object.
     */
    private AddCommand() {
        toAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code GameEntry}
     */
    public AddCommand(GameEntry gameEntry) {
        requireNonNull(gameEntry);
        toAdd = gameEntry;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String sameEntryAlert = model.hasGameEntry(toAdd)
                ? MESSAGE_DUPLICATE_GAME_ENTRY
                : "";
        String inFutureAlert = toAdd.getDate().isInFuture() ? MESSAGE_GAME_OCCURS_IN_FUTURE : "";
        model.addGameEntry(toAdd);
        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                toAdd,
                Command.joinAlerts(sameEntryAlert, inFutureAlert)
        ));
    }

    @Override
    public String getCommandWord() {
        return AddCommand.COMMAND_WORD;
    }

    @Override
    public String getCommandSummary() {
        return AddCommand.COMMAND_SUMMARY;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCommand // instanceof handles nulls
            && toAdd.equals(((AddCommand) other).toAdd));
    }
}
