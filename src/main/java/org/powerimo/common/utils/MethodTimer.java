package org.powerimo.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class MethodTimer {
    private LocalDateTime start = LocalDateTime.now();

    /**
     * Formats the given Duration into a string representation.
     *
     * @param duration the Duration to format
     * @return a string formatted as seconds and nanoseconds, separated by a period
     */
    private String formatString(Duration duration) {
        return String.format("%d.%d", duration.toSeconds(), duration.getNano());
    }

    /**
     * Calculates the duration between the initial start time and the current time.
     *
     * @return the duration between the stored start time and the current time as a {@code Duration} object
     */
    public Duration getDuration() {
        return Duration.between(start, LocalDateTime.now());
    }

    /**
     * Calculates the duration from the starting time to the current time
     * and formats it into a human-readable string.
     *
     * @return A formatted string representing the elapsed duration
     *         in seconds and nanoseconds since the starting time.
     */
    public String getDurationString() {
        final Duration d = Duration.between(start, LocalDateTime.now());
        return formatString(d);
    }

    /**
     * Calculates the duration that has elapsed since the last recorded start time,
     * updates the start time to the current moment, and returns a formatted string
     * representation of the duration.
     *
     * @return A string representing the duration elapsed since the last recorded start time,
     *         formatted as seconds and nanoseconds.
     */
    public String getDurationStringAndStartNext() {
        final Duration d = Duration.between(start, LocalDateTime.now());
        start = LocalDateTime.now();
        return formatString(d);
    }

}
