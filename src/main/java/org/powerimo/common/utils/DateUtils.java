package org.powerimo.common.utils;

import java.time.Duration;
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

    public static String formatDuration(LocalDateTime d1, LocalDateTime d2) {
        final Duration d = Duration.between(d1, d2);
        return formatDuration(d);
    }

    public static String formatDuration(Duration d) {
        return String.format("%d.%d", d.toSeconds(), d.getNano());
    }

}
