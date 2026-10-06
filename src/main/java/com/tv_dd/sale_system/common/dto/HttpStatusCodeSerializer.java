package com.tv_dd.sale_system.common.dto;

import org.springframework.http.HttpStatusCode;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class HttpStatusCodeSerializer extends ValueSerializer<HttpStatusCode> {
    @Override
    public void serialize(HttpStatusCode value, JsonGenerator gen, SerializationContext context)
            throws JacksonException {
        gen.writeNumber(value.value());
    }
}
