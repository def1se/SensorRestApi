package ru.ashitok.spring.SensorRestApi.dto;

import jakarta.validation.constraints.*;
import ru.ashitok.spring.SensorRestApi.models.Sensor;

public class MeasurementDTO {
    @NotNull(message = "Значение не должно быть пустым")
    @DecimalMin(value = "-100.00", message = "Значение должно быть в диапозоне от -100 до 100")
    @DecimalMax(value = "100.00", message = "Значение должно быть в диапозоне от -100 до 100")
    private double value;

    @NotNull(message = "Значение не должно быть пустым")
    private boolean raining;

    @NotNull(message = "Измерение должно быть выдано сенсором")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
