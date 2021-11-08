package seedu.gamebook.testutil;

import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_ENDAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_GAMETYPE;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_PROFIT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_STARTAMOUNT;
import static seedu.gamebook.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import seedu.gamebook.logic.commands.AddCommand;
import seedu.gamebook.logic.commands.EditCommand.EditGameEntryDescriptor;
import seedu.gamebook.model.gameentry.EndAmount;
import seedu.gamebook.model.gameentry.GameEntry;
import seedu.gamebook.model.gameentry.StartAmount;
import seedu.gamebook.model.tag.Tag;

/**
 * A utility class for GameEntry.
 */
public class GameEntryUtil {

    private static final DateFormat DATE_FORMAT_WITH_MINUTES = new SimpleDateFormat("dd/MM/yy HH:mm");
    private static final DateFormat DATE_FORMAT_WITHOUT_MINUTES = new SimpleDateFormat("dd/MM/yy");
    /**
     * Returns an add command string for adding the {@code gameEntry}.
     */
    public static String getAddCommand(GameEntry gameEntry) {
        return AddCommand.COMMAND_WORD + " " + getGameEntryDetails(gameEntry);
    }

    /**
     * Returns the part of command string for the given {@code gameEntry}'s details.
     */
    public static String getGameEntryDetails(GameEntry gameEntry) {
        StringBuilder sb = new StringBuilder();
        String date = gameEntry.getDate().toString();

        sb.append(PREFIX_GAMETYPE + gameEntry.getGameType().toString() + " ");
        sb.append(PREFIX_STARTAMOUNT + gameEntry.getStartAmount().toString() + " ");
        sb.append(PREFIX_ENDAMOUNT + gameEntry.getEndAmount().toString() + " ");
        sb.append(PREFIX_DATE + date + " ");
        sb.append(PREFIX_DURATION + gameEntry.getDuration().toString() + " ");
        sb.append(PREFIX_LOCATION + gameEntry.getLocation().toString() + " ");
        if (gameEntry.hasTags()) {
            sb.append(PREFIX_TAG + " ");
            sb.append(gameEntry
                    .getTags()
                    .stream()
                    .map(x -> x.toString())
                    .reduce("", (x, y) -> x + ", " + y));
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditGameEntryDescriptor}'s details.
     */
    public static String getEditGameEntryDescriptorDetails(EditGameEntryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDate().ifPresent(date ->
                sb.append(String.format("%s %s" + " ", PREFIX_DATE, date.toString()))
        );


        descriptor.getGameType().ifPresent(game -> sb.append(PREFIX_GAMETYPE + ""
                + descriptor.getGameType().get() + " "));

        if (descriptor.getStartAmount().isPresent() && descriptor.getEndAmount().isPresent()) {
            EndAmount endAmount = descriptor.getEndAmount().get();
            StartAmount startAmount = descriptor.getStartAmount().get();
            sb.append(PREFIX_PROFIT + endAmount.minus(startAmount).toString() + " ");
        }
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION + ""
                + descriptor.getDuration().get() + " "));
        descriptor.getLocation().ifPresent(location -> sb.append(PREFIX_LOCATION + ""
                + descriptor.getLocation().get() + " "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                // do nothing
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
