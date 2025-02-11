package seedu.address.model.gameentry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.gameentry.exceptions.GameEntryNotFoundException;
import seedu.address.model.util.GameEntriesDateComparator;

public class UniqueGameEntryList implements Iterable<GameEntry> {
    private final ObservableList<GameEntry> internalList = FXCollections.observableArrayList();
    private final ObservableList<GameEntry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent GameEntry as the given argument.
     */
    public boolean contains(GameEntry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGameEntry);
    }

    /**
     * Adds a GameEntry to the list.
     * The GameEntry must not already exist in the list.
     */
    public void add(GameEntry toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        internalList.sort(new GameEntriesDateComparator().reversed());
    }

    /**
     * Replaces the game entry  {@code target} in the list with {@code editedGameEntry}.
     * {@code target} must exist in the list.
     */
    public void setGameEntry(GameEntry target, GameEntry editedGameEntry) {
        requireAllNonNull(target, editedGameEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GameEntryNotFoundException();
        }

        internalList.set(index, editedGameEntry);
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

    public void setGameEntries(UniqueGameEntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code gameEntries}.
     * {@code gameEntries} must not contain duplicate gameEntries.
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGameEntryList // instanceof handles nulls
                && internalList.equals(((UniqueGameEntryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code gameEntries} contains only unique game entries.
     */
    private boolean gameEntriesAreUnique(List<GameEntry> gameEntries) {
        for (int i = 0; i < gameEntries.size() - 1; i++) {
            for (int j = i + 1; j < gameEntries.size(); j++) {
                if (gameEntries.get(i).isSameGameEntry(gameEntries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
