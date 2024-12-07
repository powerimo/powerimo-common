package org.powerimo.common.utils;

import org.powerimo.common.model.ResponseEnvelope;

import javax.servlet.http.HttpServletRequest;

public class ResponseUtils {

    public static ResponseEnvelope dataEnvelope(HttpServletRequest request, Object resultData) {
        ResponseEnvelope envelope = new ResponseEnvelope();
        envelope.setRequest(request);
        envelope.setData(resultData);
        return envelope;
    }


}
