package com.minify.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.minify.dto.UrlRequest;
import com.minify.dto.UrlResponse;
import com.minify.entity.UrlMapping;
import com.minify.service.UrlMappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shorten")
public class UrlMappingController {

    private final UrlMappingService service;
    private static final Logger log = LoggerFactory.getLogger(UrlMappingController.class);

    public UrlMappingController(UrlMappingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UrlResponse> create(@Valid @RequestBody UrlRequest request) {

        log.info("Create request received for URL: {}", request.getUrl());
        UrlMapping created = service.createShortUrl(request.getUrl());
        log.info("Short URL created with code: {}", created.getShortCode());
        return ResponseEntity.status(201).body(service.mapToResponse(created));
    }

    @GetMapping("/{code}")
    public ResponseEntity<UrlResponse> get(@PathVariable String code) {

        log.info("Fetch request for shortCode: {}", code);
        Optional<UrlMapping> result = service.findByShortCode(code);
        return result
                .map(m -> {
                    log.info("ShortCode found: {}, URL: {}", code, m.getUrl());
                    return ResponseEntity.ok(service.mapToResponse(m));
                })
                .orElseGet(() -> {
                    log.warn("ShortCode not found: {}", code);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/{code}/stats")
    public ResponseEntity<UrlResponse> getStats(@PathVariable String code) {

        Optional<UrlMapping> result = service.getUrlMappingWithoutTracking(code);

        return result
                .map(m -> ResponseEntity.ok(service.mapToResponse(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/r/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {

        log.info("Redirect request received for: {}", code);

        return service.trackAndGetByShortCode(code)
                .map(mapping -> {
                    log.info("Redirecting {} -> {}", code, mapping.getUrl());
                    return ResponseEntity
                            .status(302)
                            .header("Location", mapping.getUrl())
                            .build();
                })
                .orElseGet(() -> {
                    log.warn("Redirect failed, code not found: {}", code);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{code}")
    public ResponseEntity<UrlResponse> update(@PathVariable String code,
                                              @RequestBody UrlRequest request) {

        Optional<UrlMapping> updated = service.updateUrl(code, request.getUrl());

        return updated
                .map(m -> ResponseEntity.ok(service.mapToResponse(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {

        boolean deleted = service.deleteUrl(code);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}