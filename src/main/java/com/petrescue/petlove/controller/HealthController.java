package com.petrescue.petlove.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final DataSource dataSource;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        boolean dbUp;
        try (Connection c = dataSource.getConnection()) {
            dbUp = c != null && c.isValid(2);
        } catch (Exception e) {
            dbUp = false;
        }

        Map<String, Object> body = Map.of(
                "timestamp", OffsetDateTime.now(),
                "app", "UP",
                "db", dbUp ? "UP" : "DOWN"
        );

        HttpStatus status = dbUp ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(body);
    }
}
