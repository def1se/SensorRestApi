package ru.ashitok.spring.SensorRestApi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ashitok.spring.SensorRestApi.dto.SensorDTO;
import ru.ashitok.spring.SensorRestApi.models.Sensor;
import ru.ashitok.spring.SensorRestApi.services.SensorService;
import ru.ashitok.spring.SensorRestApi.util.SensorErrorResponse;
import ru.ashitok.spring.SensorRestApi.util.SensorNotCreatedException;
import ru.ashitok.spring.SensorRestApi.util.SensorNotFoundException;
import ru.ashitok.spring.SensorRestApi.util.SensorValidator;

import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findById(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {

        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError fieldError : errors) {
                stringBuilder.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(stringBuilder.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
            e.getMessage(),
            System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
            "Сенсора с таким id не найдено",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }
}
