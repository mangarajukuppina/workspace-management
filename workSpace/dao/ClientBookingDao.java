package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.repo.ClientBookingRepo;
import com.jsp.workSpace.util.ResponceStructure;

@Repository
public class ClientBookingDao {
	@Autowired
	private ClientBookingRepo clientBookingRepo;
	
	
	
	public ClientBooking save(ClientBooking clientBooking) {
		ClientBooking dbClient = clientBookingRepo.save(clientBooking);
		if(dbClient!=null) {
			return dbClient;
		}
		else {
			return null;
		}
		
	}
	public ClientBooking findById(int id) {
		Optional<ClientBooking> dbClient = clientBookingRepo.findById(id);
		if(dbClient.isPresent()) {
			return dbClient.get();
		}
		else {
			return null;
		}
	}
	
	public List<ClientBooking> saveAll(List<ClientBooking> clientBookings) {
		  List<ClientBooking> dbClient = clientBookingRepo.saveAll(clientBookings);
		if(dbClient.isEmpty()) {
			return null;
		}
		else {
			return dbClient;
		}
		
	}
	public List<ClientBooking> findAllClientBookings(){
		List<ClientBooking> dbList=clientBookingRepo.findAll();
		if(dbList.isEmpty()) {
			return null;
		}
		else {
			return dbList;
		}
	}
	public ClientBooking deleteClientBooking(int clientBooking_id) {
		if(clientBookingRepo.findById(clientBooking_id).isPresent()) {
			Optional<ClientBooking> dbclientBooking = clientBookingRepo.findById(clientBooking_id);
			clientBookingRepo.deleteById(clientBooking_id);
			return dbclientBooking.get();
			
			
		}
		else {
			return null;
		}
	}
//	public List<ClientBooking> findPendingClientBooking(String pending){
//		 List<ClientBooking> list = clientBookingRepo.findPendingClientBooking(pending);
//		 if(list.isEmpty()) {
//			 return null;
//		 }
//		 else {
//			 return null;
//		 }
//		
//	}
	
	

}
