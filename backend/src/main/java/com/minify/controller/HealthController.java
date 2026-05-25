package com.minify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@GetMapping("/health")
	public String healthCheck() {
		return "Minify service is up and running!";
	}
}
