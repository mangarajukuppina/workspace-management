package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ClientAlreadyExist extends RuntimeException {
	String msg="client already exist";

	public ClientAlreadyExist(String msg) {
		super();
		this.msg = msg;
	}

	public ClientAlreadyExist() {
		super();
	}
	

}
