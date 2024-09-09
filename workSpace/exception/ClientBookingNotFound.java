package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ClientBookingNotFound extends RuntimeException {
	String msg="Client booking not found";

	public ClientBookingNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public ClientBookingNotFound() {
		super();
	}
	
	

}
