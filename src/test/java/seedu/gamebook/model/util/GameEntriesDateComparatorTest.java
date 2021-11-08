package seedu.gamebook.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.gamebook.model.gameentry.GameEntry;

public class GameEntriesDateComparatorTest {
    private static final String DATE_ONE = "2021-01-11";
    private static final String DATE_TWO = "2021-01-15";
    private static final String DATE_THREE = "2021-02-22";
    private static final String DATE_FOUR = "2021-06-30";

    private static final String TIME_TWELVE_AM = " 00:00";
    private static final String TIME_TWO = " 06:43";
    private static final String TIME_THREE = " 12:16";
    private static final String TIME_FOUR = " 16:35";

    private GameEntriesDateComparator comparator = new GameEntriesDateComparator();

    private GameEntry gameOnDateOneNoTime = new GameEntry("Blackjack", "100",
            "80", DATE_ONE, "10", "Marina bay sands", "");
    private GameEntry gameOnDateOneTwelveAm = new GameEntry("Poker", "0", "50",
            DATE_ONE + TIME_TWELVE_AM, "20", "Sentosa", "");
    private GameEntry gameOnDateOneTimeTwo = new GameEntry("Blackjack", "1000",
            "1000.25", DATE_ONE + TIME_TWO, "105", "Marina bay sands", "");
    private GameEntry gameOnDateOneTimeThree = new GameEntry("Blackjack", "1000",
            "1000.25", DATE_ONE + TIME_THREE, "105", "Marina bay sands", "");
    private GameEntry gameOnDateTwoNoTime = new GameEntry("Blackjack", "100",
            "80", DATE_TWO, "10", "Marina bay sands", "");

    private GameEntry firstGameOnDateTwoTimeTwo = new GameEntry("Blackjack", "1000",
            "1000.25", DATE_TWO + TIME_TWO, "105", "Marina bay sands", "");
    private GameEntry secondGameOnDateTwoTimeTwo = new GameEntry("Poker", "1000",
            "1000.25", DATE_TWO + TIME_TWO, "105", "Marina bay sands", "");

    @Test
    public void compare_sameTime_returnsZero() {
        assertEquals(0, comparator.compare(firstGameOnDateTwoTimeTwo, secondGameOnDateTwoTimeTwo));
    }

    @Test
    public void compare_noTimeAndTwelveAm_returnsZero() {
        assertEquals(0, comparator.compare(gameOnDateOneNoTime, gameOnDateOneTwelveAm));
    }

    @Test
    public void compare_firstGameEarlierThanSecondGame_returnsMinusOne() {
        // Compares no time and got time
        assertEquals(-1, comparator.compare(gameOnDateOneNoTime, gameOnDateOneTimeTwo));

        assertEquals(-1, comparator.compare(gameOnDateOneTimeTwo, gameOnDateOneTimeThree));
        assertEquals(-1, comparator.compare(gameOnDateOneTimeTwo, gameOnDateTwoNoTime));
        assertEquals(-1, comparator.compare(gameOnDateOneTimeTwo, firstGameOnDateTwoTimeTwo));
    }

    @Test
    public void compare_firstGameLaterThanSecondGame_returnsOne() {
        // Compares no time and got time
        assertEquals(1, comparator.compare(gameOnDateOneTimeTwo, gameOnDateOneNoTime));

        assertEquals(1, comparator.compare(gameOnDateOneTimeThree, gameOnDateOneTimeTwo));
        assertEquals(1, comparator.compare(gameOnDateTwoNoTime, gameOnDateOneTimeTwo));
        assertEquals(1, comparator.compare(firstGameOnDateTwoTimeTwo, gameOnDateOneTimeTwo));
    }

}
