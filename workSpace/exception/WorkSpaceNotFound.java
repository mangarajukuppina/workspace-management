package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class WorkSpaceNotFound extends RuntimeException{
	
	String msg="WorkSPace not found";

	public WorkSpaceNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public WorkSpaceNotFound() {
		super();
	}
	
	

}
