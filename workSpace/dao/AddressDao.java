package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.repo.AddressRepo;

@Repository
public class AddressDao {
	@Autowired
	private AddressRepo addressRepo;

	public Address saveAddress(Address address) {
		return addressRepo.save(address);
	}

	public Address findById(int id) {
		if (addressRepo.findById(id).isPresent()) {
			return addressRepo.findById(id).get();
		} else {
			return null;
		}
	}

	public Address deleteAddressById(int id) {
		if (addressRepo.findById(id).isPresent()) {
			Address dbAddress = addressRepo.findById(id).get();
			addressRepo.deleteById(id);

			return dbAddress;
		} else {
			return null;
		}
	}

	public List<Address> findAddressByCityName(String name) {
		List<Address> dbAddress = addressRepo.findByCityName(name);
		if (dbAddress != null) {
			return dbAddress;
		} else {
			return null;
		}
	}

	public Address updateAddress(Address address) {
		if (addressRepo.findById(address.getId()).isPresent()){
			Optional<Address> dbAddress = addressRepo.findById(address.getId());
			if (address.getDoor_No() != null) {
				dbAddress.get().setDoor_No(address.getDoor_No());
			}
			if (address.getLandmark() != null) {
				
				dbAddress.get().setLandmark(address.getLandmark());
			}
			if (address.getStreet() != null) {
				dbAddress.get().setStreet(address.getStreet());
			}
			if (address.getCity() != null) {
				dbAddress.get().setCity(address.getCity());
			}
			if (address.getDistrict() != null) {
				dbAddress.get().setDistrict(address.getDistrict());
			}
			if (address.getState() != null) {
				dbAddress.get().setState(address.getState());
			}
			if (address.getPincode() != 0) {
				dbAddress.get().setPincode(address.getPincode());
			}
			return addressRepo.save(dbAddress.get());

		} else {
			return null;
		}
	}

}
