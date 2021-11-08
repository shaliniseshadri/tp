package seedu.gamebook.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.gamebook.commons.core.GuiSettings;
import seedu.gamebook.commons.core.LogsCenter;
import seedu.gamebook.logic.Logic;
import seedu.gamebook.logic.commands.CommandResult;
import seedu.gamebook.logic.commands.HelpCommand;
import seedu.gamebook.logic.commands.exceptions.CommandException;
import seedu.gamebook.logic.parser.exceptions.ParseException;
import seedu.gamebook.model.ModelManager;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private GameEntryListPanel gameEntryListPanel;
    private ResultDisplay resultDisplay;
    private GraphPanel graphPanel;
    private StatsPanel statsPanel;

    private ClearWindow clearWindow;
    private CommandNoteListPanel commandNoteListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane gameEntryListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane graphPanelPlaceholder;

    @FXML
    private StackPane statsPanelPlaceholder;

    @FXML
    private StackPane commandNoteListPanelPlaceholder;

    @FXML
    private VBox gameEntryList;

    @FXML
    private VBox commandNoteList;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        clearWindow = new ClearWindow(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initializeGameEntryListPanel();
        initializeCommandNoteListPanel();
        fillResultDisplay();
        fillStatusBarFooter();
        fillCommandBox();
        fillGraphPanel();
        fillStatsPanel();
    }

    private void initializeGameEntryListPanel() {
        gameEntryListPanel = new GameEntryListPanel(logic.getFilteredGameEntryList());
        gameEntryListPanelPlaceholder.getChildren().add(gameEntryListPanel.getRoot());
        gameEntryList.setVisible(true);
        gameEntryList.managedProperty().bind(gameEntryList.visibleProperty());
    }

    private void initializeCommandNoteListPanel() {
        commandNoteListPanel = new CommandNoteListPanel();
        commandNoteListPanelPlaceholder.getChildren().add(commandNoteListPanel.getRoot());
        commandNoteList.setVisible(false);
        commandNoteList.managedProperty().bind(commandNoteList.visibleProperty());
    }

    private void fillResultDisplay() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }

    private void fillStatusBarFooter() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getGameBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
    }

    private void fillCommandBox() {
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void fillGraphPanel() {
        graphPanel = new GraphPanel(logic.getFilteredGameEntryList());
        graphPanelPlaceholder.getChildren().add(graphPanel.getRoot());
        graphPanel.drawGraphOfLatestKDates(ModelManager.NUMBER_OF_DATES_TO_PLOT);
    }

    private void fillStatsPanel() {
        statsPanel = new StatsPanel(logic.getFilteredGameEntryList());
        statsPanelPlaceholder.getChildren().add(statsPanel.getRoot());
        statsPanel.getStats();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        gameEntryList.setVisible(false);
        commandNoteList.setVisible(true);
        resultDisplay.setFeedbackToUser(HelpCommand.SHOWING_HELP_MESSAGE);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);

        primaryStage.hide();
    }

    /**
     * Opens the clear data window or focuses on it if it's already opened.
     */
    @FXML
    private void handleClear() {
        if (!clearWindow.isShowing()) {
            clearWindow.show();
        } else {
            clearWindow.focus();
        }
        resultDisplay.setFeedbackToUser("");
    }

    /**
     * Updates the graphPanel with recent changes to GameEntryList.
     * */
    public void updateGraph() {
        graphPanel.updateGameEntryList(logic.getFilteredGameEntryList());
    }

    public GameEntryListPanel getGameEntryListPanel() {
        return gameEntryListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.gamebook.logic.Logic#execute(String, boolean)
     */
    public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            boolean b = gameEntryList.isVisible();
            CommandResult commandResult = logic.execute(commandText, gameEntryList.isVisible());
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (!commandResult.isShowHelp()) {
                gameEntryList.setVisible(true);
                commandNoteList.setVisible(false);
            }
            if (commandResult.isShowHelp()) {
                gameEntryList.setVisible(false);
                commandNoteList.setVisible(true);
            }
            if (commandResult.isExit()) {
                handleExit();
            }
            if (commandResult.isClear()) {
                handleClear();
            }

            statsPanel.updateStats(logic.getFilteredGameEntryList());
            graphPanel.updateGameEntryList(logic.getFilteredGameEntryList());
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
