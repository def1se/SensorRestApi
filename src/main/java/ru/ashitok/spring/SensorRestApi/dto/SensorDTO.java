package ru.ashitok.spring.SensorRestApi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotNull(message = "Название не должно быть пустым")
    @Size(min = 3, max = 30, message = "Название должно иметь не менее 3 и не более 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
