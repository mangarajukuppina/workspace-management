package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class AdminNotFound extends RuntimeException {
	
	
	private String msg = "Admin not found please try with different one.";

	public AdminNotFound(String msg) {
		super();
		this.msg = msg;
	}
	
	public AdminNotFound() {
		super();
		
	}
	

}
