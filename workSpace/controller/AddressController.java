package com.jsp.workSpace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.service.AddressService;
import com.jsp.workSpace.util.ResponceStructure;
@RestController
@CrossOrigin
public class AddressController {
	@Autowired
	private AddressService addressService;
	@PostMapping("/address")
	public ResponseEntity<ResponceStructure<Address>> saveAddress(@RequestBody Address address){
		return addressService.saveAddress(address);
	}
	@PutMapping("/address")
	public ResponseEntity<ResponceStructure<Address>> updateAddress(@RequestBody Address address){
		return addressService.updateAddress(address);
	}
	@GetMapping("/address")
	public ResponseEntity<ResponceStructure<Address>> findAddresById(@RequestParam int addressId){
		return addressService.findById(addressId);
	}

}
