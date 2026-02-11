package com.example.demo.dto;



public class ApiError {	
	private int status;
	private String message;
	private long timestamp=System.currentTimeMillis();
	
	public ApiError(int status,String message) {
		this.message=message;
		this.status=status;
	
	}
	
	public int getStatus() {return status;}
	public String getMessage() {return message;}
	public long getTimestamp() {return timestamp;}
	
}
