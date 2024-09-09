package com.jsp.workSpace.clone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;

import com.jsp.workSpace.dto.Address;

import lombok.Data;

@Data
public class ManagerClone {
	private int id;
	private String name;
	private String email;
	private long phone;
	private String gender;
	private int experience;
	private Address address;


}
