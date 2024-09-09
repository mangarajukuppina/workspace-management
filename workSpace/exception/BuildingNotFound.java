package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class BuildingNotFound extends RuntimeException{
	private String msg = " Building not found.";

	public BuildingNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public BuildingNotFound() {
		super();
	}
	
	

}
