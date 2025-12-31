# Car Management & Fuel Tracking System

A Java-based application for managing cars and tracking fuel consumption, built with **Spring Boot** (backend) and a standalone **CLI client** with HTTP communication.

---

## 📋 Project Structure

```
car-management-system/
├── backend/                 # Spring Boot REST API
│   ├── src/main/java/com/example/carmanagement/
│   │   ├── model/           # Data models (Car, FuelEntry)
│   │   ├── service/         # Business logic (CarService, FuelService)
│   │   ├── controller/      # REST endpoints and Servlet
│   │   ├── dto/             # Data Transfer Objects
│   │   └── CarManagementApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── cli-client/              # Standalone CLI application
│   ├── src/main/java/com/example/cli/
│   │   ├── Main.java        # CLI entry point
│   │   ├── CarCLI.java      # Business logic for CLI
│   │   └── HttpClientWrapper.java # HTTP client
│   ├── pom.xml
│   └── run.sh
└── README.md
```

---

## 🚀 Prerequisites

- **Java 17+**
- **Maven 3.6+**

---

## 🛠️ Building the Project

1. **Build Backend**

```bash
cd backend
mvn clean package
```

2. **Build CLI Client**

```bash
cd cli-client
mvn clean package
```

---

## ▶️ Running the Application

### Start Backend Server

```bash
cd backend
mvn spring-boot:run
```

The server will start on: `http://localhost:8080/api`

**Available Endpoints:**

| Method | Endpoint                           | Description                  |
| ------ | ---------------------------------- | ---------------------------- |
| POST   | /api/cars                          | Create a car                 |
| GET    | /api/cars                          | List all cars                |
| POST   | /api/cars/{id}/fuel                | Add fuel entry               |
| GET    | /api/cars/{id}/fuel/stats          | Get fuel statistics          |
| GET    | /api/servlet/fuel-stats?carId={id} | Get stats via custom servlet |

---

### Use CLI Client

Open a new terminal and run:

```bash
cd cli-client

# Using the run script
./run.sh --create-car --brand Toyota --model Corolla --year 2018

# Or directly with the JAR
java -jar target/cli-client-1.0-SNAPSHOT-jar-with-dependencies.jar --create-car --brand Toyota --model Corolla --year 2018
```

---

## 🖍️ CLI Commands

| Command      | Description        | Example                                                        |
| ------------ | ------------------ | -------------------------------------------------------------- |
| --create-car | Register a new car | --create-car --brand Toyota --model Corolla --year 2018        |
| --add-fuel   | Add fuel entry     | --add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000 |
| --fuel-stats | View statistics    | --fuel-stats --carId 1                                         |
| --list-cars  | List all cars      | --list-cars                                                    |
| --help       | Show help          | --help                                                         |

**Complete Example Session:**

```bash
# Create a car
./run.sh --create-car --brand Toyota --model Corolla --year 2018
# Output: ✅ Car created successfully! ID: 1

# Add first fuel entry
./run.sh --add-fuel --carId 1 --liters 40 --price 52.5 --odometer 45000
# Output: ✅ Fuel entry added successfully!

# Add second fuel entry
./run.sh --add-fuel --carId 1 --liters 35 --price 45.0 --odometer 45500

# View statistics
./run.sh --fuel-stats --carId 1
# Output: 📊 Fuel Statistics for Car ID 1
#          Total fuel: 75.0 L
#          Total cost: $3622.5
#          Average consumption: 150.0 L/100km

# List all cars
./run.sh --list-cars
```

---

## 🏗️ Architecture

### Backend (Spring Boot)

- **Models**: Plain Java objects (`Car`, `FuelEntry`) with proper encapsulation
- **Service**: Business logic using in-memory storage (`ConcurrentHashMap`, `AtomicLong`)
- **Controller**: REST API endpoints using Spring `@RestController`
- **Servlet**: Custom `HttpServlet` demonstrating manual request handling
- **DTO**: Optional Data Transfer Objects for input validation

### CLI Client

- **Standalone Module**: Independent Maven module
- **HTTP Communication**: Uses `java.net.http.HttpClient` for API calls
- **Argument Parsing**: Apache Commons CLI
- **Output Formatting**: User-friendly console output with emoji indicators
