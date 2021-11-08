package seedu.gamebook.model.gameentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.gamebook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.gamebook.testutil.GameEntryBuilder;

public class GameEntryContainsKeywordPredicateTest {

    @Test
    public void gameType_containsKeyword() {

        // Keyword is GameType
        List<String> keywords = Arrays.asList("poker", "randomText1", "randomText2");
        GameEntryContainsKeywordPredicate finder = new GameEntryContainsKeywordPredicate(keywords);
        GameEntry gameEntry = new GameEntryBuilder().withGameType("Poker").build();
        assertTrue(finder.test(gameEntry));

        // Keyword is contained in a location
        keywords = Arrays.asList("marina");
        gameEntry = new GameEntryBuilder().withLocation("marina bay sands").build();
        finder = new GameEntryContainsKeywordPredicate(keywords);
        assertTrue(finder.test(gameEntry));
        gameEntry = new GameEntryBuilder().withLocation("sentosa").build();
        assertFalse(finder.test(gameEntry));

        // Keyword is contained in a tag
        keywords = Arrays.asList("friends", "solo");
        finder = new GameEntryContainsKeywordPredicate(keywords);
        gameEntry = new GameEntryBuilder().withTags("friends").build();
        assertTrue(finder.test(gameEntry));
        gameEntry = new GameEntryBuilder().withTags("solo").build();
        assertTrue(finder.test(gameEntry));

        // Keyword is profit amount -> does not work
        keywords = Arrays.asList("100");
        finder = new GameEntryContainsKeywordPredicate(keywords);
        gameEntry = new GameEntryBuilder().withStartAmount("0").withEndAmount("100").build();
        assertFalse(finder.test(gameEntry));

        // Keyword is date -> does not work
        keywords = Arrays.asList("2020-10-10");
        finder = new GameEntryContainsKeywordPredicate(keywords);
        gameEntry = new GameEntryBuilder().withDatePlayed("2020-10-10").build();
        assertFalse(finder.test(gameEntry));
    }

    @Test
    public void empty_keyword_throwsIllegalArgumentExceptionTest() {
        // Keyword is empty -> returns false
        List<String> empty = Arrays.asList("");
        GameEntryContainsKeywordPredicate emptyFinder = new GameEntryContainsKeywordPredicate(empty);
        GameEntry gameEntry = new GameEntryBuilder().build();
        assertThrows(IllegalArgumentException.class, () -> emptyFinder.test(gameEntry));

        // Keyword is white space -> returns false
        List<String> whitespaces = Arrays.asList("    \n   ");
        GameEntryContainsKeywordPredicate whitespaceFinder = new GameEntryContainsKeywordPredicate(whitespaces);
        assertThrows(IllegalArgumentException.class, () -> whitespaceFinder.test(gameEntry));
    }

    @Test
    public void equal_test() {

        // order does not matter
        List<String> keywords1 = Arrays.asList("word1", "word2");
        List<String> keywords2 = Arrays.asList("word2", "word1");
        GameEntryContainsKeywordPredicate finder1 = new GameEntryContainsKeywordPredicate(keywords1);
        GameEntryContainsKeywordPredicate finder2 = new GameEntryContainsKeywordPredicate(keywords2);
        assertTrue(finder1.equals(finder2));
        assertTrue(finder2.equals(finder1));

        // case does not matter
        keywords1 = Arrays.asList("ignorecases");
        keywords2 = Arrays.asList("IGNORECASES");
        finder1 = new GameEntryContainsKeywordPredicate(keywords1);
        finder2 = new GameEntryContainsKeywordPredicate(keywords2);
        assertTrue(finder1.equals(finder2));
        assertTrue(finder2.equals(finder1));

        // contains different keywords -> false
        keywords1 = Arrays.asList("ignorecases", "word1");
        keywords2 = Arrays.asList("IGNORECASES", "word2");
        finder1 = new GameEntryContainsKeywordPredicate(keywords1);
        finder2 = new GameEntryContainsKeywordPredicate(keywords2);
        assertFalse(finder1.equals(finder2));
        assertFalse(finder2.equals(finder1));
    }
}
