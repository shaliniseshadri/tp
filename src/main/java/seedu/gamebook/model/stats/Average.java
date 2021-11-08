package seedu.gamebook.model.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import seedu.gamebook.model.gameentry.GameEntry;

public class Average {
    private static final String REGEX_TO_SPLIT_DATE_AND_TIME = "\\s";

    private Average() {
    }

    /**
     * Puts all the Dates and corresponding profits into a TreeMap from a GameEntry List
     *
     * @param gameEntryList The list of game entries
     * @return a treemap with the dates as keys and the average profit as values
     */
    public static TreeMap<String, Double> getAverageData(List<GameEntry> gameEntryList) {
        TreeMap<String, List<Double>> preprocessedDates = new TreeMap<>(); // before calculating average
        TreeMap<String, Double> processedDates = new TreeMap<>(); // after calculating average

        // Populate preprocessedDates, with keys being the dates, and values being the List of profits from the dates
        gameEntryList.forEach(gameEntry -> {
            String dateWithoutTime =
                    gameEntry.getDate().toString().strip().split(REGEX_TO_SPLIT_DATE_AND_TIME, 2)[0];
            if (!preprocessedDates.containsKey(dateWithoutTime)) {
                preprocessedDates.put(dateWithoutTime, new ArrayList<>());
            }
            preprocessedDates.get(dateWithoutTime).add(gameEntry.getDifference());
        });

        // Using preprocessedDates, populate processedDates, with dates as the keys and average profits as the values
        preprocessedDates.forEach((date, listOfProfits) -> {
            Double averageProfit = listOfProfits.stream().mapToDouble(Double::doubleValue).average().orElse(0.00);
            processedDates.put(date, averageProfit);
        });

        return processedDates;
    }

    public static Double getOverallAverage(List<GameEntry> gameEntryList) {
        return gameEntryList.stream().mapToDouble(GameEntry::getDifference).average().orElse(0.00);
    }

}
