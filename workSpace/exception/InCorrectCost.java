package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class InCorrectCost extends RuntimeException {
	String msg="enter correct amount..";

	public InCorrectCost(String msg) {
		super();
		this.msg = msg;
	}

	public InCorrectCost() {
		super();
	}
	
	
	

}
