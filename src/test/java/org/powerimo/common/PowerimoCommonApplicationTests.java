package org.powerimo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powerimo.common.exception.PowerimoRuntimeException;
import org.powerimo.common.model.ServiceStatus;
import org.powerimo.common.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

class PowerimoCommonApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testResponseBuilder() {
        var r = AlternativeResponseBuilder.builder()
                .path("/v1/test")
                .message("test message")
                .payload("DATA STRING")
                .status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .buildEnvelope();
        Assertions.assertNotNull(r);
        Assertions.assertEquals(500, r.getHttpStatus());
        Assertions.assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, r.getHttpStatus());
        Assertions.assertEquals(r.getDataClassName(), String.class.getCanonicalName());
        Assertions.assertEquals(r.getData(), "DATA STRING");
    }

    @Test
    void strToIntConvert() {
        var d1 = Utils.stringToIntegerDef("120", 0);
        Assertions.assertEquals(d1, 120);
        var d2 = Utils.stringToIntegerDef("aaa", 10);
        Assertions.assertEquals(d2, 10);
    }

    @Test
    void slf4jtest() {
        var s1 = Utils.slf4jFormat("test data: {}", 120);
        Assertions.assertEquals(s1, "test data: 120");
    }

    @Test
    void testExceptionsRoot() {
        PowerimoRuntimeException e = new PowerimoRuntimeException("test exception");
        PowerimoRuntimeException eExt = new PowerimoRuntimeException("ext", e);
        PowerimoRuntimeException eExt2 = new PowerimoRuntimeException("ext2", eExt);

        var e1 = Utils.extractRootException(eExt);
        Assertions.assertEquals(e1, e);
        var s1= Utils.extractRootExceptionMessage(eExt);
        Assertions.assertEquals(s1, "test exception");


        var e2 = Utils.extractRootException(e);
        Assertions.assertNull(e2);
        var s2 = Utils.extractRootExceptionMessage(e);
        Assertions.assertNull(s2);

        var e3 = Utils.extractRootException(eExt2);
        Assertions.assertEquals(e3, e);
        Assertions.assertEquals(Utils.extractRootExceptionMessage(eExt2), "test exception");
    }

    @Test
    void testExceptionFullMessage() {
        PowerimoRuntimeException e = new PowerimoRuntimeException("test exception");
        PowerimoRuntimeException eExt = new PowerimoRuntimeException("ext", e);
        PowerimoRuntimeException eExt2 = new PowerimoRuntimeException("ext2", eExt);

        final String s = "ext2\r\next\r\ntest exception";
        final String s2 = "test exception";
        Assertions.assertEquals(Utils.extractMessage(eExt2), s);
        Assertions.assertEquals(Utils.extractMessage(e), s2);
    }

    @Test
    void testBeansString() {
        var s1 = Utils.fmtStartBean(this.getClass());
        var s2 = Utils.fmtStartBean(this);
        var s3 = Utils.fmtStartBean("test3");

        Assertions.assertEquals("Bean PowerimoCommonApplicationTests......................................STARTED", s1);
        Assertions.assertEquals("Bean PowerimoCommonApplicationTests......................................STARTED", s2);
        Assertions.assertEquals("Bean test3...............................................................STARTED", s3);
    }

    @Test
    void serviceStatus() {
        final ServiceStatus status = new ServiceStatus();
        Assertions.assertNotNull(status.getUptime());
        Assertions.assertEquals(status.getServiceStatus(), ServiceStatus.STATUS_INITIALIZATION);
        Assertions.assertNotNull(status.getUptimeString());
        Assertions.assertNotNull(status.getServiceMode(), ServiceStatus.STATE_OK);
        status.updateStatistics("stat1", 100);
        var v1 = status.getStatAsInt("stat1", 0);
        Assertions.assertEquals(100, v1);
        status.updateStatistics("stat2", "abc");
        Assertions.assertEquals(0, status.getStatAsInt("stat2", 0));
        status.getUptimeSeconds();
    }

    @Test
    void methodTimer() throws InterruptedException {
        var mt = new MethodTimer();
        Thread.sleep(1000);
        var s = mt.getDurationString();
        Assertions.assertNotNull(s);
        s = mt.getDurationStringAndStartNext();
        Assertions.assertNotNull(s);
        Assertions.assertNotNull(mt.getDuration());
    }

    @Test
    void randomStringTest1() {
        var rs = new RandomStrings();
        var s = rs.nextString();
        Assertions.assertNotNull(s);
    }

    @Test
    void randomStringsTestLength() {
        var rs = new RandomStrings(64);
        var s = rs.nextString();
        Assertions.assertEquals(64, s.length());
    }

    @Test
    void compareValueTest() {
        Assertions.assertFalse(Utils.compareValue(UUID.randomUUID(), UUID.randomUUID()));
        Assertions.assertTrue(Utils.compareValue("aaa", "aaa"));
        Assertions.assertFalse(Utils.compareValue("aaa", "bbb"));
        Assertions.assertFalse(Utils.compareValue(null, "a"));
        Assertions.assertFalse(Utils.compareValue("b", null));
    }

    @Test
    void formatDurationTest() {
        var d1 = Instant.now().minus(1, ChronoUnit.HOURS).minus(3, ChronoUnit.SECONDS);
        var d2 = Instant.now();
        var dur = Duration.between(d1, d2);
        Assertions.assertTrue(DateUtils.formatDuration(dur).startsWith("3603"));
    }

    @Test
    void formatDurationTest2() {
        var d1 = LocalDateTime.now().minus(10, ChronoUnit.SECONDS);
        var d2 = LocalDateTime.now();
        Assertions.assertTrue(DateUtils.formatDuration(d1, d2).startsWith("10"));
    }
}
