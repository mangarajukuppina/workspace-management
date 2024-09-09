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

import com.jsp.workSpace.clone.AdminClone;
import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.service.AdminService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/admin")
	public  ResponseEntity<ResponceStructure<AdminClone>> saveAdmin(@RequestBody Admin admin) throws SQLIntegrityConstraintViolationException { 
		//Responce entity is used to get perfect status code
		return adminService.saveAdmin(admin);
	}
	@GetMapping("/adminlogin")
	public ResponseEntity<ResponceStructure<Admin>> login(@RequestParam String email, @RequestParam String password){
		return adminService.login(email, password);
	}
	@GetMapping("/fetchadmin")
	public ResponseEntity<ResponceStructure<Admin>> fetchAdmin(@RequestParam  int id){
		return adminService.findAdminById(id);
		
	}
	@GetMapping("/fetchadminbyname")
	public ResponseEntity<ResponceStructure<List<Admin>>> fetchAdminByName(@RequestParam String name){
		return adminService.findAdminByName(name);
	}

	@GetMapping("/fetchalladmin")
	public ResponseEntity<ResponceStructure<List<Admin>>> fetchAllAdmin(){
		return adminService.fetchAllAdmin();
	}
	
	
	@PutMapping("/admin")
	public ResponseEntity<ResponceStructure<Admin>> updateAdmin(@RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}
	
//	@PutMapping("/assignaddress")
//	public ResponseEntity<ResponceStructure<Admin>> assignAddress(@RequestParam int adminId, @RequestParam int addressId){
//		System.out.println(adminId);
//		System.out.println(addressId);
//		return adminService.assignAddress(adminId, addressId);
//	}
	@DeleteMapping("/admin")
	public ResponseEntity<ResponceStructure<Admin>> deleteById(@RequestParam int id){
		return adminService.deleteAdminById(id);
	}
	@DeleteMapping("/admindeleteall")
	public ResponseEntity<ResponceStructure<Admin>> deleteAllAdminById(@RequestParam int id){
		return adminService.deleteAllAdminById(id);
	}
	
	}
	//===================================================================================
//save admin 

//{
//	  "address": {
//	    "city": "hyderbad",
//	    "district": "ts",
//	    "door_No": "2-69",
//	    
//	    "landmark": "ameerpeta",
//	    "pincode": 500016,
//	    "state": "ts",
//	    "street": "yelllareddyguda"
//	  },
//	  "email": "raju@gmail.com",
//	  "gender": "male",
//	   
//	 
//	  "name": "Manga Raju",
//	  "password": "Raju@2000",
//	  "phone": 7997409901
//	}


