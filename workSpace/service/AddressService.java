package com.jsp.workSpace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.dao.AddressDao;
import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.exception.AddressNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class AddressService {
	@Autowired
	private AddressDao addressDao;
	 public ResponseEntity<ResponceStructure<Address>> saveAddress(Address address){
		 Address dbAddress=addressDao.saveAddress(address);
		 ResponceStructure<Address> structure=new ResponceStructure<Address>();
		 structure.setMessege("address successfully saves");
		 structure.setStatus(HttpStatus.CREATED.value());
		 structure.setData(dbAddress);
		 return new ResponseEntity<ResponceStructure<Address>>(structure, HttpStatus.CREATED);
	 }
	 
	 public ResponseEntity<ResponceStructure<Address>> updateAddress(Address address){
		 Address dbAddress = addressDao.findById(address.getId());
		 if(dbAddress!=null) {
			Address updatedAddress = addressDao.updateAddress(address);
			ResponceStructure<Address> structure=new ResponceStructure<Address>();
			structure.setMessege("Address updated succcessfully....");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(updatedAddress);
			return new ResponseEntity<ResponceStructure<Address>>(structure, HttpStatus.OK);
		 }
		 else {
			 throw new AddressNotFound("address not found with id = "+address.getId());
		 }
		 
	 }
	 
	 
	 
	 public ResponseEntity<ResponceStructure<List<Address>>> findAddressByCityName(String name){
		 List<Address> dblist=addressDao.findAddressByCityName(name);
		 if(dblist!=null) {
			 ResponceStructure<List<Address>> structure=new ResponceStructure<List<Address>>();
			 structure.setMessege("list of addresses are found");
			 structure.setStatus(HttpStatus.FOUND.value());
			 structure.setData(dblist);
			 return new ResponseEntity<ResponceStructure<List<Address>>>(structure, HttpStatus.FOUND);
		 }
		 else {
			 throw new AddressNotFound("Address not found with city name = "+name);
		 }
	 }
	 public ResponseEntity<ResponceStructure<Address>> findById(int id){
		 Address dbAddress = addressDao.findById(id);
		 if(dbAddress!=null) {
			 ResponceStructure<Address> structure=new ResponceStructure<Address>();
			 structure.setMessege("Address found successfully....");
			 structure.setStatus(HttpStatus.FOUND.value());
			 structure.setData(dbAddress);
			 return  new ResponseEntity<ResponceStructure<Address>>(structure, HttpStatus.FOUND);
		 }
		 else {
			 throw new AddressNotFound("Address not exis with id = "+id);
		 }
	 }

}
