package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ManagerAlredyAssingnAnotherBuilding  extends RuntimeException{
	private String msg = "manager alredy assingn another building";

	public ManagerAlredyAssingnAnotherBuilding(String msg) {
		super();
		this.msg = msg;
	}
	public ManagerAlredyAssingnAnotherBuilding() {
		super();
		
	}
	
	

}
