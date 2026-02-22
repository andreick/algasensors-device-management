package com.example.algasensors.device.management.api.model;

import com.example.algasensors.device.management.domain.model.Sensor;
import lombok.Data;

@Data
public class SensorInput {

    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;

    public Sensor toEntity() {
        return Sensor.builder()
                .name(name)
                .ip(ip)
                .location(location)
                .protocol(protocol)
                .model(model)
                .enabled(false)
                .build();
    }
}
