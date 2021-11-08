
package seedu.gamebook.logic.parser;

import static seedu.gamebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gamebook.logic.parser.ArgumentTokenizer.MESSAGE_DUPLICATE_FLAGS;
import static seedu.gamebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.gamebook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.gamebook.logic.parser.ParserTestUtil.GAMEONE;
import static seedu.gamebook.logic.parser.ParserTestUtil.STARTAMOUNT_INVALID_WITH_PREFIX;
import static seedu.gamebook.logic.parser.ParserTestUtil.TAG_EMPTY;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_DATE_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_DURATION_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_ENDAMOUNT_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_GAMETYPE_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_LOCATION_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_STARTAMOUNT_1;
import static seedu.gamebook.logic.parser.ParserTestUtil.VALID_TAG_1;
import static seedu.gamebook.testutil.TypicalIndexes.INDEX_FIRST_GAMEENTRY;
import static seedu.gamebook.testutil.TypicalIndexes.INDEX_SECOND_GAMEENTRY;
import static seedu.gamebook.testutil.TypicalIndexes.INDEX_THIRD_GAMEENTRY;

import org.junit.jupiter.api.Test;

import seedu.gamebook.commons.core.index.Index;
import seedu.gamebook.logic.commands.EditCommand;
import seedu.gamebook.logic.commands.EditCommand.EditGameEntryDescriptor;
import seedu.gamebook.testutil.EditGameEntryDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, GAMEONE.gameTypeWithPrefix, MESSAGE_INVALID_FORMAT);
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + GAMEONE.gameTypeWithPrefix, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + GAMEONE.gameTypeWithPrefix, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_success() {
        Index targetIndex = INDEX_FIRST_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + GAMEONE.gameTypeWithPrefix + " "
            + GAMEONE.profitWithPrefix + "randomString";
        EditGameEntryDescriptor descriptor;
        descriptor = new EditGameEntryDescriptorBuilder()
            .withGameType(VALID_GAMETYPE_1)
            .build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        // invalid profit and a valid gametype should return an EditCommand with a valid game type and empty profit
        // descriptor
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + GAMEONE.gameTypeWithPrefix + GAMEONE.profitWithPrefix
                + GAMEONE.dateWithPrefix + GAMEONE.durationWithPrefix
                + GAMEONE.locationWithPrefix + GAMEONE.tagWithPrefix;

        EditGameEntryDescriptor descriptor;
        descriptor = new EditGameEntryDescriptorBuilder()
                .withGameType(VALID_GAMETYPE_1)
                .withProfit(VALID_ENDAMOUNT_1.minus(VALID_STARTAMOUNT_1))
                .withDatePlayed(VALID_DATE_1)
                .withDuration(VALID_DURATION_1).withLocation(VALID_LOCATION_1)
                .withTags(VALID_TAG_1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + GAMEONE.profitWithPrefix;


        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder()
                .withProfit(VALID_ENDAMOUNT_1.minus(VALID_STARTAMOUNT_1)).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // gameType
        Index targetIndex = INDEX_THIRD_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + GAMEONE.gameTypeWithPrefix;
        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder()
            .withGameType(VALID_GAMETYPE_1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + GAMEONE.dateWithPrefix;
        descriptor = new EditGameEntryDescriptorBuilder().withDatePlayed(VALID_DATE_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // duration
        userInput = targetIndex.getOneBased() + GAMEONE.durationWithPrefix;
        descriptor = new EditGameEntryDescriptorBuilder().withDuration(VALID_DURATION_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + GAMEONE.locationWithPrefix;
        descriptor = new EditGameEntryDescriptorBuilder().withLocation(VALID_LOCATION_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + GAMEONE.tagWithPrefix;
        descriptor = new EditGameEntryDescriptorBuilder().withTags(VALID_TAG_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // TODO
        // assertParseFailure(parser, userInput, MESSAGE_DUPLICATE_FLAGS);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // TODO
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + STARTAMOUNT_INVALID_WITH_PREFIX
                + GAMEONE.startAmountWithPrefix;
        assertParseFailure(parser, userInput, MESSAGE_DUPLICATE_FLAGS);

        // other valid values specified
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_GAMEENTRY;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditGameEntryDescriptor descriptor = new EditGameEntryDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

