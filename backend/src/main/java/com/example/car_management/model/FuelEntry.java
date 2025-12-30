package com.example.car_management.model;

import java.time.LocalDateTime;

public class FuelEntry {
    private Long id;
    private Long carId;
    private double liters;
    private double price;
    private int odometer;
    private LocalDateTime timestamp;

    // Constructors, getters, setters
    public FuelEntry() {
    }

    public FuelEntry(Long carId, double liters, double price, int odometer) {
        this.carId = carId;
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
        this.timestamp = LocalDateTime.now();
    }
}