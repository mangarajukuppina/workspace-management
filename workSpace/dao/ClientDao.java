package com.jsp.workSpace.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Clint;
import com.jsp.workSpace.repo.ClintRepo;


@Repository

public class ClientDao {

	@Autowired
	private ClintRepo clientRepo;
	
	
	public Clint saveClient(Clint client) {
		return clientRepo.save(client);
	}
	public List<Clint> findAllClients(){
		List<Clint> dbList=clientRepo.findAll();
		if(dbList.isEmpty()) {
			return null;
		}
		else {
			return dbList;
		}
	}
	public Clint findClientByEmail(String email) {
		Clint dbClient = clientRepo.findClientByEmail(email);
		if(dbClient!=null) {
			return dbClient;
		}
		else {
			return null;
		}
		
	}
	public Clint findbyId(int id) {
		Optional<Clint> dbClient = clientRepo.findById(id);
		if(dbClient.isPresent()) {
			return dbClient.get();
		}
		else {
			return null;
		}
		
	}
	public Clint deleteClientById(int id) {
		if(clientRepo.findById(id).isPresent()) {
			Clint dbClient = clientRepo.findById(id).get();
			clientRepo.deleteById(id);
			return dbClient;
		
		}else {
			return null;
		}
	}

}
