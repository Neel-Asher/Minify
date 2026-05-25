package com.minify.dto;

import java.time.LocalDateTime;

public class UrlResponse {
	
	private long id;
	private String url;
	private String shortCode;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private long accessCount;
	
	public UrlResponse() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getShortCode() {
		return shortCode;
	}
	
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public long getAccessCount() {
		return accessCount;
	}
	
	public void setAccessCount(long accessCount) {
		this.accessCount = accessCount;
	}
}
