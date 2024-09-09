package com.jsp.workSpace.util;

import java.util.List;

import com.jsp.workSpace.dto.Admin;

import lombok.Data;

@Data
public class ResponceStructure<T> {
	
	private String messege;
	private int status;
	private T data;

}
