package com.example.algasensors.device.management.api.model;

import com.example.algasensors.device.management.domain.model.Sensor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SensorInput {

    @NotBlank
    private String name;

    @NotBlank
    private String ip;

    @NotBlank
    private String location;

    @NotBlank
    private String protocol;

    @NotBlank
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
