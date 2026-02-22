package com.example.algasensors.device.management.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToTSIDConverter implements Converter<String, TSID> {

    @Override
    public TSID convert(@NonNull String source) {
        return TSID.from(source);
    }
}
