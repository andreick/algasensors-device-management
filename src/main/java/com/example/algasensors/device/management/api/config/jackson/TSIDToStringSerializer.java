package com.example.algasensors.device.management.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

public class TSIDToStringSerializer extends StdSerializer<TSID> {

    public TSIDToStringSerializer() {
        this(null);
    }

    public TSIDToStringSerializer(Class<TSID> t) {
        super(t);
    }

    @Override
    public void serialize(TSID value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
}
