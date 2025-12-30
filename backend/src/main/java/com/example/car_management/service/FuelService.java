package com.example.car_management.service;

import com.example.car_management.model.FuelEntry;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FuelService {
    private final Map<Long, List<FuelEntry>> fuelEntries = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public FuelEntry addFuelEntry(Long carId, double liters, double price, int odometer) {
        FuelEntry entry = new FuelEntry(carId, liters, price, odometer);
        entry.setId(idCounter.getAndIncrement());

        fuelEntries.computeIfAbsent(carId, k -> new ArrayList<>()).add(entry);
        return entry;
    }

    public List<FuelEntry> getFuelEntries(Long carId) {
        return fuelEntries.getOrDefault(carId, new ArrayList<>());
    }

    public Map<String, Object> getFuelStatistics(Long carId) {
        List<FuelEntry> entries = getFuelEntries(carId);

        if (entries.isEmpty()) {
            return Map.of(
                    "totalFuel", 0.0,
                    "totalCost", 0.0,
                    "averageConsumption", 0.0);
        }

        double totalFuel = entries.stream().mapToDouble(FuelEntry::getLiters).sum();
        double totalCost = entries.stream().mapToDouble(e -> e.getLiters() * e.getPrice()).sum();

        // Calculate average consumption per 100km
        entries.sort(Comparator.comparingInt(FuelEntry::getOdometer));
        int totalDistance = entries.get(entries.size() - 1).getOdometer() -
                entries.get(0).getOdometer();

        double averageConsumption = totalDistance > 0 ? (totalFuel / totalDistance) * 100 : 0;

        return Map.of(
                "totalFuel", Math.round(totalFuel * 100.0) / 100.0,
                "totalCost", Math.round(totalCost * 100.0) / 100.0,
                "averageConsumption", Math.round(averageConsumption * 100.0) / 100.0);
    }
}