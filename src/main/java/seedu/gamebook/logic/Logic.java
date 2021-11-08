package seedu.gamebook.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.gamebook.commons.core.GuiSettings;
import seedu.gamebook.logic.commands.CommandResult;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.model.ReadOnlyGameBook;
import seedu.gamebook.model.gameentry.GameEntry;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command given the constraint {@param isGameEntryListVisible} and returns the result.
     *
     * @param commandText       The command as entered by the user.
     * @param isGameEntryListVisible boolean that is true when game list is displayed.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText, boolean isGameEntryListVisible) throws CommandException, ParseException;


    /**
     * Returns the GameBook.
     *
     * @see seedu.gamebook.model.Model#getGameBook()
     */
    ReadOnlyGameBook getGameBook();

    /**
     * Returns an unmodifiable view of the filtered list of game entries
     */
    ObservableList<GameEntry> getFilteredGameEntryList();

    /**
     * Returns the user prefs' game book file path.
     */
    Path getGameBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
