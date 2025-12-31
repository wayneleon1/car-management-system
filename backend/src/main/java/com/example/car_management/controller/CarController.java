package com.example.car_management.controller;

import com.example.car_management.dto.CarDTO;
import com.example.car_management.dto.FuelDTO;
import com.example.car_management.model.Car;
import com.example.car_management.model.FuelEntry;
import com.example.car_management.service.CarService;
import com.example.car_management.service.FuelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
@Validated
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private FuelService fuelService;

    @PostMapping
    public ResponseEntity<?> createCar(@Valid @RequestBody CarDTO carDTO) {
        Car car = new Car();
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());

        Car createdCar = carService.createCar(car);
        System.out.println("Created car: " + createdCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        System.out.println("Cars retrieved: " + cars);
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/{id}/fuel")
    public ResponseEntity<?> addFuelEntry(
            @PathVariable Long id,
            @Valid @RequestBody FuelDTO fuelDTO) {

        if (!carService.carExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found with id: " + id);
        }

        FuelEntry entry = fuelService.addFuelEntry(
                id,
                fuelDTO.getLiters(),
                fuelDTO.getPrice(),
                fuelDTO.getOdometer());

        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<?> getFuelStats(@PathVariable Long id) {
        if (!carService.carExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found with id: " + id);
        }

        Map<String, Object> stats = fuelService.getFuelStatistics(id);
        return ResponseEntity.ok(stats);
    }
}