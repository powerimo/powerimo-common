package org.powerimo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powerimo.common.model.ResponseEnvelope;
import org.powerimo.common.utils.AlternativeResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseEnvelopeTests {
    private static final String DEFAULT_MESSAGE = "test message";
    private static final String DEFAULT_PATH =  "/v1/test";

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Test
    public void error1Test() {
        var r = ResponseEnvelope.error(DEFAULT_MESSAGE);
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
    }

    @Test
    public void error2Test() {
        var r = ResponseEnvelope.error(DEFAULT_MESSAGE, HttpServletResponse.SC_CONFLICT);
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
    }

    @Test
    public void error3Test() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn(DEFAULT_PATH);

        var r = ResponseEnvelope.error(DEFAULT_MESSAGE, HttpServletResponse.SC_CONFLICT, request);
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
        assertEquals(DEFAULT_PATH, r.getPath());
    }

    @Test
    public void error4Test() {
        var r = ResponseEnvelope.error(new RuntimeException(DEFAULT_MESSAGE));
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
    }

    @Test
    public void error5Test() {
        var r = ResponseEnvelope.error(new RuntimeException(DEFAULT_MESSAGE), HttpServletResponse.SC_CONFLICT);
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
    }

    @Test
    public void error6Test() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn(DEFAULT_PATH);

        var r = ResponseEnvelope.error(new RuntimeException(DEFAULT_MESSAGE), HttpServletResponse.SC_CONFLICT, request);
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
        assertEquals(DEFAULT_PATH, r.getPath());
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
    }

    @Test
    void testResponse1() {
        var ab = AlternativeResponseBuilder.builder()
                .message("test message")
                .path("/v1/test")
                .payload("testDate")
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .build();
        assertNotNull(ab);
        assertEquals(ab.getHttpStatus(), 400);
    }

    @Test
    void testResponse2() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/v1/test");

        var ab = AlternativeResponseBuilder.builder()
                .message("test message")
                .path(request)
                .payload("testData")
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .build();
        assertNotNull(ab);
        assertEquals(ab.getHttpStatus(), 400);
        Assertions.assertTrue(ab.toString().contains("/v1/test"));
    }

    @Test
    void testResponse3() {
        var r = ResponseEnvelope.of("test1");
        assertEquals("test1", r.getData());
    }

    @Test
    void testResponse4() {
        var r = ResponseEnvelope.of(null);
        Assertions.assertNull(r.getData());
        Assertions.assertNull(r.getDataClassName());
    }

    @Test
    void testResponse5() {
        var r = ResponseEnvelope.of(null, null);
        Assertions.assertNull(r.getPath());
        r.setRequest(null);
        Assertions.assertNull(r.getPath());

        var request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/sample/path");
        r = ResponseEnvelope.of(null, request);
        assertEquals("/sample/path", r.getPath());
    }

    @Test
    void testResponse6() {
        var r = new ResponseEnvelope(DEFAULT_MESSAGE);
        r.setStatus(HttpServletResponse.SC_CONFLICT);
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
    }

    @Test
    void testResponse7() {
        var r = new ResponseEnvelope(DEFAULT_MESSAGE, DEFAULT_PATH);
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
        assertEquals(HttpServletResponse.SC_OK, r.getHttpStatus());
        assertEquals(DEFAULT_PATH, r.getPath());
    }

    @Test
    void testResponse8() {
        var r = new ResponseEnvelope(DEFAULT_MESSAGE, DEFAULT_PATH, HttpServletResponse.SC_CONFLICT);
        assertEquals(DEFAULT_MESSAGE, r.getMessage());
        assertEquals(HttpServletResponse.SC_CONFLICT, r.getHttpStatus());
        assertEquals(DEFAULT_PATH, r.getPath());
    }

    @Test
    void serializeResponse() throws JsonProcessingException {
        var mapper = createMapper();

        ResponseEnvelope envelope = ResponseEnvelope.of("test");
        envelope.setMessage("test message");
        String data = mapper.writeValueAsString(envelope);
        assertNotNull(data);
    }

    @Test
    void deserializeResponse() throws JsonProcessingException {
        final String data = "{\"message\":\"test message\",\"data\":\"test\",\"httpStatus\":200,\"path\":null,\"timestamp\":\"2022-09-01T15:21:02.8847257\",\"dataClassName\":\"java.lang.String\",\"details\":{},\"statusString\":\"200\",\"statusCode\":200}";
        ObjectMapper mapper = createMapper();
        var response = mapper.readValue(data, ResponseEnvelope.class);
        assertNotNull(response);
        assertEquals("test", response.getData());
    }

}
