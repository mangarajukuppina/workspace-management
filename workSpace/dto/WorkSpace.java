package com.jsp.workSpace.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.jsp.workSpace.enums.WorkSpaceType;

import lombok.Data;

@Data
@Entity
public class WorkSpace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int capacity;
	private WorkSpaceType type;
	private double pricePerday;
	private String availability;
	private String squareFeet;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Clint> client;

}
