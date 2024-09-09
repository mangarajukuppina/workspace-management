package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ManagerNotFound extends RuntimeException{

	private String msg="Manager not found.";

	public ManagerNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public ManagerNotFound() {
		super();
	}
	

}
