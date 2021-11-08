package seedu.gamebook.model.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.gamebook.model.gameentry.GameEntry;

public class AverageTest {
    private static final String DATE_ONE = "2021-01-11";
    private static final String DATE_TWO = "2021-01-15";
    private static final String DATE_THREE = "2021-02-22";
    private static final String DATE_FOUR = "2021-06-30";

    private static final String TIME_ONE = " 03:05";
    private static final String TIME_TWO = " 06:43";
    private static final String TIME_THREE = " 12:16";
    private static final String TIME_FOUR = " 16:35";

    private GameEntry gameOnDateOneNoTime = new GameEntry("Blackjack", "100",
            "80", DATE_ONE, "10", "Marina bay sands", "");
    private GameEntry gameOnDateOneTimeOne = new GameEntry("Poker", "0", "50",
            DATE_ONE + TIME_ONE, "20", "Sentosa", "");
    private GameEntry gameOnDateOneTimeTwo = new GameEntry("Blackjack", "1000",
            "1000.25", DATE_ONE + TIME_TWO, "105", "Marina bay sands", "");
    private GameEntry gameOnDateTwoTimeOne = new GameEntry("Blackjack", "0.00",
            "15.25", DATE_TWO + TIME_ONE, "10", "Marina bay sands", "");

    @Test
    public void getAverageData_emptyList_returnsEmptyTreeMap() {
        List<GameEntry> emptyGameEntryList = new ArrayList<>();
        TreeMap<String, Double> actualTreeMap = Average.getAverageData(emptyGameEntryList);
        TreeMap<String, Double> expectedTreeMap = new TreeMap<>();
        assertEquals(expectedTreeMap, actualTreeMap);
    }

    @Test
    public void getAverageData_multipleDatesAndMultipleEntriesOnADate_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        GameEntry[] gameEntriesToAdd = { gameOnDateOneNoTime, gameOnDateOneTimeOne, gameOnDateOneTimeTwo,
            gameOnDateTwoTimeOne };
        for (GameEntry gameEntry : gameEntriesToAdd) {
            gameEntryList.add(gameEntry);
        }
        TreeMap<String, Double> actualTreeMap = Average.getAverageData(gameEntryList);

        TreeMap<String, Double> expectedTreeMap = new TreeMap<>();
        expectedTreeMap.put(DATE_ONE, 30.25 / 3);
        expectedTreeMap.put(DATE_TWO, 15.25);

        assertEquals(expectedTreeMap, actualTreeMap);
    }

    @Test
    public void getOverallAverage_emptyList_returnsZero() {
        List<GameEntry> emptyGameEntryList = new ArrayList<>();
        assertEquals(0.00, Average.getOverallAverage(emptyGameEntryList));
    }

    @Test
    public void getOverallAverage_multipleEntries_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        GameEntry[] gameEntriesToAdd = {gameOnDateOneNoTime, gameOnDateOneTimeOne, gameOnDateOneTimeTwo,
            gameOnDateTwoTimeOne};
        for (GameEntry gameEntry : gameEntriesToAdd) {
            gameEntryList.add(gameEntry);
        }
        Double averageComputed = Average.getOverallAverage(gameEntryList);

        Double expectedAverage = 45.5 / 4;

        assertEquals(expectedAverage, averageComputed);
    }
}
