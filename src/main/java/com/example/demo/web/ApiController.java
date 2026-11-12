package com.example.demo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        return Map.of(
                "user", auth.getName(),
                "roles", auth.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .toList()
        );
    }

    @GetMapping("/items")
    public Map<String, String> listItems() {
        return Map.of("message", "List of items — accessible to USER and ADMIN");
    }

    @PostMapping("/items")
    public ResponseEntity<Map<String, String>> createItem(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(201).body(Map.of(
                "message", "Item created — ADMIN only",
                "name", body.getOrDefault("name", "")
        ));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
