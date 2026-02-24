package com.example.algasensors.device.management.api.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

public class TSIDStringDeserializer extends StdDeserializer<TSID> {

    public TSIDStringDeserializer() {
        this(null);
    }

    public TSIDStringDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TSID deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return TSID.from(jp.getText());
    }
}
