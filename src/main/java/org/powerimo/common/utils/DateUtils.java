package org.powerimo.common.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class DateUtils {

    /**
     * Format uptime string "days:hours:minutes:seconds"
     *
     * @param duration duration of uptime
     * @return formatted uptime string
     */
    public static String formatUptimeString(Duration duration) {
        return formatUptimeString(duration.toSeconds());
    }

    /**
     * Format uptime string "days:hours:minutes:seconds"
     *
     * @param seconds uptime seconds
     * @return formatted uptime string
     */
    public static String formatUptimeString(long seconds) {
        final long absSeconds = Math.abs(seconds);
        final String positive = String.format(
                "%d:%02d:%02d:%02d",
                absSeconds / 3600 / 24,                     // days
                (absSeconds % (3600 * 24)) / (3600),       // hours
                (absSeconds % 3600) / 60,                   // minutes
                absSeconds % 60);                           // seconds
        return seconds < 0 ? "-" + positive : positive;
    }

    /**
     * Returns duration between two dates in 's.nnn' format
     * @param d1 Start date
     * @param d2 End date
     * @return formatted string
     */
    public static String formatDuration(LocalDateTime d1, LocalDateTime d2) {
        final Duration d = Duration.between(d1, d2);
        return formatDuration(d);
    }

    public static String formatDuration(Duration d) {
        return String.format("%d.%d", d.toSeconds(), d.getNano());
    }

    /**
     * Returns formatted string 'hh:mm:ss' or 'hh:mm:ss.nnn' as a duration between two dates
     * @param d1 Start date
     * @param d2 End Date
     * @param includeMillis include millis to result
     * @return formatted string
     */
    public static String formatDurationHMS(Instant d1, Instant d2, boolean includeMillis) {
        final Duration duration = Duration.between(d1, d2);
        long hours = duration.toHours();
        Duration remaining = duration.minusHours(hours);
        long minutes = remaining.toMinutes();
        remaining = remaining.minusMinutes(minutes);
        long seconds = remaining.toSeconds();

        if (includeMillis) {
            long millis = remaining.minusSeconds(seconds).toMillisPart();
            return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
