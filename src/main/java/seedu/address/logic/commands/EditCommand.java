package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GAME_ENTRIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import seedu.address.model.gameentry.DatePlayed;
import seedu.address.model.gameentry.GameEntry;
import seedu.address.model.gameentry.GameType;
import seedu.address.model.gameentry.Location;

/**
 * Edits the details of an existing game entry in the game book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the game entry identified "
            + "by the index number used in the displayed games list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_GAMETYPE + "GAMETYPE] "
            + "[" + PREFIX_STARTAMOUNT + "START AMOUNT] "
            + "[" + PREFIX_ENDAMOUNT + "END AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_LOCATION + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GAMETYPE + "poker "
            + PREFIX_ENDAMOUNT + "150";

    public static final String MESSAGE_EDIT_GAME_SUCCESS = "Edited Game Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GAME = "This game already exists in the game book.";

    private final Index index;
    private final EditGameEntryDescriptor editGameEntryDescriptor;

    /**
     * @param index of the game in the filtered game list to edit
     * @param editGameDescriptor details to edit the game with
     */
    public EditCommand(Index index, EditGameEntryDescriptor editGameDescriptor) {
        requireNonNull(index);
        requireNonNull(editGameDescriptor);

        this.index = index;
        this.editGameEntryDescriptor = new EditGameEntryDescriptor(editGameDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GameEntry> lastShownList = model.getFilteredGameEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GAME_DISPLAYED_INDEX);
        }

        GameEntry gameEntryToEdit = lastShownList.get(index.getZeroBased());
        GameEntry editedGameEntry = createEditedGameEntry(gameEntryToEdit, editGameEntryDescriptor);

        // kiv for change
        if (!gameEntryToEdit.isSameGameEntry(editedGameEntry) && model.hasGameEntry(editedGameEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_GAME);
        }

        model.setGameEntry(gameEntryToEdit, editedGameEntry);

        // edit "PERSONS"?
        model.updateFilteredGameEntryList(PREDICATE_SHOW_ALL_GAME_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_GAME_SUCCESS, editedGameEntry));
    }

    /**
     * Creates and returns a {@code GameEntry} with the details of {@code gameEntryToEdit}
     * edited with {@code editGameEntryDescriptor}.
     */
    private static GameEntry createEditedGameEntry(GameEntry gameEntryToEdit,
            EditGameEntryDescriptor editGameEntryDescriptor) {
        assert gameEntryToEdit != null;

        GameType updatedGameType = editGameEntryDescriptor.getGameType().orElse(new GameType(gameEntryToEdit.getGameType()));
        Double updatedStartAmount = editGameEntryDescriptor.getStartAmount().orElse(gameEntryToEdit.getStartAmount());
        Double updatedEndAmount = editGameEntryDescriptor.getEndAmount().orElse(gameEntryToEdit.getEndAmount());
        DatePlayed date = editGameEntryDescriptor.getDate().orElse(gameEntryToEdit.getDate());
        Integer updatedDuration = editGameEntryDescriptor.getDuration().orElse(gameEntryToEdit.getDurationMinutes());
        Location updatedLocation = editGameEntryDescriptor.getLocation().orElse(new Location(gameEntryToEdit.getLocation()));
        Set<Tag> updatedTags = editGameEntryDescriptor.getTags().orElse(gameEntryToEdit.getTags());

        return new GameEntry(updatedGameType.toString(), updatedStartAmount, updatedEndAmount, date,
                updatedDuration, updatedLocation.toString(), updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editGameEntryDescriptor.equals(e.editGameEntryDescriptor);
    }

    /**
     * Stores the details to edit the game entry with. Each non-empty field value will replace the
     * corresponding field value of the game entry.
     */
    public static class EditGameEntryDescriptor {
        private GameType gameType;
        private Double startAmount;
        private Double endAmount;
        private DatePlayed date;
        private Integer durationMinutes;
        private Location location;
        private Set<Tag> tags;

        public EditGameEntryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGameEntryDescriptor(EditGameEntryDescriptor toCopy) {
            setGameType(toCopy.gameType);
            setStartAmount(toCopy.startAmount);
            setEndAmount(toCopy.endAmount);
            setDate(toCopy.date);
            setDuration(toCopy.durationMinutes);
            setLocation(toCopy.location);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(gameType, startAmount, endAmount, date, durationMinutes, location, tags);
        }

        public void setGameType(GameType gameType) {
            this.gameType = gameType;
        }

        public Optional<GameType> getGameType() {
            return Optional.ofNullable(gameType);
        }

        public void setStartAmount(Double startAmount) {
            this.startAmount = startAmount;
        }

        public Optional<Double> getStartAmount() {
            return Optional.ofNullable(startAmount);
        }

        public void setEndAmount(Double endAmount) {
            this.endAmount = endAmount;
        }

        public Optional<Double> getEndAmount() {
            return Optional.ofNullable(endAmount);
        }

        public void setDate(DatePlayed date) {
            this.date = date;
        }

        public Optional<DatePlayed> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDuration(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
        }

        public Optional<Integer> getDuration() {
            return Optional.ofNullable(durationMinutes);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGameEntryDescriptor)) {
                return false;
            }

            // state check
            EditGameEntryDescriptor e = (EditGameEntryDescriptor) other;

            // assume different game entries must be unique in their fields
            return getGameType().equals(e.getGameType())
                    && getStartAmount().equals(e.getStartAmount())
                    && getEndAmount().equals(e.getEndAmount())
                    && getDate().equals(e.getDate())
                    && getDuration().equals(e.getDuration())
                    && getLocation().equals(e.getLocation())
                    && getTags().equals(e.getTags());
        }
    }
}
