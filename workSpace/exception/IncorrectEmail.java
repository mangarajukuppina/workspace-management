package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class IncorrectEmail extends RuntimeException{
	String msg="Email is incorrect";

	public IncorrectEmail(String msg) {
		super();
		this.msg = msg;
	}

	public IncorrectEmail() {
		super();
	}
	
	

}
