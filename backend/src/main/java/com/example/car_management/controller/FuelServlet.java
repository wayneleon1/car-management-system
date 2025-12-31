package com.example.car_management.controller;

import com.example.car_management.service.FuelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@WebServlet("/servlet/fuel-stats") // Maps to URL like Express app.get()
public class FuelServlet extends HttpServlet {

    @Autowired
    private FuelService fuelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // Manually parse query parameter (like req.query in Express)
        String carIdParam = req.getParameter("carId");

        if (carIdParam == null || carIdParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"carId parameter is required\"}");
            return;
        }

        try {
            Long carId = Long.parseLong(carIdParam);
            Map<String, Object> stats = fuelService.getFuelStatistics(carId);

            // Set response headers manually
            resp.setStatus(HttpServletResponse.SC_OK); // 200
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            // Write JSON response
            objectMapper.writeValue(resp.getWriter(), stats);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"Invalid carId format\"}");
        }
    }
}