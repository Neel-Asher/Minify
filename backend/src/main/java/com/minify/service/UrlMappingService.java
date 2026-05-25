package com.minify.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.minify.dto.UrlResponse;
import com.minify.entity.UrlMapping;
import com.minify.repository.UrlMappingRepository;

@Service
public class UrlMappingService {

	private final UrlMappingRepository repository;
	private static final Logger log = LoggerFactory.getLogger(UrlMappingService.class);
	
	public UrlMappingService(UrlMappingRepository repository) {
		
		this.repository = repository;
	}
	
	public UrlMapping createShortUrl(String originalUrl) {

	    log.info("Generating short URL for: {}", originalUrl);

	    UrlMapping urlMapping = new UrlMapping();

	    urlMapping.setUrl(originalUrl);
	    urlMapping.setShortCode(generateUniqueShortCode());
	    urlMapping.setCreatedAt(LocalDateTime.now());
	    urlMapping.setUpdatedAt(LocalDateTime.now());
	    urlMapping.setAccessCount(0);

	    UrlMapping saved = repository.save(urlMapping);

	    log.info("Saved mapping: {} -> {}", saved.getShortCode(), saved.getUrl());

	    return saved;
	}
	
	public Optional<UrlMapping> getByShortCode(String shortCode) {
		
		Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);
		
		mapping.ifPresent(m -> {
			m.setAccessCount(m.getAccessCount()+1);
			repository.save(m);
		});
		
		return mapping;
 	}
	
	public Optional<UrlMapping> findByShortCode(String shortCode) {
	    return repository.findByShortCode(shortCode);
	}
	
	public Optional<UrlMapping> trackAndGetByShortCode(String shortCode) {

	    log.info("Tracking access for shortCode: {}", shortCode);

	    Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);

	    mapping.ifPresent(m -> {
	        m.setAccessCount(m.getAccessCount() + 1);
	        repository.save(m);

	        log.info("Access count updated for {} -> {}", shortCode, m.getAccessCount());
	    });

	    return mapping;
	}
	
	public Optional<UrlMapping> updateUrl(String shortCode, String newUrl) {
		
		Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);
		
		if (mapping.isPresent()) {
			
			UrlMapping urlMapping = mapping.get();
            urlMapping.setUrl(newUrl);
            urlMapping.setUpdatedAt(LocalDateTime.now());
            repository.save(urlMapping);
		}
		
		return mapping;
	}
	
	public boolean deleteUrl(String shortCode) {
		
		Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);
		
			if (mapping.isPresent()) {
				repository.delete(mapping.get());
				return true;
			}
		
		return false;
	}
	
	public String generateShortCode() {
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		
		StringBuilder code = new StringBuilder();
		
		for (int i=0; i<6; i++) code.append(characters.charAt(random.nextInt(characters.length())));
		
		return code.toString();
	}
	
	public String generateUniqueShortCode() {
		
		String shortCode;
		
		do {
			shortCode = generateShortCode();
		} while (repository.findByShortCode(shortCode).isPresent());
		
		return shortCode;
	}
	
	public UrlResponse mapToResponse(UrlMapping mapping) {

	    UrlResponse response = new UrlResponse();

	    response.setId(mapping.getId());
	    response.setUrl(mapping.getUrl());
	    response.setShortCode(mapping.getShortCode());
	    response.setCreatedAt(mapping.getCreatedAt());
	    response.setUpdatedAt(mapping.getUpdatedAt());
	    response.setAccessCount(mapping.getAccessCount());

	    return response;
	}
	
	public Optional<UrlMapping> getUrlMappingWithoutTracking(String shortCode) {
		return repository.findByShortCode(shortCode);
	}
	
	public Optional<UrlMapping> getAndTrack(String shortCode) {

	    Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);

	    mapping.ifPresent(m -> {
	        m.setAccessCount(m.getAccessCount() + 1);
	        repository.save(m);
	    });

	    return mapping;
	}
}