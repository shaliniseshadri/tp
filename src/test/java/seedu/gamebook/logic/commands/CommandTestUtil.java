package seedu.gamebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.model.GameBook;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.gameentry.DatePlayed;
import seedu.gamebook.model.gameentry.Duration;
import seedu.gamebook.model.gameentry.EndAmount;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.model.gameentry.GameType;
import seedu.gamebook.model.gameentry.Location;
import seedu.gamebook.model.gameentry.StartAmount;
import seedu.gamebook.model.tag.Tag;
import seedu.gamebook.testutil.EditGameEntryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // TODO - put these into a common util class
    public static final GameType VALID_GAMETYPE_1 = new GameType("Poker");
    public static final StartAmount VALID_STARTAMOUNT_1 = new StartAmount("0.0");
    public static final EndAmount VALID_ENDAMOUNT_1 = new EndAmount("100.0");
    public static final DatePlayed VALID_DATE_1 = new DatePlayed("2021-01-01 10:00");
    public static final Duration VALID_DURATION_1 = new Duration("10");
    public static final Location VALID_LOCATION_1 = new Location("Sentosa");
    public static final String VALID_TAG_1 = "lucky";
    public static final Set<Tag> VALID_TAGSET_1 = Tag.parseTagList(VALID_TAG_1);

    public static final GameType VALID_GAMETYPE_2 = new GameType("Black Jack");
    public static final StartAmount VALID_STARTAMOUNT_2 = new StartAmount("10.0");
    public static final EndAmount VALID_ENDAMOUNT_2 = new EndAmount("200.0");
    public static final DatePlayed VALID_DATE_2 = new DatePlayed("2021-10-10");
    public static final Duration VALID_DURATION_2 = new Duration("20");
    public static final Location VALID_LOCATION_2 = new Location("Marina Bay");
    public static final String VALID_TAG_2 = "drunk";
    public static final Set<Tag> VALID_TAGSET_2 = Tag.parseTagList(VALID_TAG_2);

    public static final EditCommand.EditGameEntryDescriptor GAME_ONE = new EditGameEntryDescriptorBuilder()
            .withGameType(VALID_GAMETYPE_1).withProfit(VALID_ENDAMOUNT_1.minus(VALID_STARTAMOUNT_1))
            .withDatePlayed(VALID_DATE_1).withDuration(VALID_DURATION_1).withLocation(VALID_LOCATION_1)
            .withTags(VALID_TAG_1).build();
    public static final EditCommand.EditGameEntryDescriptor GAME_TWO = new EditGameEntryDescriptorBuilder()
            .withGameType(VALID_GAMETYPE_2).withProfit(VALID_ENDAMOUNT_2.minus(VALID_STARTAMOUNT_2))
            .withDatePlayed(VALID_DATE_2).withDuration(VALID_DURATION_2).withLocation(VALID_LOCATION_2)
            .withTags(VALID_TAG_2).build();

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the game book, filtered game entry list and selected game entry in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        GameBook expectedGameBook = new GameBook(actualModel.getGameBook());
        List<GameEntry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredGameEntryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedGameBook, actualModel.getGameBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredGameEntryList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the game entry at the given {@code targetIndex} in the
     * {@code model}'s game book.
     */
    public static void showGameEntryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGameEntryList().size());

        GameEntry gameEntry = model.getFilteredGameEntryList().get(targetIndex.getZeroBased());

        // Use == here to compare exact object. .equals is less strict.
        model.updateFilteredGameEntryList(game -> game == gameEntry);
        assertEquals(1, model.getFilteredGameEntryList().size());
    }

}
