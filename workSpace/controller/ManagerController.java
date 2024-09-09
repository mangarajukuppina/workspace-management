package com.jsp.workSpace.controller;

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

import com.jsp.workSpace.clone.ManagerClone;
import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.service.ManagerService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/manager")
	public ResponseEntity<ResponceStructure<ManagerClone>> saveManager(@RequestBody Manager manager){
		
		return managerService.saveManager(manager);
	}
	@GetMapping("managerlogin")
	public ResponseEntity<ResponceStructure<ManagerClone>> loginManager(@RequestParam String email, @RequestParam String password){
		return managerService.login(email, password);
	}
	@GetMapping("managerbyemail")
	public ResponseEntity<ResponceStructure<ManagerClone>> loginManager(@RequestParam String email){
		return managerService.findManagerByemail(email);
	}
	@GetMapping("/managerbyid")
	public ResponseEntity<ResponceStructure<ManagerClone>> findManagerByID(@RequestParam int id){
		return managerService.findManagerById(id);
	}
	
	@GetMapping("/managerbyexperience")
	public ResponseEntity<ResponceStructure<List<ManagerClone>>> findManagerByExperience(@RequestParam int experience){
		return managerService.findManagerByExperience(experience);
	}
	@GetMapping("/managerall")
	public ResponseEntity<ResponceStructure<List<Manager>>> findAllmanager(){
	   return managerService.findAllManager();
	}
	@PutMapping("/manager")
	public ResponseEntity<ResponceStructure<Manager>> updateManager(@RequestBody Manager manager){
		return managerService.updateManager(manager);
	}
	@DeleteMapping("/manager")
	public ResponseEntity<ResponceStructure<ManagerClone>> deleteManager(@RequestParam int id){
		return managerService.deleteManagerById(id);
	}
	@GetMapping("/managerUnAssigned")
	public ResponseEntity<ResponceStructure<List<Manager>>> unAssignedManagers(){
		return managerService.unAssignedManagers();
	}

}
