package com.example.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class CarCLI {
    private final HttpClientWrapper httpClient;
    private final ObjectMapper objectMapper;

    public CarCLI(HttpClientWrapper httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public void createCar(String brand, String model, int year) {
        try {
            System.out.println("Creating car: " + brand + " " + model + " (" + year + ")");

            Map<String, Object> carData = new HashMap<>();
            carData.put("brand", brand);
            carData.put("model", model);
            carData.put("year", year);

            String jsonBody = objectMapper.writeValueAsString(carData);
            HttpResponse<String> response = httpClient.post("/cars", jsonBody);

            if (response.statusCode() == 201) {
                JsonNode node = objectMapper.readTree(response.body());
                System.out.println("✅ Car created successfully!");
                System.out.println("   ID: " + node.get("id").asText());
                System.out.println("   Brand: " + node.get("brand").asText());
                System.out.println("   Model: " + node.get("model").asText());
                System.out.println("   Year: " + node.get("year").asText());
            } else {
                System.out.println("❌ Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Error creating car: " + e.getMessage());
        }
    }

    public void addFuelEntry(long carId, double liters, double price, int odometer) {
        try {
            System.out.println("Adding fuel entry for car ID: " + carId);

            Map<String, Object> fuelData = new HashMap<>();
            fuelData.put("liters", liters);
            fuelData.put("price", price);
            fuelData.put("odometer", odometer);

            String jsonBody = objectMapper.writeValueAsString(fuelData);
            HttpResponse<String> response = httpClient.post("/cars/" + carId + "/fuel", jsonBody);

            if (response.statusCode() == 201) {
                System.out.println("✅ Fuel entry added successfully!");
            } else {
                System.out.println("❌ Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Error adding fuel entry: " + e.getMessage());
        }
    }

    public void getFuelStats(long carId) {
        try {
            System.out.println("Getting fuel statistics for car ID: " + carId);

            HttpResponse<String> response = httpClient.get("/cars/" + carId + "/fuel/stats");

            if (response.statusCode() == 200) {
                JsonNode stats = objectMapper.readTree(response.body());
                System.out.println("\n📊 Fuel Statistics for Car ID " + carId);
                System.out.println("   Total fuel: " + stats.get("totalFuel").asText() + " L");
                System.out.println("   Total cost: $" + stats.get("totalCost").asText());
                System.out.println("   Average consumption: " + stats.get("averageConsumption").asText() + " L/100km");
                System.out.println();
            } else {
                System.out.println("❌ Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Error getting fuel stats: " + e.getMessage());
        }
    }

    public void listCars() {
        try {
            System.out.println("Listing all cars...");

            HttpResponse<String> response = httpClient.get("/cars");

            if (response.statusCode() == 200) {
                JsonNode cars = objectMapper.readTree(response.body());
                System.out.println("\n🚗 All Cars:");
                System.out.println("============");

                for (JsonNode car : cars) {
                    System.out.println("ID: " + car.get("id").asText());
                    System.out.println("  Brand: " + car.get("brand").asText());
                    System.out.println("  Model: " + car.get("model").asText());
                    System.out.println("  Year: " + car.get("year").asText());
                    System.out.println();
                }
            } else {
                System.out.println("❌ Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("❌ Error listing cars: " + e.getMessage());
        }
    }
}