package ru.ashitok.spring.SensorRestApi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.ashitok.spring.SensorRestApi.dto.MeasurementDTO;
import ru.ashitok.spring.SensorRestApi.models.Measurement;
import ru.ashitok.spring.SensorRestApi.services.MeasurementService;
import ru.ashitok.spring.SensorRestApi.util.MeasurementErrorResponse;
import ru.ashitok.spring.SensorRestApi.util.MeasurementNotCreatedException;
import ru.ashitok.spring.SensorRestApi.util.MeasurementNotFoundException;
import ru.ashitok.spring.SensorRestApi.util.MeasurementValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementService.findById(id));
    }

    @GetMapping("/rainyDayCount")
    public Integer getRainyDays() {
        return measurementService.findRainyDay();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError fieldError : errors) {
                stringBuilder.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(stringBuilder.toString());
        }

        measurementService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                "Измерения с таким id не найдено",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
