package com.jsp.workSpace.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.workSpace.clone.ClientClone;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.dto.Clint;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.service.ClientBookingService;
import com.jsp.workSpace.service.ClientService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class ClientController {
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/client")
	public ResponseEntity<ResponceStructure<ClientClone>>  saveClient(@RequestBody Clint client) throws SQLIntegrityConstraintViolationException{
		return clientService.saveClient(client);
	}
	
	//Integer[] i= {1,2,3};
	@GetMapping("/clientlogin")
	public ResponseEntity<ResponceStructure<Clint>> loginClient(@RequestParam String email, @RequestParam String password){
		System.out.println(password);
		return clientService.loginClient(email, password);
	}
	@GetMapping("/clientbyid")
	public ResponseEntity<ResponceStructure<ClientClone>> findClientById(@RequestParam int client_Id){
		return clientService.findClientByOId(client_Id);
	}
	@GetMapping("/clientbyemail")
	public ResponseEntity<ResponceStructure<Clint>> findClientByEmail(@RequestParam String client_email){
		return clientService.findClientByEmail(client_email);
	}
	@PutMapping("/Client")
	public ResponseEntity<ResponceStructure<ClientClone>> updateClient(@RequestBody Clint client) throws SQLIntegrityConstraintViolationException{
		return clientService.updateClient(client);
	}
	@DeleteMapping("/client")
	public ResponseEntity<ResponceStructure<ClientClone>> deleteClientById(@RequestParam int client_Id){
		return clientService.deleteClientById(client_Id);
	}
	


}


