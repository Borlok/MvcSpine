package com.borlok.mvcSpine.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Erofeevskiy Yuriy on 29.05.2024
 */

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("HYI");
    }
}
