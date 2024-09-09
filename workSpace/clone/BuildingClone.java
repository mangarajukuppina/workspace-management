package com.jsp.workSpace.clone;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.dto.Manager;

import lombok.Data;

@Data
public class BuildingClone {
	private int id;
	private String building_Name;
	private int rating;
	private Address address;
	private List<Floor> floors;

}
