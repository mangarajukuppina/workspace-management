package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ClientNotFound extends RuntimeException{
	
	String msg="client not exist";

	public ClientNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public ClientNotFound() {
		super();
	}
	
	

}
