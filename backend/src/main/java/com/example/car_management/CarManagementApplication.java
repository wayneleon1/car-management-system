package com.example.car_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // This scans for @WebServlet annotations
public class CarManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarManagementApplication.class, args);
	}

}