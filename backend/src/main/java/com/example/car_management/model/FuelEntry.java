package com.example.car_management.model;

import java.time.LocalDateTime;

public class FuelEntry {

    private Long id;
    private Long carId;
    private double liters;
    private double price;
    private int odometer;
    private LocalDateTime timestamp;

    // No-args constructor
    public FuelEntry() {
    }

    // Constructor
    public FuelEntry(Long carId, double liters, double price, int odometer) {
        this.carId = carId;
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public double getLiters() {
        return liters;
    }

    public void setLiters(double liters) {
        this.liters = liters;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
