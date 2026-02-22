package com.example.algasensors.device.management.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class SensorId implements Serializable {

    private TSID value;

    public SensorId(@NonNull TSID value) {
        this.value = value;
    }

    public SensorId(@NonNull Long value) {
        this.value = TSID.from(value);
    }

    public SensorId(@NonNull String value) {
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
