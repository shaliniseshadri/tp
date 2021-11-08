package seedu.gamebook.model.gameentry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.gamebook.commons.util.StringUtil;

/**
 * Tests that a {@code GameEntry}'s text matches any of the keywords given.
 */
public class GameEntryContainsKeywordPredicate implements Predicate<GameEntry> {
    private final Set<String> keywords;

    /**
     * Constructs a {@Code GameEntryContainsKeywordPredicate}.
     *
     * @param keywords
     */
    public GameEntryContainsKeywordPredicate(List<String> keywords) {
        this.keywords = new HashSet<>();
        for (String keyword : keywords) {
            this.keywords.add(keyword.toLowerCase());
        }
    }

    @Override
    public boolean test(GameEntry gameEntry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(gameEntry.getSearchableCorpus(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof GameEntryContainsKeywordPredicate)) {
            return false;
        }
        GameEntryContainsKeywordPredicate tmp = (GameEntryContainsKeywordPredicate) other;
        return this.keywords.equals(tmp.keywords);
    }

}
