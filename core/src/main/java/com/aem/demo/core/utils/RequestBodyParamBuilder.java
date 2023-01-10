package com.aem.demo.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBodyParamBuilder<T> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public T getParamObject(SlingHttpServletRequest request, Class<T> type) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        ObjectMapper mapper = new ObjectMapper();
        T object = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            object = mapper.readValue(jb.toString(), type);
        } catch (IOException ex) {
            log.error("Error at RequestBodyParamBuilder: {}", ex.getMessage(), ex);
        }

        return object;
    }
}
