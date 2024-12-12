package org.powerimo.common.utils;

import org.slf4j.helpers.MessageFormatter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Useful functions
 */
public class Utils {
    public static final String FORMAT_START_BEAN = "[@] bean start: {}";
    public static final int DEFAULT_LOG_LINE_LENGTH = 80;
    public static final String STARTED = "STARTED";
    public static final String COMPLETED = "COMPLETED";

    public static String extractMessage(Throwable ex) {
        String m = ex.getMessage();
        if (ex.getCause() != null) {
            m = m + "\r\n" + extractMessage(ex.getCause());
        }
        return m;
    }

    public static String extractSourceExceptionText(Throwable ex) {
        if (ex == null)
            return null;
        if (ex.getCause() != null) {
            return extractSourceExceptionText(ex.getCause());
        } else {
            return ex.getMessage();
        }
    }

    /**
     * Recursively extract root exception from getCause methods
     * @param ex source exception
     * @return root exception if causes store value else returns null
     */
    public static Throwable extractRootException(Throwable ex) {
        if (ex.getCause() != null) {
            if (ex.getCause().getCause() != null) {
                return extractRootException(ex.getCause());
            } else {
                return ex.getCause();
            }
        }
        return null;
    }

    /**
     * Execute getMessage from root exception. If there is no root exception returns null
     * @param ex source exception
     * @return message text of root exception if cause store value. Otherwise returns null
     */
    public static String extractRootExceptionMessage(Throwable ex) {
        Throwable e1 = extractRootException(ex);
        if (e1 != null) {
            return e1.getMessage();
        }
        return null;
    }

    /**
     * Start bean string for log usage
     * @param name bean name
     * @return log string
     */
    public static String fmtStartBean(String name) {
        return formatLogValue("Bean " + name, STARTED);
    }

    /**
     * Start bean string for log usage
     * @param cls bean class
     * @return log string
     */
    public static String fmtStartBean(Class cls) {
        return formatLogValue("Bean " + cls.getSimpleName(), STARTED);
    }

    /**
     * Start bean string for log usage (class name)
     * @param obj object of bean
     * @return log string
     */
    public static String fmtStartBean(Object obj) {
        return formatLogValue("Bean " + obj.getClass().getSimpleName(), STARTED);
    }

    /**
     * SLF4J style formmatting string
     * @param pattern string pattern
     * @param objects arguments
     * @return formatted string
     */
    public static String slf4jFormat(String pattern, Object... objects) {
        return MessageFormatter.arrayFormat(pattern, objects).getMessage();
    }

    /**
     * Convert sting to Integer with default value
     * @param s string to convert
     * @param defaultValue default value
     * @return Integer value
     */
    public static Integer stringToIntegerDef(String s, Integer defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * Repeat a string Count times
     * @param s an input string
     * @param count a number of repeats
     * @return an input string repeated Count times
     */
    public static String repeat(String s, int count) {
        return String.valueOf(s).repeat(Math.max(0, count));
    }

    /**
     * Represents a parameter name and value into a string with fixed length: name.....value
     * @param name name of a parameter
     * @param value value of a parameter
     * @param length result string length
     * @return result string
     */
    public static String formatLogValue(String name, Object value, int length) {
        String valueString = value != null ? value.toString() : "<null>";
        int nameLength = name.length();
        int valueLength = valueString.length();
        int dotLength = length - nameLength - valueLength;
        if (dotLength > 0) {
            return name + repeat(".", dotLength) + valueString;
        } else
            return name + "..." + valueString;
    }

    /**
     * Represents a parameter name and value into a string with fixed length: name.....value with default length (80)
     * @param name name of a parameter
     * @param value value of a parameter
     * @return result string
     */
    public static String formatLogValue(String name, Object value) {
        return formatLogValue(name, value, DEFAULT_LOG_LINE_LENGTH);
    }

    public static String formatLogValueStarted(String name) {
        return formatLogValue(name, STARTED);
    }

    public static String formatLogValueCompleted(String name) {
        return formatLogValue(name, COMPLETED);
    }


    public static boolean compareValue(Object v1, Object v2) {
        return Objects.equals(v1, v2);
    }

    public static String masqueradeKey(String s, int startCharCount, int endCharCount) {
        if (s == null)
            return null;

        int len = s.length();
        String result="";
        if (startCharCount < len)
            result = s.substring(0, startCharCount);
        result = result + "**************";
        if (endCharCount > 0 && endCharCount <= len) {
            result = result + s.substring(len - endCharCount, len);
        } else {
            result = result + s;
        }
        return result;
    }

    public static String readTextResource(String name) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return readTextResource(name, classLoader);
    }

    public static String readTextResource(String name, ClassLoader classLoader) throws IOException {
        try (InputStream is = classLoader.getResourceAsStream(name)) {
            if (is == null) {
                throw new IOException("Resource not found: " + name);
            }

            try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }

                return result.toString(StandardCharsets.UTF_8);
            }
        }
    }
}
