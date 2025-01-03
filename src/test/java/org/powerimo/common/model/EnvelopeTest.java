package org.powerimo.common.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnvelopeTest {

    @Test
    void testEnvelopeBuilder() {
        MockJakartaRequest request = new MockJakartaRequest();
        request.setRequestUri("/api/v1/test");

        Envelope envelope = Envelope.builder()
                .code(200)
                .message("success")
                .data("test data")
                .request(request)
                .build();

        assertNotNull(envelope.getTimestamp());
        assertEquals(200, envelope.getCode());
        assertEquals("success", envelope.getMessage());
        assertEquals("test data", envelope.getData());
        assertEquals("/api/v1/test", envelope.getPath());
    }

    @Test
    void testEnvelopeOf() {
        Object payload = "test payload";
        Envelope envelope = Envelope.of(payload);

        assertNotNull(envelope.getTimestamp());
        assertEquals(200, envelope.getCode());
        assertEquals(payload, envelope.getData());
    }

    @Test
    void testEnvelope_TypedString() {
        Envelope<String> envelope = Envelope.of("testString");
        assertEquals("testString", envelope.getData());
    }

    @Test
    void testEnvelope_TypedResult() {
        Envelope<Long> envelope = Envelope.of(123L);
        assertEquals(123L, envelope.getData());
    }

    @Test
    void testEnvelopeOfWithRequest() {
        Object payload = "test payload";
        MockJakartaRequest request = new MockJakartaRequest();
        request.setRequestUri("/api/v1/test");

        Envelope envelope = Envelope.of(payload, request);

        assertNotNull(envelope.getTimestamp());
        assertEquals(200, envelope.getCode());
        assertEquals(payload, envelope.getData());
        assertEquals("/api/v1/test", envelope.getPath());
    }

    @Test
    void testEnvelopeError() {
        Object payload = "test payload";
        String message = "error message";
        int code = 400;

        Envelope envelope = Envelope.error(payload, code, message);

        assertNotNull(envelope.getTimestamp());
        assertEquals(code, envelope.getCode());
        assertEquals(payload, envelope.getData());
        assertEquals(message, envelope.getMessage());
    }

    @Test
    void testSeparatePath() {
        Object payload = "test payload";
        String message = "error message";
        String path = "sample path";
        Instant tm = Instant.now().minusSeconds(1000);
        String messageCode = "TEST-CODE";
        int code = 400;

        Envelope envelope = Envelope.builder()
                .path(path)
                .data(payload)
                .message(message)
                .messageCode(messageCode)
                .code(code)
                .timestamp(tm)
                .build();

        assertNotNull(envelope.getTimestamp());
        assertEquals(code, envelope.getCode());
        assertEquals(payload, envelope.getData());
        assertEquals(message, envelope.getMessage());
        assertEquals(path, envelope.getPath());
        assertEquals(tm, envelope.getTimestamp());
        assertEquals(messageCode, envelope.getMessageCode());
    }
}
