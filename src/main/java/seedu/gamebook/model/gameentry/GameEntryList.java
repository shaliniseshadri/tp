package seedu.gamebook.model.gameentry;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.gamebook.model.gameentry.exceptions.GameEntryNotFoundException;
import seedu.gamebook.model.util.GameEntriesDateComparator;

public class GameEntryList implements Iterable<GameEntry> {
    private final ObservableList<GameEntry> internalList = FXCollections.observableArrayList();
    private final ObservableList<GameEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent GameEntry as the given argument. Game entries are considered
     * equivalent if they have the same game type and date.
     */
    public boolean contains(GameEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGameEntry);
    }

    /**
     * Adds a GameEntry to the list and sorts the list by date to ensure it remains in sorted order.
     *
     * @param toAdd GameEntry to be added.
     */
    public void add(GameEntry toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        internalList.sort(new GameEntriesDateComparator().reversed());
    }

    /**
     * Replaces the game entry  {@code target} in the list with {@code editedGameEntry}, then sorts the list by date to
     * ensure it remains in sorted order.
     * {@code target} must exist in the list.
     *
     * @param target GameEntry to be replaced
     * @param editedGameEntry The edited GameEntry object
     */
    public void setGameEntry(GameEntry target, GameEntry editedGameEntry) {
        requireAllNonNull(target, editedGameEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GameEntryNotFoundException();
        }

        internalList.set(index, editedGameEntry);
        internalList.sort(new GameEntriesDateComparator().reversed());
    }

    /**
     * Removes the equivalent GameEntry from the list.
     * The GameEntry must exist in the list.
     */
    public void remove(GameEntry toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GameEntryNotFoundException();
        }
    }

    public void setGameEntries(GameEntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code gameEntries}.
     */
    public void setGameEntries(List<GameEntry> gameEntries) {
        requireAllNonNull(gameEntries);

        internalList.setAll(gameEntries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<GameEntry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<GameEntry> iterator() {
        return internalList.iterator();
    }

    /**
     * Utility function to check each item between two iterators is equal.
     */
    private static boolean iteratorEquals(Iterator i1, Iterator i2) {
        if (i1 == i2) {
            return true;
        }
        while (i1.hasNext()) {
            if (!i2.hasNext()) {
                return false;
            }
            if (!Objects.equals(i1.next(), i2.next())) {
                return false;
            }
        }
        if (i2.hasNext()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof GameEntryList)) {
            return false;
        }

        GameEntryList other = (GameEntryList) obj;
        return iteratorEquals(this.iterator(), other.iterator());
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(this.iterator(), Spliterator.ORDERED), false)
                .map(GameEntry::toString)
                .reduce("", (x, y) -> x + "\n" + y);
    }
}
