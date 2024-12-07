package org.powerimo.common.model;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powerimo.common.model.ResponseEnvelope;

public class ResponseEnvelopeTest {

    @AllArgsConstructor
    private final static class TestData {
        String text;
        Long number;
    }

    @Test
    void responseEnvelope_Typed() {
        var testData = new TestData("test1", 125L);
        ResponseEnvelope<TestData> envelope = ResponseEnvelope.of(testData);

        Assertions.assertNotNull(envelope);
        Assertions.assertNotNull(envelope.getData());
        Assertions.assertEquals(testData.text, envelope.getData().text);
        Assertions.assertEquals(testData.number, envelope.getData().number);
    }

    @Test
    void responseEnvelope_RawUsage() {
        var testData = new TestData("test1", 125L);
        ResponseEnvelope<?> envelope = ResponseEnvelope.of(testData);

        Assertions.assertNotNull(envelope);
        Assertions.assertNotNull(envelope.getData());
        Assertions.assertEquals(testData.getClass().getCanonicalName(), envelope.getDataClassName());
    }

    @Test
    void responseEnvelope_statusTest() {
        var testData = new TestData("test1", 125L);
        ResponseEnvelope<?> envelope = ResponseEnvelope.of(testData);
        envelope.setStatus(403)
                .setMessage("my message");

        Assertions.assertNotNull(envelope.getData());
        Assertions.assertEquals(403, envelope.getHttpStatus());
        Assertions.assertEquals("my message", envelope.getMessage());
        Assertions.assertEquals(testData.getClass().getCanonicalName(), envelope.getDataClassName());
    }
}
