package com.example.algasensors.device.management.api.controller;

import com.example.algasensors.device.management.api.model.SensorInput;
import com.example.algasensors.device.management.api.model.SensorOutput;
import com.example.algasensors.device.management.common.IdGenerator;
import com.example.algasensors.device.management.domain.model.Sensor;
import com.example.algasensors.device.management.domain.model.SensorId;
import com.example.algasensors.device.management.domain.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput createSensor(@RequestBody SensorInput input) {
        Sensor sensor = input.toEntity();
        sensor.setId(new SensorId(IdGenerator.generateTSID()));
        sensor = sensorRepository.saveAndFlush(sensor);
        return SensorOutput.fromEntity(sensor);
    }
}
