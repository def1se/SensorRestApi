package ru.ashitok.spring.SensorRestApi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ashitok.spring.SensorRestApi.dto.SensorDTO;
import ru.ashitok.spring.SensorRestApi.models.Sensor;
import ru.ashitok.spring.SensorRestApi.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        //теперь смотрим, есть ли такой же сенсор
        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Сенсор с таким названием уже есть");
        }
    }
}
