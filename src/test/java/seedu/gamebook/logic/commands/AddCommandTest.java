package seedu.gamebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.gamebook.commons.core.GuiSettings;
import seedu.gamebook.model.GameBook;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.ReadOnlyGameBook;
import seedu.gamebook.model.ReadOnlyUserPrefs;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.testutil.GameEntryBuilder;


public class AddCommandTest {

    @Test
    public void constructor_nullGameEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_gameEntryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGameEntryAdded modelStub = new ModelStubAcceptingGameEntryAdded();
        GameEntry validGameEntry = new GameEntryBuilder().build();

        CommandResult commandResult = new AddCommand(validGameEntry).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validGameEntry, ""), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validGameEntry), modelStub.gameEntriesAdded);
    }

    @Test
    public void execute_duplicateGameEntry_alertsUser() {
        GameEntry validGameEntry = new GameEntryBuilder().build();
        AddCommand addCommand = new AddCommand(validGameEntry);
        ModelStub modelStub = new ModelStubWithGameEntry(validGameEntry);
        CommandResult commandResult = new AddCommand(validGameEntry).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validGameEntry, AddCommand.MESSAGE_DUPLICATE_GAME_ENTRY),
                commandResult.getFeedbackToUser());
    }


    @Test
    public void equals() {
        GameEntry blackjack = new GameEntryBuilder().withGameType("Blackjack").build();
        GameEntry mahjong = new GameEntryBuilder().withGameType("Mahjong").build();
        AddCommand addBlackjackCommand = new AddCommand(blackjack);
        AddCommand addMahjongCommand = new AddCommand(mahjong);

        // same object -> returns true
        assertTrue(addBlackjackCommand.equals(addBlackjackCommand));

        // same values -> returns true
        AddCommand addBlackjackCommandCopy = new AddCommand(blackjack);
        assertTrue(addBlackjackCommand.equals(addBlackjackCommandCopy));

        // different types -> returns false
        assertFalse(addBlackjackCommand.equals(1));

        // null -> returns false
        assertFalse(addBlackjackCommand.equals(null));

        // different person -> returns false
        assertFalse(addBlackjackCommand.equals(addMahjongCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGameBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGameBookFilePath(Path gameBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGameEntry(GameEntry gameEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGameBook(ReadOnlyGameBook gameBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGameBook getGameBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGameEntry(GameEntry gameEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGameEntry(GameEntry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGameEntry(GameEntry target, GameEntry editedGameEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<GameEntry> getFilteredGameEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGameEntryList(Predicate<GameEntry> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single game entry.
     */
    private class ModelStubWithGameEntry extends ModelStub {
        private final GameEntry gameEntry;

        ModelStubWithGameEntry(GameEntry gameEntry) {
            requireNonNull(gameEntry);
            this.gameEntry = gameEntry;
        }

        @Override
        public boolean hasGameEntry(GameEntry gameEntry) {
            requireNonNull(gameEntry);
            return this.gameEntry.isSameGameEntry(gameEntry);
        }

        @Override
        public void addGameEntry(GameEntry gameEntry) {
            requireNonNull(gameEntry);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingGameEntryAdded extends ModelStub {
        final ArrayList<GameEntry> gameEntriesAdded = new ArrayList<>();

        @Override
        public boolean hasGameEntry(GameEntry gameEntry) {
            requireNonNull(gameEntry);
            return gameEntriesAdded.stream().anyMatch(gameEntry::isSameGameEntry);
        }

        @Override
        public void addGameEntry(GameEntry gameEntry) {
            requireNonNull(gameEntry);
            gameEntriesAdded.add(gameEntry);
        }

        @Override
        public ReadOnlyGameBook getGameBook() {
            return new GameBook();
        }
    }

}
