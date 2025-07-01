package ru.ashitok.spring.SensorRestApi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    @NotNull(message = "Значение не должно быть пустым")
    @DecimalMin(value = "-100.00", message = "Значение должно быть в диапозоне от -100 до 100")
    @DecimalMax(value = "100.00", message = "Значение должно быть в диапозоне от -100 до 100")
    private Double value;

    @NotNull(message = "Значение не должно быть пустым")
    @Column(name = "raining")
    private Boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull(message = "Измерение должно быть выдано сенсором")
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    public Measurement() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
