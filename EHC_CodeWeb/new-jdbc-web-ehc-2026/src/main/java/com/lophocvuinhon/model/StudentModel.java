package com.lophocvuinhon.model;

public class StudentModel {
	private long userId;
	private String studentCode;
	private String major;
	private int enrollYear;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public int getEnrollYear() {
		return enrollYear;
	}
	public void setEnrollYear(int enrollYear) {
		this.enrollYear = enrollYear;
	}
	
}
