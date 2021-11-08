package seedu.gamebook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.gamebook.commons.core.Config;
import seedu.gamebook.commons.core.LogsCenter;
import seedu.gamebook.commons.core.Version;
import seedu.gamebook.commons.exceptions.DataConversionException;
import seedu.gamebook.commons.util.ConfigUtil;
import seedu.gamebook.commons.util.StringUtil;
import seedu.gamebook.logic.Logic;
import seedu.gamebook.logic.LogicManager;
import seedu.gamebook.model.GameBook;
import seedu.gamebook.model.Model;
import seedu.gamebook.model.ModelManager;
import seedu.gamebook.model.ReadOnlyGameBook;
import seedu.gamebook.model.ReadOnlyUserPrefs;
import seedu.gamebook.model.UserPrefs;
import seedu.gamebook.model.util.SampleDataUtil;
import seedu.gamebook.storage.GameBookStorage;
import seedu.gamebook.storage.JsonGameBookStorage;
import seedu.gamebook.storage.JsonUserPrefsStorage;
import seedu.gamebook.storage.Storage;
import seedu.gamebook.storage.StorageManager;
import seedu.gamebook.storage.UserPrefsStorage;
import seedu.gamebook.ui.Ui;
import seedu.gamebook.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing GameBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GameBookStorage gameBookStorage = new JsonGameBookStorage(userPrefs.getGameBookFilePath());
        storage = new StorageManager(gameBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s gamebook book and {@code userPrefs}. <br>
     * The data from the sample game book will be used instead if {@code storage}'s game book is not found,
     * or an empty game book will be used instead if errors occur when reading {@code storage}'s game book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyGameBook> gameBookOptional;
        ReadOnlyGameBook initialData;
        try {
            gameBookOptional = storage.readGameBook();
            if (!gameBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GameBook");
            }
            initialData = gameBookOptional.orElseGet(SampleDataUtil::getSampleGameBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GameBook");
            initialData = new GameBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GameBook");
            initialData = new GameBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GameBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting GameBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping GameBook ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
