package com.example.car_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class FuelDTO {

    @NotNull(message = "Liters is required")
    @Min(value = 0, message = "Liters must be positive")
    private Double liters;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be positive")
    private Double price;

    @NotNull(message = "Odometer is required")
    @Min(value = 0, message = "Odometer must be positive")
    private Integer odometer;

    // Default constructor
    public FuelDTO() {
    }

    // Constructor with parameters
    public FuelDTO(Double liters, Double price, Integer odometer) {
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
    }

    // Getters and Setters
    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    @Override
    public String toString() {
        return "FuelDTO{liters=" + liters + ", price=" + price + ", odometer=" + odometer + "}";
    }
}