package ru.ashitok.spring.SensorRestApi.util;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String msg) {
        super(msg);
    }
}
