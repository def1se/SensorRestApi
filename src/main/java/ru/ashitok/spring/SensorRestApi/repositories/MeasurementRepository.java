package ru.ashitok.spring.SensorRestApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ashitok.spring.SensorRestApi.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    public List<Measurement> findByRaining(boolean raining);
}
