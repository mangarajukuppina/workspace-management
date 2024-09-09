package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class InCorrectRating extends RuntimeException{
	String msg="rating must be less than 5";

	public InCorrectRating(String msg) {
		super();
		this.msg = msg;
	}

	public InCorrectRating() {
		super();
	}
	
	
	
	
	

}
