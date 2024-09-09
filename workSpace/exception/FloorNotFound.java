package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class FloorNotFound extends RuntimeException{
	String msg="floor not found";

	public FloorNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public FloorNotFound() {
		super();
	}
	
	
	

}
