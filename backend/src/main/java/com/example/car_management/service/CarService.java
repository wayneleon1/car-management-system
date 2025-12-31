package com.example.car_management.service;

import com.example.car_management.model.Car;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarService {
    private final Map<Long, Car> cars = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Car createCar(Car car) {
        car.setId(idCounter.getAndIncrement());
        cars.put(car.getId(), car);
        return car;
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    public Car getCarById(Long id) {
        return cars.get(id);
    }

    public boolean carExists(Long id) {
        return cars.containsKey(id);
    }
}