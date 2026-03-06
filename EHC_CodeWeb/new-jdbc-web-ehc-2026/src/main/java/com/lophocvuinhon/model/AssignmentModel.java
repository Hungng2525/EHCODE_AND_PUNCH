package com.lophocvuinhon.model;

import java.sql.Timestamp;

public class AssignmentModel {
	private long id;
	private String title;
	private String description;
	private String filePath;
	private long createdById; // id teacher
	private Timestamp createdAt;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}
