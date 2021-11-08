package seedu.gamebook.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.gamebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gamebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.gamebook.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.gamebook.logic.commands.AddCommand;
import seedu.gamebook.logic.commands.CommandResult;
import seedu.gamebook.logic.commands.DeleteCommand;
import seedu.gamebook.logic.commands.ListCommand;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.ModelManager;
import seedu.gamebook.model.ReadOnlyGameBook;
import seedu.gamebook.model.UserPrefs;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.storage.JsonGameBookStorage;
import seedu.gamebook.storage.JsonUserPrefsStorage;
import seedu.gamebook.storage.StorageManager;
import seedu.gamebook.testutil.GameEntryBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
    private static final String GAMETYPE_VALID_WITH_PREFIX = " " + PREFIX_GAMETYPE + "Poker";
    private static final String STARTAMOUNT_VALID_WITH_PREFIX = " " + PREFIX_STARTAMOUNT + "0.0";
    private static final String ENDAMOUNT_VALID_WITH_PREFIX = " " + PREFIX_ENDAMOUNT + "100.0";
    private static final String DATE_VALID_WITH_PREFIX = " " + PREFIX_DATE + "01/01/21 10:00";
    private static final String DURATION_VALID_WITH_PREFIX = " " + PREFIX_DURATION + "10";
    private static final String LOCATION_VALID_WITH_PREFIX = " " + PREFIX_LOCATION + "Sentosa";
    private static final String TAG_VALID_WITH_PREFIX = " " + PREFIX_TAG + "lucky";

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonGameBookStorage addressBookStorage =
                new JsonGameBookStorage(temporaryFolder.resolve("GameBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonGameBookStorage gameBookStorage =
                new JsonGameBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionGameBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(gameBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + GAMETYPE_VALID_WITH_PREFIX + STARTAMOUNT_VALID_WITH_PREFIX
                + ENDAMOUNT_VALID_WITH_PREFIX + DATE_VALID_WITH_PREFIX + DURATION_VALID_WITH_PREFIX
                + LOCATION_VALID_WITH_PREFIX;
        GameEntry expectedGameEntry = new GameEntryBuilder().withTags().build();

        ModelManager expectedModel = new ModelManager();
        expectedModel.addGameEntry(expectedGameEntry);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        // TODO fix this after changing GameEntryBuilder
        // assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredGameEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredGameEntryList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand, true);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getGameBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand, true));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonGameBookIoExceptionThrowingStub extends JsonGameBookStorage {
        private JsonGameBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveGameBook(ReadOnlyGameBook gameBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
