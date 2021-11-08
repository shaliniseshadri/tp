package seedu.gamebook.model.util;

import static seedu.gamebook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.gamebook.model.gameentry.GameEntry;

/**
 * A comparator to compare game entries by date. A game entry with an earlier date is deemed smaller.
 */
public class GameEntriesDateComparator implements Comparator<GameEntry> {
    @Override
    public int compare(GameEntry gameOne, GameEntry gameTwo) {
        requireAllNonNull(gameOne, gameTwo);
        requireAllNonNull(gameOne.getDate(), gameTwo.getDate());
        return gameOne.compareTo(gameTwo);
    }
}

