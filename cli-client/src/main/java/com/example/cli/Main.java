package com.example.cli;

import org.apache.commons.cli.*;

public class Main {
    private static final String API_BASE_URL = "http://localhost:8080/api";

    public static void main(String[] args) {
        System.out.println("🚗 Car Management CLI");
        System.out.println("=====================");

        try {
            HttpClientWrapper httpClient = new HttpClientWrapper(API_BASE_URL);
            CarCLI carCLI = new CarCLI(httpClient);

            Options options = createOptions();

            if (args.length == 0) {
                printHelp(options);
                return;
            }

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("create-car")) {
                handleCreateCar(cmd, carCLI);
            } else if (cmd.hasOption("add-fuel")) {
                handleAddFuel(cmd, carCLI);
            } else if (cmd.hasOption("fuel-stats")) {
                handleFuelStats(cmd, carCLI);
            } else if (cmd.hasOption("list-cars")) {
                handleListCars(carCLI);
            } else if (cmd.hasOption("help")) {
                printHelp(options);
            } else {
                System.out.println("Unknown command. Use --help for usage.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            if (e instanceof ParseException) {
                printHelp(createOptions());
            }
        }
    }

    private static Options createOptions() {
        Options options = new Options();

        // Create car
        options.addOption("c", "create-car", false, "Create a new car");
        options.addOption("b", "brand", true, "Car brand");
        options.addOption("m", "model", true, "Car model");
        options.addOption("y", "year", true, "Manufacturing year");

        // Add fuel
        options.addOption("a", "add-fuel", false, "Add fuel entry");
        options.addOption("i", "carId", true, "Car ID");
        options.addOption("l", "liters", true, "Fuel liters");
        options.addOption("p", "price", true, "Fuel price per liter");
        options.addOption("o", "odometer", true, "Odometer reading");

        // Fuel stats
        options.addOption("s", "fuel-stats", false, "Get fuel statistics");

        // List cars
        options.addOption("L", "list-cars", false, "List all cars");

        // Help
        options.addOption("h", "help", false, "Show help");

        return options;
    }

    private static void handleCreateCar(CommandLine cmd, CarCLI carCLI) {
        if (!cmd.hasOption("brand") || !cmd.hasOption("model") || !cmd.hasOption("year")) {
            System.out.println("❌ Error: --brand, --model, and --year are required for create-car");
            return;
        }

        String brand = cmd.getOptionValue("brand");
        String model = cmd.getOptionValue("model");
        int year = Integer.parseInt(cmd.getOptionValue("year"));

        carCLI.createCar(brand, model, year);
    }

    private static void handleAddFuel(CommandLine cmd, CarCLI carCLI) {
        if (!cmd.hasOption("carId") || !cmd.hasOption("liters") ||
                !cmd.hasOption("price") || !cmd.hasOption("odometer")) {
            System.out.println("❌ Error: --carId, --liters, --price, and --odometer are required for add-fuel");
            return;
        }

        long carId = Long.parseLong(cmd.getOptionValue("carId"));
        double liters = Double.parseDouble(cmd.getOptionValue("liters"));
        double price = Double.parseDouble(cmd.getOptionValue("price"));
        int odometer = Integer.parseInt(cmd.getOptionValue("odometer"));

        carCLI.addFuelEntry(carId, liters, price, odometer);
    }

    private static void handleFuelStats(CommandLine cmd, CarCLI carCLI) {
        if (!cmd.hasOption("carId")) {
            System.out.println("❌ Error: --carId is required for fuel-stats");
            return;
        }

        long carId = Long.parseLong(cmd.getOptionValue("carId"));
        carCLI.getFuelStats(carId);
    }

    private static void handleListCars(CarCLI carCLI) {
        carCLI.listCars();
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("car-cli", options);
        System.out.println("\n📚 Examples:");
        System.out.println("  --create-car --brand Toyota --model Corolla --year 2018");
        System.out.println("  --add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000");
        System.out.println("  --fuel-stats --carId 1");
        System.out.println("  --list-cars");
    }
}