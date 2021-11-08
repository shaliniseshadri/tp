package seedu.gamebook.model.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.gamebook.model.gameentry.GameEntry;

public class MedianTest {
    // Dates and times are just precautionary measures, to ensure the list is sorted by profit, not date.
    private static final String DATE_ONE = "2021-01-11";
    private static final String DATE_TWO = "2021-01-15";
    private static final String DATE_THREE = "2021-02-22";
    private static final String DATE_FOUR = "2021-06-30";

    private static final String TIME_ONE = " 03:05";
    private static final String TIME_TWO = " 06:43";
    private static final String TIME_THREE = " 12:16";
    private static final String TIME_FOUR = " 16:35";

    // gameOne's Profit = -20
    private GameEntry gameOne = new GameEntry("Blackjack", "100",
            "80", DATE_THREE, "10", "Marina bay sands", "");
    // gameTwo's Profit = 50
    private GameEntry gameTwo = new GameEntry("Poker", "0", "50",
            DATE_ONE + TIME_ONE, "20", "Sentosa", "");
    // gameThree's Profit = 0.25
    private GameEntry gameThree = new GameEntry("Blackjack", "1000",
            "1000.25", DATE_FOUR + TIME_TWO, "105", "Marina bay sands", "");
    // gameFour's Profit = 15.25
    private GameEntry gameFour = new GameEntry("Blackjack", "0.00",
            "15.25", DATE_TWO + TIME_ONE, "10", "Marina bay sands", "");

    @Test
    public void getOverallMedian_emptyList_returnsZero() {
        List<GameEntry> emptyGameEntryList = new ArrayList<>();
        assertEquals(0.00, Median.getOverallMedian(emptyGameEntryList));
    }

    // Odd number of entries (Try 1 entry and 3 entries)
    @Test
    public void getOverallMedian_oneEntry_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        gameEntryList.add(gameOne);

        Double expectedMedian = -20.00;
        assertEquals(expectedMedian, Median.getOverallMedian(gameEntryList));
    }

    @Test
    public void getOverallMedian_threeEntries_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        gameEntryList.add(gameOne);
        gameEntryList.add(gameTwo);
        gameEntryList.add(gameThree);

        Double expectedMedian = 0.25;
        assertEquals(expectedMedian, Median.getOverallMedian(gameEntryList));
    }

    // Even number of entries (Try 2 entries and 4 entries)
    @Test
    public void getOverallMedian_twoEntries_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        gameEntryList.add(gameOne);
        gameEntryList.add(gameThree);

        Double expectedMedian = -9.875;
        assertEquals(expectedMedian, Median.getOverallMedian(gameEntryList));
    }

    @Test
    public void getOverallMedian_fourEntries_computesCorrectly() {
        List<GameEntry> gameEntryList = new ArrayList<>();
        gameEntryList.add(gameOne);
        gameEntryList.add(gameTwo);
        gameEntryList.add(gameThree);
        gameEntryList.add(gameFour);

        Double expectedMedian = 7.75;
        assertEquals(expectedMedian, Median.getOverallMedian(gameEntryList));
    }
}
