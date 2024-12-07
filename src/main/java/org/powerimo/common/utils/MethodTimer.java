package org.powerimo.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class MethodTimer {
    private LocalDateTime start = LocalDateTime.now();

    private String formatString(Duration duration) {
        return String.format("%d.%d", duration.toSeconds(), duration.getNano());
    }

    public Duration getDuration() {
        return Duration.between(start, LocalDateTime.now());
    }

    public String getDurationString() {
        final Duration d = Duration.between(start, LocalDateTime.now());
        return formatString(d);
    }

    public String getDurationStringAndStartNext() {
        final Duration d = Duration.between(start, LocalDateTime.now());
        start = LocalDateTime.now();
        return formatString(d);
    }

}
