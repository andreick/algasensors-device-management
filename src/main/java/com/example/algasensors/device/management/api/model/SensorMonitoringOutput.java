package com.example.algasensors.device.management.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SensorMonitoringOutput {
    private TSID id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;
}
