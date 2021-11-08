package seedu.gamebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.logic.commands.CommandTestUtil.GAME_ONE;
import static seedu.gamebook.logic.commands.CommandTestUtil.GAME_TWO;
import static seedu.gamebook.logic.commands.CommandTestUtil.VALID_ENDAMOUNT_2;
import static seedu.gamebook.logic.commands.CommandTestUtil.VALID_GAMETYPE_2;
import static seedu.gamebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.gamebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.gamebook.logic.commands.CommandTestUtil.showGameEntryAtIndex;
import static seedu.gamebook.testutil.TypicalGameEntries.getTypicalGameBook;
import static seedu.gamebook.testutil.TypicalIndexes.INDEX_FIRST_GAMEENTRY;
import static seedu.gamebook.testutil.TypicalIndexes.INDEX_SECOND_GAMEENTRY;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.logic.commands.EditCommand.EditGameEntryDescriptor;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.model.GameBook;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.ModelManager;
import seedu.gamebook.model.UserPrefs;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.testutil.EditGameEntryDescriptorBuilder;
import seedu.gamebook.testutil.GameEntryBuilder;



/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */

public class EditCommandTest {

    private Model model = new ModelManager(getTypicalGameBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        GameEntry editedGameEntry = new GameEntryBuilder().build();
        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder(editedGameEntry).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMEENTRY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAME_SUCCESS, editedGameEntry, "");

        Model expectedModel = new ModelManager(new GameBook(model.getGameBook()), new UserPrefs());
        expectedModel.setGameEntry(model.getFilteredGameEntryList().get(0), editedGameEntry);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGameEntry = Index.fromOneBased(model.getFilteredGameEntryList().size());
        GameEntry lastGameEntry = model.getFilteredGameEntryList().get(indexLastGameEntry.getZeroBased());

        GameEntryBuilder gameEntryInList = new GameEntryBuilder(lastGameEntry);
        GameEntry editedGameEntry = gameEntryInList
                .withGameType(VALID_GAMETYPE_2.toString())
                .withEndAmount(VALID_ENDAMOUNT_2.toString())
                .build();

        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder(editedGameEntry).build();

        EditCommand editCommand = new EditCommand(indexLastGameEntry, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAME_SUCCESS, editedGameEntry, "");

        Model expectedModel = new ModelManager(new GameBook(model.getGameBook()), new UserPrefs());
        expectedModel.setGameEntry(lastGameEntry, editedGameEntry);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noChangeUnfilteredList_failure() {
        GameEntry firstGameEntry = model.getFilteredGameEntryList().get(INDEX_FIRST_GAMEENTRY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMEENTRY,
                new EditGameEntryDescriptorBuilder(firstGameEntry).build());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_FIELDS_ARE_IDENTICAL);
    }

    @Test
    public void execute_filteredList_success() {
        Predicate<GameEntry> validPredicate = game -> game.getSearchableCorpus().contains("Poker");
        model.updateFilteredGameEntryList(validPredicate);

        GameEntry gameEntryInFilteredList = model.getFilteredGameEntryList().get(0);
        GameEntry editedGameEntry = new GameEntryBuilder(gameEntryInFilteredList)
                .withGameType(VALID_GAMETYPE_2.toString()).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMEENTRY,
                new EditGameEntryDescriptorBuilder().withGameType(VALID_GAMETYPE_2).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GAME_SUCCESS, editedGameEntry, "");

        Model expectedModel = new ModelManager(new GameBook(model.getGameBook()), new UserPrefs());
        expectedModel.updateFilteredGameEntryList(validPredicate);
        expectedModel.setGameEntry(model.getFilteredGameEntryList().get(0), editedGameEntry);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateGameEntryUnfilteredList_alertsUser() {
        GameEntry firstGameEntry = model.getFilteredGameEntryList().get(INDEX_FIRST_GAMEENTRY.getZeroBased());
        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder(firstGameEntry).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_GAMEENTRY, descriptor);

        try {
            CommandResult commandResult = editCommand.execute(model);
            assertEquals(String.format(EditCommand.MESSAGE_EDIT_GAME_SUCCESS, firstGameEntry,
                    EditCommand.MESSAGE_DUPLICATE_GAME_ENTRY), commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            // Should not happen in this case
        }
    }


    @Test
    public void execute_duplicateGameEntryFilteredList_alertsUser() {
        showGameEntryAtIndex(model, INDEX_FIRST_GAMEENTRY);

        // edit game entry in filtered list into a duplicate in gamebook
        GameEntry gameEntryInList = model.getGameBook().getGameEntryList().get(INDEX_SECOND_GAMEENTRY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GAMEENTRY,
                new EditGameEntryDescriptorBuilder(gameEntryInList).build());

        try {
            CommandResult commandResult = editCommand.execute(model);
            assertEquals(String.format(EditCommand.MESSAGE_EDIT_GAME_SUCCESS, gameEntryInList,
                    EditCommand.MESSAGE_DUPLICATE_GAME_ENTRY), commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            // Should not happen in this case
        }
    }


    @Test
    public void execute_invalidGameEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGameEntryList().size() + 1);
        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder()
                .withGameType(VALID_GAMETYPE_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_USAGE);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of gamebook book
     */
    @Test
    public void execute_invalidGameEntryIndexFilteredList_failure() {
        Predicate<GameEntry> validPredicate = game -> game.getSearchableCorpus().contains("Darts");
        model.updateFilteredGameEntryList(validPredicate);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGameEntryList().size() + 1);

        // ensures that outOfBoundIndex is still in bounds of gamebook book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGameBook().getGameEntryList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditGameEntryDescriptorBuilder().withGameType(VALID_GAMETYPE_2).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_USAGE);
    }


    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_GAMEENTRY, GAME_ONE);

        // same values -> returns true
        EditGameEntryDescriptor copyDescriptor = new EditGameEntryDescriptor(GAME_ONE);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_GAMEENTRY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_GAMEENTRY, GAME_ONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_GAMEENTRY, GAME_TWO)));
    }


}
