package ru.ashitok.spring.SensorRestApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashitok.spring.SensorRestApi.models.Measurement;
import ru.ashitok.spring.SensorRestApi.models.Sensor;
import ru.ashitok.spring.SensorRestApi.repositories.MeasurementRepository;
import ru.ashitok.spring.SensorRestApi.util.MeasurementNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findById(int id) {
        Optional<Measurement> foundMeasurement = measurementRepository.findById(id);
        return foundMeasurement.orElseThrow(MeasurementNotFoundException::new);
    }

    public Integer findRainyDay() {
        List<Measurement> getMeasurementWithRainingTrue = measurementRepository.findByRaining(true);
        return getMeasurementWithRainingTrue.size();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setCreated_at(LocalDateTime.now());
        measurementRepository.save(measurement);
    }
}
