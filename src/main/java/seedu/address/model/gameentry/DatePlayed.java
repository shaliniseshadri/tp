package seedu.address.model.gameentry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePlayed {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private Date datePlayed;

    public DatePlayed() {
        this.datePlayed = new Date();
    }

    public DatePlayed(long millisecondsSinceEpochStart) {
        this.datePlayed = new Date(millisecondsSinceEpochStart);
    }

    public DatePlayed(Date date) {
        long millisecondsSinceEpochStart = date.getTime();
        this.datePlayed = new Date(millisecondsSinceEpochStart);
    }

    /**
     * Checks if another object is equal to this DatePlayed. Two DatePlayed objects are
     * equal if and only if they occur within the same minute of each other.
     *
     * @param other Object to check if is equal.
     * @return Whether the other object is equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof DatePlayed) {
            int millisecondsPerMinute = 60000;
            DatePlayed tmp = (DatePlayed) other;
            long minutesSinceEpoch = datePlayed.getTime() / millisecondsPerMinute;
            long otherMinutesSinceEpoch = tmp.datePlayed.getTime() / millisecondsPerMinute;
            return minutesSinceEpoch == otherMinutesSinceEpoch;
        }
        return false;
    }

    @Override
    public String toString() {
        return DATE_FORMAT.format(datePlayed);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
