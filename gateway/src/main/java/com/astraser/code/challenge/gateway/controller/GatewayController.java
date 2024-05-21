package com.astraser.code.challenge.gateway.controller;

import com.astraser.code.challenge.gateway.analytics.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    @Autowired
    private RequestCounterService requestCounterService;

    @GetMapping("/request-counter")
    private ResponseEntity<?> getRequestsCounter() {
        return ResponseEntity.status(HttpStatus.OK).body(requestCounterService.getRequestCount());
    }
}
