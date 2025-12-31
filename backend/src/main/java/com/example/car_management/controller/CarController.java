package com.example.car_management.controller;

import com.example.car_management.model.Car;
import com.example.car_management.model.FuelEntry;
import com.example.car_management.service.CarService;
import com.example.car_management.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cars") // Base path like Express router
public class CarController {

    @Autowired // Dependency injection (like Angular services)
    private CarService carService;

    @Autowired
    private FuelService fuelService;

    // POST /api/cars - Create car
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createdCar = carService.createCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    // GET /api/cars - List all cars
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // POST /api/cars/{id}/fuel - Add fuel entry
    @PostMapping("/{id}/fuel")
    public ResponseEntity<?> addFuelEntry(
            @PathVariable Long id,
            @RequestBody Map<String, Object> fuelData) {

        if (!carService.carExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Car not found with id: " + id);
        }

        double liters = Double.parseDouble(fuelData.get("liters").toString());
        double price = Double.parseDouble(fuelData.get("price").toString());
        int odometer = Integer.parseInt(fuelData.get("odometer").toString());

        FuelEntry entry = fuelService.addFuelEntry(id, liters, price, odometer);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    // GET /api/cars/{id}/fuel/stats - Get fuel statistics
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