package com.example.algasensors.device.management.api.config.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.hypersistence.tsid.TSID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TSIDJacksonConfig {

    @Bean
    SimpleModule tsidModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(TSID.class, new TSIDStringSerializer());
        module.addDeserializer(TSID.class, new TSIDStringDeserializer());
        return module;
    }
}
