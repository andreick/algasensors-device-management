package com.example.algasensors.device.management.api.controller;

import com.example.algasensors.device.management.api.client.SensorMonitoringClient;
import com.example.algasensors.device.management.api.model.SensorDetailOutput;
import com.example.algasensors.device.management.api.model.SensorInput;
import com.example.algasensors.device.management.api.model.SensorMonitoringOutput;
import com.example.algasensors.device.management.api.model.SensorOutput;
import com.example.algasensors.device.management.common.IdGenerator;
import com.example.algasensors.device.management.domain.model.Sensor;
import com.example.algasensors.device.management.domain.model.SensorId;
import com.example.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final SensorMonitoringClient sensorMonitoringClient;

    @GetMapping
    public Page<SensorOutput> getSensors(@PageableDefault Pageable pageable) {
        return sensorRepository.findAll(pageable)
                .map(SensorOutput::fromEntity);
    }

    @GetMapping("/{sensorId}")
    public SensorOutput getSensorById(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);
        return SensorOutput.fromEntity(sensor);
    }

    @GetMapping("{sensorId}/detail")
    public SensorDetailOutput getSensorByIdWithDetail(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);

        SensorMonitoringOutput monitoringOutput = sensorMonitoringClient.getDetail(sensorId);
        SensorOutput sensorOutput = SensorOutput.fromEntity(sensor);

        return SensorDetailOutput.builder()
                .monitoring(monitoringOutput)
                .sensor(sensorOutput)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput createSensor(@RequestBody @Valid SensorInput input) {
        Sensor sensor = input.toEntity();
        sensor.setId(new SensorId(IdGenerator.generateTSID()));
        sensor = sensorRepository.saveAndFlush(sensor);
        return SensorOutput.fromEntity(sensor);
    }

    @PutMapping("/{sensorId}")
    public SensorOutput updateSensor(@PathVariable("sensorId") TSID sensorId, @RequestBody @Valid SensorInput input) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);

        sensor.setName(input.getName());
        sensor.setIp(input.getIp());
        sensor.setLocation(input.getLocation());
        sensor.setProtocol(input.getProtocol());
        sensor.setModel(input.getModel());

        sensor = sensorRepository.save(sensor);

        return SensorOutput.fromEntity(sensor);
    }

    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableSensor(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);
        sensor.enable();
        sensorMonitoringClient.enableMonitoring(sensorId);
        sensorRepository.save(sensor);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensor(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);
        sensorMonitoringClient.disableMonitoring(sensorId);
        sensorRepository.delete(sensor);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableSensor(@PathVariable("sensorId") TSID sensorId) {
        Sensor sensor = findSensorByIdOrThrow(sensorId);
        sensor.disable();
        sensorMonitoringClient.disableMonitoring(sensorId);
        sensorRepository.save(sensor);
    }

    private Sensor findSensorByIdOrThrow(TSID sensorId) {
        return sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
