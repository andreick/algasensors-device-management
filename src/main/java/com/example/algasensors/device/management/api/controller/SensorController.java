package com.example.algasensors.device.management.api.controller;

import com.example.algasensors.device.management.api.model.SensorInput;
import com.example.algasensors.device.management.api.model.SensorOutput;
import com.example.algasensors.device.management.common.IdGenerator;
import com.example.algasensors.device.management.domain.model.Sensor;
import com.example.algasensors.device.management.domain.model.SensorId;
import com.example.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;

    @GetMapping
    public Page<SensorOutput> getSensors(@PageableDefault @NonNull Pageable pageable) {
        return sensorRepository.findAll(pageable)
                .map(SensorOutput::fromEntity);
    }

    @GetMapping("/{sensorId}")
    public SensorOutput getSensorById(@PathVariable("sensorId") TSID sensorId) {
        return sensorRepository.findById(new SensorId(sensorId))
                .map(SensorOutput::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput createSensor(@RequestBody SensorInput input) {
        Sensor sensor = input.toEntity();
        sensor.setId(new SensorId(IdGenerator.generateTSID()));
        sensor = sensorRepository.saveAndFlush(sensor);
        return SensorOutput.fromEntity(sensor);
    }
}
