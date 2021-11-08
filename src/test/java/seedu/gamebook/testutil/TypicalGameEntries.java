package seedu.gamebook.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.gamebook.model.GameBook;
import seedu.gamebook.model.gameentry.GameEntry;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalGameEntries {

    // GameEntries without time indicated
    public static final GameEntry POKER1_WITHOUT_TIME = new GameEntryBuilder().withGameType("Poker")
            .withStartAmount("123").withEndAmount("134")
            .withDatePlayed("2020-10-10").withDuration("30")
            .withLocation("Marina Bay Sands")
            .withTags("friends").build();
    public static final GameEntry POKER2_WITHOUT_TIME = new GameEntryBuilder().withGameType("Poker")
            .withStartAmount("0").withEndAmount("-10")
            .withDatePlayed("2020-10-09").withDuration("30")
            .withLocation("Home").build();
    public static final GameEntry DARTS1_WITHOUT_TIME = new GameEntryBuilder().withGameType("Darts")
            .withStartAmount("0").withEndAmount("0")
            .withDatePlayed("2020-12-10").withDuration("30")
            .withLocation("Local Bar")
            .withTags("solo").build();
    public static final GameEntry DARTS2_WITHOUT_TIME = new GameEntryBuilder().withGameType("Darts")
            .withStartAmount("0").withEndAmount("0")
            .withDatePlayed("2020-12-10").withDuration("30")
            .withLocation("Local Bar")
            .withTags("solo").build();

    // GameEntries with time indicated
    public static final GameEntry POKER1_WITH_TIME = new GameEntryBuilder().withGameType("Poker")
            .withStartAmount("0").withEndAmount("50")
            .withDatePlayed("2020-10-11 12:34").withDuration("110")
            .withLocation("Marina Bay Sands")
            .withTags("solo-morning").build();
    public static final GameEntry POKER2_WITH_TIME = new GameEntryBuilder().withGameType("Poker")
            .withStartAmount("1.23").withEndAmount("4.56")
            .withDatePlayed("2020-10-11 13:24").withDuration("110")
            .withLocation("Marina Bay Sands")
            .withTags("solo-morning").build();
    public static final GameEntry BLACKJACK1_WITH_TIME = new GameEntryBuilder().withGameType("Blackjack")
            .withStartAmount("0").withEndAmount("5")
            .withDatePlayed("2020-11-10 18:00").withDuration("10")
            .withLocation("Friend's Place")
            .withTags("friends-drunk").build();
    public static final GameEntry BLACKJACK2_WITH_TIME = new GameEntryBuilder().withGameType("Blackjack")
            .withStartAmount("200").withEndAmount("199")
            .withDatePlayed("2020-11-10 19:00").withDuration("30")
            .withLocation("Friend's Place")
            .withTags("friends-drunk").build();
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER



    // Manually added
    static final GameEntry POKER4 = new GameEntryBuilder().withGameType("Poker")
            .withStartAmount("0").withEndAmount("1")
            .withDatePlayed("2020-10-22 12:34").withDuration("20")
            .withLocation("Sentosa")
            .withTags("vacation").build();

    static final GameEntry BLACKJACK3 = new GameEntryBuilder().withGameType("Blackjack")
            .withStartAmount("30").withEndAmount("15")
            .withDatePlayed("2020-05-22").withDuration("20")
            .withLocation("Sentosa")
            .withTags("vacation").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    // public static final Person AMY = new GameEntryBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
    //        .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    // public static final Person BOB = new GameEntryBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //        .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
    //        .build();

    private TypicalGameEntries() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static GameBook getTypicalGameBook() {
        GameBook gb = new GameBook();
        for (GameEntry gameEntry : getTypicalGameEntries()) {
            gb.addGameEntry(gameEntry);
        }
        return gb;
    }

    public static List<GameEntry> getTypicalGameEntries() {
        return new ArrayList<>(Arrays.asList(
                POKER1_WITHOUT_TIME, POKER1_WITH_TIME,
                POKER2_WITHOUT_TIME, POKER2_WITH_TIME,
                BLACKJACK1_WITH_TIME, BLACKJACK2_WITH_TIME,
                DARTS2_WITHOUT_TIME, DARTS1_WITHOUT_TIME));
    }
}
