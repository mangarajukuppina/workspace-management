package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ManagerAlreadyExist extends RuntimeException{  //make class as exception class
	String msg="Mangager already exist";

	public ManagerAlreadyExist(String msg) {
		super();
		this.msg = msg;
	}

	public ManagerAlreadyExist() {
		super();
	}
	

}
