package seedu.gamebook.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.gamebook.commons.core.GuiSettings;
import seedu.gamebook.commons.core.LogsCenter;
import seedu.gamebook.logic.commands.Command;
import seedu.gamebook.logic.commands.CommandResult;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.logic.parser.GameBookParser;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.ReadOnlyGameBook;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final GameBookParser gameBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        gameBookParser = new GameBookParser();
    }

    @Override
    public CommandResult execute(String commandText, boolean isGameEntryListVisible) throws CommandException,
        ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = gameBookParser.parseCommand(commandText, isGameEntryListVisible);
        commandResult = command.execute(model);

        try {
            storage.saveGameBook(model.getGameBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    @Override
    public ReadOnlyGameBook getGameBook() {
        return model.getGameBook();
    }

    @Override
    public ObservableList<GameEntry> getFilteredGameEntryList() {
        return model.getFilteredGameEntryList();
    }

    @Override
    public Path getGameBookFilePath() {
        return model.getGameBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
