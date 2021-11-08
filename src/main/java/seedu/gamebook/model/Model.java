package seedu.gamebook.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.gamebook.commons.core.GuiSettings;
import seedu.gamebook.model.gameentry.GameEntry;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<GameEntry> PREDICATE_SHOW_ALL_GAME_ENTRIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' gamebook book file path.
     */
    Path getGameBookFilePath();

    /**
     * Sets the user prefs' gamebook book file path.
     */
    void setGameBookFilePath(Path gameBookFilePath);

    /**
     * Replaces game book data with the data in {@code gameBook}.
     */
    void setGameBook(ReadOnlyGameBook gameBook);

    /** Returns the GameBook */
    ReadOnlyGameBook getGameBook();

    /**
     * Returns true if a game entry with the same identity as {@code gameEntry} exists in the game book.
     */
    boolean hasGameEntry(GameEntry gameEntry);

    /**
     * Deletes the given game entry.
     * The game entry must exist in the game book.
     */
    void deleteGameEntry(GameEntry target);

    /**
     * Adds the given game entry and sorts the list of game entries by date.
     */
    void addGameEntry(GameEntry gameEntry);

    /**
     * Replaces the given game entry {@code target} with {@code editedGameEntry} and sorts the list of game entries by
     * date.
     * {@code target} must exist in the game book.
     */
    void setGameEntry(GameEntry target, GameEntry editedGameEntry);

    /** Returns an unmodifiable view of the filtered game entry list */
    ObservableList<GameEntry> getFilteredGameEntryList();

    /**
     * Updates the filter of the filtered game entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGameEntryList(Predicate<GameEntry> predicate);
}
