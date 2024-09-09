package com.jsp.workSpace.clone;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.dto.ClientBooking;

import lombok.Data;

@Data
public class ClientClone {
	private int id;
	private String name;
	private String email;
	private long phone;
	private String gender;
	private int age;
	private String id_proof;
	private List<ClientBooking> bookings;

}
