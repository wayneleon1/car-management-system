package com.example.car_management.controller;

import com.example.car_management.service.FuelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/servlet/fuel-stats", name = "FuelServlet")
public class FuelServlet extends HttpServlet {

    @Autowired
    private FuelService fuelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() {
        // This enables Spring dependency injection in the Servlet
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        System.out.println("Servlet called! Query string: " + req.getQueryString());

        // Manually parse query parameter
        String carIdParam = req.getParameter("carId");

        if (carIdParam == null || carIdParam.isEmpty()) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"error\": \"carId parameter is required\"}");
            return;
        }

        try {
            Long carId = Long.parseLong(carIdParam);
            System.out.println("Getting stats for carId: " + carId);

            Map<String, Object> stats = fuelService.getFuelStatistics(carId);
            System.out.println("Stats: " + stats);

            // Set response headers manually
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            // Write JSON response
            objectMapper.writeValue(resp.getWriter(), stats);

        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "{\"error\": \"Invalid carId format\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "{\"error\": \"Internal server error: " + e.getMessage() + "\"}");
        }
    }

    private void sendError(HttpServletResponse resp, int status, String message)
            throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.getWriter().write(message);
    }
}