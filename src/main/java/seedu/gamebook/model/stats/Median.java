package seedu.gamebook.model.stats;

import java.util.List;
import java.util.stream.DoubleStream;

import seedu.gamebook.model.gameentry.GameEntry;

public class Median {

    private Median() {
    }

    public static Double getOverallMedian(List<GameEntry> gameEntryList) {
        try {
            double overallMedian;
            DoubleStream sortedProfitList = gameEntryList.stream().mapToDouble(GameEntry::getProfit).sorted();
            if (gameEntryList.size() % 2 == 0) {
                overallMedian = sortedProfitList.skip((gameEntryList.size() / 2) - 1).limit(2).average().orElse(0.00);
            } else {
                overallMedian = sortedProfitList.skip(gameEntryList.size() / 2).findFirst().orElse(0.00);
            }
            return overallMedian;
        } catch (IllegalArgumentException e) {
            return 0.00;
        }
    }
}
