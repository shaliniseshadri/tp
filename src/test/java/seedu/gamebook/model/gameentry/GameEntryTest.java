package seedu.gamebook.model.gameentry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.testutil.Assert.assertThrows;

import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.gamebook.model.tag.Tag;

public class GameEntryTest {

    private static final String DATETIME_STRING = "2021-09-11 22:38";
    private static final String DATE_STRING = "2021-09-11";
    private static final GameEntry POKER_WITH_TIME = new GameEntry("Poker", "100", "80",
            DATETIME_STRING, "10", "Marina bay sands", ""
    );
    private static final GameEntry ROULETTE_WITH_TIME = new GameEntry("Roulette", "100", "80.12",
            DATETIME_STRING, "10", "Marina bay sands", ""
    );
    private static final GameEntry POKER_WITHOUT_TIME = new GameEntry("Poker", "100", "80.12",
            DATE_STRING, "10", "Marina bay sands", ""
    );
    private static final GameEntry ROULETTE_WITHOUT_TIME = new GameEntry("Roulette", "100", "80.12",
            DATE_STRING, "10", "Marina bay sands", ""
    );

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> POKER_WITH_TIME.getTags().remove(0));
    }

    @Test
    public void isSameGameEntry() {
        // same object -> returns true
        assertTrue(POKER_WITH_TIME.isSameGameEntry(POKER_WITH_TIME));

        // null -> returns false
        assertFalse(POKER_WITH_TIME.isSameGameEntry(null));

        // same gameType and date, all other attributes different -> returns true
        HashSet<Tag> tmpTags = new HashSet<>();
        tmpTags.add(new Tag("Tmp"));
        GameEntry editedPoker = new GameEntry(new GameType("Poker"), new StartAmount("101"), new EndAmount("81"),
                POKER_WITH_TIME.getDate(), new Duration(11), new Location("Resort World Sentosa"), tmpTags
        );
        assertTrue(POKER_WITH_TIME.isSameGameEntry(editedPoker));

        // different name, all other attributes same -> returns false
        editedPoker = new GameEntry(new GameType("Blackjack"), new StartAmount("100"), new EndAmount("81"),
                POKER_WITH_TIME.getDate(), new Duration(10), new Location("Marina bay sands"), new HashSet<>()
        );
        assertFalse(POKER_WITH_TIME.isSameGameEntry(editedPoker));

        // different date, all other attributes same -> returns false
        editedPoker = new GameEntry(new GameType("Poker"), new StartAmount("100"), new EndAmount("80"),
                new DatePlayed("2003-02-01 04:05"), new Duration(10), new Location("Marina bay sands"),
                new HashSet<>()
        );
        assertFalse(POKER_WITH_TIME.isSameGameEntry(editedPoker));

        // all attributes same except date without time indicated -> returns false
        assertFalse(POKER_WITH_TIME.isSameGameEntry(POKER_WITHOUT_TIME));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Date date = new Date();
        GameEntry pokerCopy = new GameEntry("Poker", "100.00", "80.00",
                DATETIME_STRING, "10", "Marina bay sands", ""
        );
        assertTrue(POKER_WITH_TIME.equals(pokerCopy));

        // same object -> returns true
        assertTrue(POKER_WITH_TIME.equals(POKER_WITH_TIME));

        // null -> returns false
        assertFalse(POKER_WITH_TIME.equals(null));

        // different type -> returns false
        assertFalse(POKER_WITH_TIME.equals(5));

        // different game entries -> returns false
        assertFalse(POKER_WITH_TIME.equals(ROULETTE_WITH_TIME));

        // different name -> returns false
        GameEntry editedPoker = new GameEntry("Blackjack", "100", "80",
                DATETIME_STRING, "10", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // different startAmount -> returns false
        editedPoker = new GameEntry("Poker", "100.10", "80.00",
                DATETIME_STRING, "10", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // different endAmount -> returns false
        editedPoker = new GameEntry("Poker", "100", "80.12",
                DATETIME_STRING, "10", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // different date -> returns false
        editedPoker = new GameEntry("Poker", "100", "80.10",
                "2003-02-01 04:05", "11", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // different duration -> returns false
        editedPoker = new GameEntry("Poker", "100", "80.10",
                DATETIME_STRING, "11", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // different tags -> returns false
        HashSet<Tag> tmpTags = new HashSet<>();
        tmpTags.add(new Tag("Tmp"));
        editedPoker = new GameEntry("Poker", "100", "80.10",
                DATETIME_STRING, "11", "Marina bay sands", ""
        );
        assertFalse(POKER_WITH_TIME.equals(editedPoker));

        // Time field indicated for one but not the other -> returns false
        assertFalse(POKER_WITH_TIME.equals(POKER_WITHOUT_TIME));
        assertFalse(POKER_WITHOUT_TIME.equals(POKER_WITH_TIME));
    }

    @Test
    public void toStringTest() {
        assertEquals(
                "Game type: Poker; Profit: -20.00; Date played: 2021-09-11 22:38; "
                        + "Game duration: 10m; Location: Marina Bay Sands",
                POKER_WITH_TIME.toString()
        );
        assertEquals(
                "Game type: Roulette; Profit: -19.88; Date played: 2021-09-11 22:38; "
                        + "Game duration: 10m; Location: Marina Bay Sands",
                ROULETTE_WITH_TIME.toString()
        );
        assertEquals(
                "Game type: Poker; Profit: -19.88; Date played: 2021-09-11; "
                        + "Game duration: 10m; Location: Marina Bay Sands",
                POKER_WITHOUT_TIME.toString()
        );
        assertEquals(
                "Game type: Roulette; Profit: -19.88; Date played: 2021-09-11; "
                        + "Game duration: 10m; Location: Marina Bay Sands",
                ROULETTE_WITHOUT_TIME.toString()
        );
    }

}
