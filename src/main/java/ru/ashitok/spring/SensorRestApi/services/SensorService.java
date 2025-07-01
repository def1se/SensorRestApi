package ru.ashitok.spring.SensorRestApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashitok.spring.SensorRestApi.models.Sensor;
import ru.ashitok.spring.SensorRestApi.repositories.SensorRepository;
import ru.ashitok.spring.SensorRestApi.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findById(int id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        return foundSensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
