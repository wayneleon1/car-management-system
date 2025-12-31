# 🚀 Backend Server - Car Management API

## Overview

Spring Boot REST API with manual Servlet implementation for car and fuel tracking.

## Features

- RESTful endpoints with JSON
- Manual HttpServlet demonstration
- In-memory storage with thread safety
- Input validation and error handling

## API Endpoints

| Endpoint                | Method | Description      |
| ----------------------- | ------ | ---------------- |
| `/cars`                 | POST   | Create car       |
| `/cars`                 | GET    | List cars        |
| `/cars/{id}/fuel`       | POST   | Add fuel entry   |
| `/cars/{id}/fuel/stats` | GET    | Get statistics   |
| `/servlet/fuel-stats`   | GET    | Servlet endpoint |

## Running

```bash
mvn spring-boot:run
# or
java -jar target/car-management-0.0.1-SNAPSHOT.jar
```
