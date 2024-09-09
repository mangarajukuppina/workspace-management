package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class AddressNotFound extends RuntimeException{
	
	String msg="Address not found";

	public AddressNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public AddressNotFound() {
		super();
	}
	
	

}
