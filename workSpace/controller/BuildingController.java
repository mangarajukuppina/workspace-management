package com.jsp.workSpace.controller;

import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
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

import com.jsp.workSpace.clone.BuildingClone;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.service.BuildingService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class BuildingController {
	@Autowired
	private BuildingService buildingService;
	
	@PostMapping("/building")
	public ResponseEntity<ResponceStructure<BuildingClone>> saveBuilding(@RequestBody Building building, @RequestParam int adminId, @RequestParam int managerId	){
		return buildingService.saveBuilding(building, adminId, managerId);
	}
	@PutMapping("/building")
	public ResponseEntity<ResponceStructure<BuildingClone>> updatebuilding(@RequestBody Building building){
		return buildingService.updateBuilding(building);
	}
	@GetMapping("/buildingbyid")
	public ResponseEntity<ResponceStructure<BuildingClone>> findBuildingById(@RequestParam int id){
		return buildingService.findBuildingById(id);
	}
	
	@GetMapping("buildingbymanagerid")
	public ResponseEntity<ResponceStructure<BuildingClone>> findBuildingByManagerId(@RequestParam int manager_id){
		return buildingService.findBuildingByManagerId(manager_id);
	}
	
	@GetMapping("/buildingbyName")
	public ResponseEntity<ResponceStructure<List<BuildingClone>>> findByName(@RequestParam String name){
		return buildingService.findBuildingByName(name);
	}
	@GetMapping("/buildingall")
	public ResponseEntity<ResponceStructure<List<Building>>> findAllBuildings(){
		return buildingService.findAllbuildings();
	}
	@GetMapping("/buildingbyaddress")
	public ResponseEntity<ResponceStructure<List<BuildingClone>>> findBuildingByCity(@RequestParam String name){
		return buildingService.findBuildingByAddress(name);
	}
	@DeleteMapping("/deleteBuilding")
	public ResponseEntity<ResponceStructure<BuildingClone>> deleteBuildingbyId(@RequestParam int id){
		return buildingService.deleteBuildingById(id);
		
	}
	@PutMapping("/assignmanager")
	public ResponseEntity<ResponceStructure<Building>> assignManager(@RequestParam int building_id, @RequestParam int admin_id, @RequestParam int manager_id){
		return buildingService.assignManagerToBuilding(building_id, manager_id, admin_id);
	}
	
	
	@PutMapping("/buildingrating")
	public ResponseEntity<ResponceStructure<BuildingClone>> buildingRating(@RequestParam int client_id, @RequestParam int Building_id, @RequestParam int rating){
		return buildingService.buildingRating(client_id, rating, Building_id);
	}
	@GetMapping("/buildingByAdminId")
	public ResponseEntity<ResponceStructure<Building>> findBuildingByAdmin(@RequestParam int admin_id){
		return buildingService.findBuildingByAdmin(admin_id);
	}

}




//save building 


//{
//	  "address": {
//	    "city": "vijaywada",
//	    "district": "westgodhavari",
//	    "door_No": "2+25",
//	    
//	    "landmark": "vijaywada",
//	    "pincode": 444774,
//	    "state": "ap",
//	    "street": "vij"
//	  },
//
//	  "building_Name": "jspiders",
//	  "floors": [
//	    {
//	      "floor_Number": 1,
//	      
//	      "spaces": [
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 500,
//	          "squareFeet": "20*20",
//	          "type": "meetingRoom"
//	        },
//	
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 1000,
//	          "squareFeet": "20*20",
//	          "type": "training"
//
//},
//	 
//	        {
//	          "availability": "yes",
//	          "capacity": 3000,
//	         
//	         
//	          "pricePerday": 1500,
//	          "squareFeet": "20*20",
//	          "type": "privateoffice"
//	        },
//	
//	        {
//	          "availability": "yes",
//	          "capacity": 4000,
//	         
//	         
//	          "pricePerday": 200,
//	          "squareFeet": "30*20",
//	          "type": "virtualoffice"
//	        }
//	      ]
//	    },
//	 {
//	      "floor_Number": 2,
//	      
//	      "spaces": [
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 500,
//	          "squareFeet": "20*20",
//	          "type": "sleeping"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 100,
//	          "squareFeet": "40*20",
//	          "type": "drinking"
//	        },
//	        {
//	          "availability": "yes",
//	          "capacity": 300,
//	         
//	         
//	          "pricePerday": 400,
//	          "squareFeet": "20*90",
//	          "type": "cafeteria"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 4800,
//	         
//	         
//	          "pricePerday": 800,
//	          "squareFeet": "20*20",
//	          "type": "shopping"
//	        }
//	      ]
//	    },
//	 {
//	      "floor_Number": 3,
//	      
//	      "spaces": [
//	        {
//	          "availability": "yes",
//	          "capacity": 8000,
//	         
//	         
//	          "pricePerday": 1200,
//	          "squareFeet": "90*20",
//	          "type": "virtualoffice"
//	        },
//	        {
//	          "availability": "yes",
//	          "capacity": 4000,
//	         
//	         
//	          "pricePerday": 1000,
//	          "squareFeet": "50*20",
//	          "type": "privateoffice"
//	        },
//	        {
//	          "availability": "yes",
//	          "capacity": 3500,
//	         
//	         
//	          "pricePerday": 500,
//	          "squareFeet": "20*20",
//	          "type": "drinking"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 2500,
//	         
//	         
//	          "pricePerday": 4100,
//	          "squareFeet": "20*20",
//	          "type": "training"
//	        }
//	      ]
//	    },
//	 {
//	      "floor_Number": 4,
//	      
//	      "spaces": [
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 505,
//	          "squareFeet": "20*20",
//	          "type": "meetingRoom"
//	        },
//	
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 506,
//	          "squareFeet": "20*20",
//	          "type": "training"
//},
//	  
//	        {
//	          "availability": "yes",
//	          "capacity": 3000,
//	         
//	         
//	          "pricePerday": 507,
//	          "squareFeet": "20*20",
//	          "type": "meetingRoom"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 4000,
//	         
//	         
//	          "pricePerday": 508,
//	          "squareFeet": "20*20",
//	          "type": "privateoffice"
//	        }
//	      ]
//	    },
//	 {
//	      "floor_Number": 5,
//	      
//	      "spaces": [
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 505,
//	          "squareFeet": "20*20",
//	          "type": "meetingRoom"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 2000,
//	         
//	         
//	          "pricePerday": 506,
//	          "squareFeet": "20*20",
//	          "type": "cafeteria"
//	        },
//	        {
//	          "availability": "yes",
//	          "capacity": 3000,
//	         
//	         
//	          "pricePerday": 507,
//	          "squareFeet": "20*20",
//	          "type": "privateoffice"
//	        },
//
//	        {
//	          "availability": "yes",
//	          "capacity": 4000,
//	         
//	         
//	          "pricePerday": 508,
//	          "squareFeet": "20*20",
//	          "type": "privateoffice"
//	        }
//	      ]
//	    }
//	  ],
//
//
//	  "rating": 0
//	}
//
//
//
