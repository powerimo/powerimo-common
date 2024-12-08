package org.powerimo.common.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void formatDurationHMS_when_ms() {
        var d1 = LocalDateTime.of(2020,1,1,1,1, 5);
        var d2 = LocalDateTime.of(2020,1,10,10,15, 10, 666);
        var result = DateUtils.formatDurationHMS(d1.toInstant(ZoneOffset.UTC), d2.toInstant(ZoneOffset.UTC), false);
        assertEquals("225:14:05", result);
    }

    @Test
    void formatDurationHMS_when_without_ms() {
        var d1 = LocalDateTime.of(2020,1,1,1,1, 5, 222_000_000);
        var d2 = LocalDateTime.of(2020,1,1,1,1, 10, 666_000_000);
        var result = DateUtils.formatDurationHMS(d1.toInstant(ZoneOffset.UTC), d2.toInstant(ZoneOffset.UTC), true);
        assertEquals("00:00:05.444", result);
    }
}