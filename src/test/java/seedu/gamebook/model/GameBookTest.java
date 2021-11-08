package seedu.gamebook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.testutil.Assert.assertThrows;
import static seedu.gamebook.testutil.TypicalGameEntries.POKER1_WITHOUT_TIME;
import static seedu.gamebook.testutil.TypicalGameEntries.POKER1_WITH_TIME;
import static seedu.gamebook.testutil.TypicalGameEntries.getTypicalGameBook;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.testutil.GameEntryBuilder;


public class GameBookTest {
    private final GameBook gameBook = new GameBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), gameBook.getGameEntryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gameBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGameBook_replacesData() {
        GameBook newData = getTypicalGameBook();
        gameBook.resetData(newData);
        assertEquals(newData, gameBook);
    }

    @Test
    public void hasGameEntry_nullGameEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gameBook.hasGameEntry(null));
    }

    @Test
    public void hasGameEntry_gameEntryNotInGameBook_returnsFalse() {
        assertFalse(gameBook.hasGameEntry(POKER1_WITHOUT_TIME));
    }

    @Test
    public void hasGameEntry_gameEntryWithSameIdentityFieldsInGameBook_returnsTrue() {
        gameBook.addGameEntry(POKER1_WITH_TIME);

        GameEntry editedPoker = new GameEntryBuilder(POKER1_WITH_TIME).withStartAmount("1321.23").withDuration("31")
                .build();
        assertTrue(gameBook.hasGameEntry(editedPoker));
    }

    @Test
    public void addGameEntry_gameEntryWithDifferentIdentityFieldsInGameBook_addsGameEntry() {
        gameBook.addGameEntry(POKER1_WITH_TIME);

        GameEntry editedPoker = new GameEntryBuilder(POKER1_WITH_TIME)
                .withDatePlayed("2021-10-12 11:36")
                .withStartAmount("1321.12")
                .withEndAmount("0.12")
                .withLocation("school")
                .withDuration("31")
                .build();
        gameBook.addGameEntry(editedPoker);
        assertTrue(gameBook.hasGameEntry(editedPoker));
    }

    @Test
    public void getGameEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> gameBook.getGameEntryList().remove(0));
    }

    /**
     * A stub ReadOnlyGameBook whose gameEntries list can violate interface constraints.
     */
    private static class GameBookStub implements ReadOnlyGameBook {
        private final ObservableList<GameEntry> gameEntries = FXCollections.observableArrayList();

        GameBookStub(Collection<GameEntry> persons) {
            this.gameEntries.setAll(persons);
        }

        @Override
        public ObservableList<GameEntry> getGameEntryList() {
            return gameEntries;
        }
    }
}
