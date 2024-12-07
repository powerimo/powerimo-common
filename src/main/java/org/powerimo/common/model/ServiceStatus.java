package org.powerimo.common.model;

import lombok.Data;
import org.powerimo.common.utils.DateUtils;
import org.powerimo.common.utils.Utils;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@Data
public class ServiceStatus implements Serializable {
    public static final String STATE_MAINTENANCE = "MAINTENANCE";
    public static final String STATE_OK = "OK";
    public static final String STATUS_STOPPED = "STOPPED";
    public static final String STATUS_RUNNING = "RUNNING";
    public static final String STATUS_INITIALIZATION = "INITIALIZATION";
    public static final String STATUS_STARTING = "STARTING";
    public static final String TYPE_ENVIRONMENT_DEV = "DEV";
    public static final String TYPE_ENVIRONMENT_TEST = "TEST";
    public static final String TYPE_ENVIRONMENT_QA = "QA";
    public static final String TYPE_ENVIRONMENT_PROD = "PROD";
    private final HashMap<String, Object> statistics = new HashMap<>();
    private String serviceStatus = STATUS_INITIALIZATION;
    private String serviceMode = STATE_OK;
    private String typeEnvironment = TYPE_ENVIRONMENT_DEV;
    private OffsetDateTime started = OffsetDateTime.now(ZoneOffset.UTC);
    private String version = "N/A";

    /**
     * Service uptime duration
     *
     * @return uptime duration
     */
    public Duration getUptime() {
        return Duration.between(started, OffsetDateTime.now());
    }

    /**
     * Uptime in seconds
     *
     * @return seconds
     */
    public long getUptimeSeconds() {
        return getUptime().toSeconds();
    }

    /**
     * Uptime string ("days:hours:minutes:seconds")
     *
     * @return uptime string
     */
    public String getUptimeString() {
        Duration d = getUptime();
        return DateUtils.formatUptimeString(d);
    }

    public HashMap<String, Object> getStatistics() {
        return statistics;
    }

    public void updateStatistics(String key, Object value) {
        statistics.put(key, value);
    }

    public int getStatAsInt(String key, int defaultValue) {
        Object obj = statistics.get(key);
        if (obj == null)
            return defaultValue;
        return Utils.stringToIntegerDef(obj.toString(), defaultValue);
    }
}
