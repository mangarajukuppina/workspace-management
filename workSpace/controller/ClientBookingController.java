package com.jsp.workSpace.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.service.ClientBookingService;
import com.jsp.workSpace.util.ResponceStructure;

import net.bytebuddy.asm.Advice.Return;

@RestController
@CrossOrigin
public class ClientBookingController {
	@Autowired
	private ClientBookingService clientBookingService;
	

	@PostMapping("/bookworkspaces")
	public ResponseEntity<ResponceStructure<List<WorkSpace>>> bookWorkSpaces(@RequestBody ClientBooking clientBooking, @RequestBody List<Integer> list,@RequestParam int clientId){
		return clientBookingService.bookWotkSpaces(clientBooking, clientId, list);
	}
	@PostMapping("/bookoneworkspaces")
	public ResponseEntity<ResponceStructure<WorkSpace>> bookOneWorkSpaces(@RequestBody ClientBooking clientBooking, @RequestParam int workspaceid, @RequestParam int clientId){
		return clientBookingService.bookOneWorkSpace(clientId, workspaceid, clientBooking);
	}
	@PostMapping("/paymentbooking")
	public ResponseEntity<ResponceStructure<ClientBooking>> payment(@RequestParam int client_id, @RequestParam int client_Bookin_id, @RequestParam String payment, @RequestParam double workSpaceCost){
		System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		return clientBookingService.payment(client_id, client_Bookin_id, payment, workSpaceCost);
	}
	
	@PutMapping("/acceptclientbooking")
	public ResponseEntity<ResponceStructure<ClientBooking>> acceptClientBooking(@RequestParam int id, @RequestParam String msg){
		return clientBookingService.acceptClientBooking(id, msg);
		
	}
	
	@DeleteMapping("/removeClientBooking")
	public ResponseEntity<ResponceStructure<WorkSpace>> removeClientBookingWorkSpace(@RequestParam int workSpace_id, @RequestParam int client_id, @RequestParam int clientBooking_id){
		return clientBookingService.removeWorkSpaceFromClient(workSpace_id, client_id, clientBooking_id);
	}
	
	@DeleteMapping("/removeClientBookingbyid")
	public ResponseEntity<ResponceStructure<ClientBooking>> removeClientBookingById( @RequestParam int client_id, @RequestParam int clientBooking_id){
		return clientBookingService.removeClientBookingById(client_id, clientBooking_id);
	
	}
	@GetMapping("/clientbooking")
public ResponseEntity<ResponceStructure<ClientBooking>> findClientBookingById(@RequestParam int clientBooking_id){
		return clientBookingService.findById(clientBooking_id);
	}
	
//	@GetMapping("requists")
//	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findRequists(){
//		return clientBookingService.findPendingClientBookins();
//	}
//	@GetMapping("/requists")
//	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findRequists(@RequestParam int building_id){
//		System.out.println("buildning id is "+building_id);
//		return clientBookingService.findPendingClientBookins(building_id);
//	}
	
	@GetMapping("/clietntrequists")
	public ResponseEntity<ResponceStructure<List<ClientBooking>>> fetchClientrequists(@RequestParam int building_id){
		System.out.println(building_id+" building id");
		return clientBookingService.findPendingClientBookins(building_id);
	}
	@GetMapping("/clientallbookings")
	public ResponseEntity<ResponceStructure<List<ClientBooking>>> fetchclientBookingsByClientId(@RequestParam int client_id){
		
		return clientBookingService.findClientBookingbyClientId(client_id);
	}
	
	@GetMapping("/findClientAcceptedClientBookngs")
public ResponseEntity<ResponceStructure<List<ClientBooking>>> findAcceptedClientBookings(@RequestParam int client_id){
		
		return clientBookingService.findAcceptedClientBookings(client_id);
	}
	

}
