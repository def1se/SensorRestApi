package ru.ashitok.spring.SensorRestApi.util;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String message) {
        super(message);
    }
}
