package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class IncorrectPassword extends RuntimeException{
	
	String msg="Password is incorrect";

	public IncorrectPassword(String msg) {
		super();
		this.msg = msg;
	}

	public IncorrectPassword() {
		super();
	}
	
	

}
