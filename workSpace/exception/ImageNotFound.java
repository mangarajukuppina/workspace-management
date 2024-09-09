package com.jsp.workSpace.exception;

import lombok.Data;

@Data
public class ImageNotFound extends RuntimeException{
	

	private String msg = "Image Not found!.";

	public ImageNotFound(String msg) {
		super();
		this.msg = msg;
	}

	public ImageNotFound() {
		super();
	}
	
	

}
