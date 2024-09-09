package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.repo.BuildingRepo;



@Repository
public class BuildingDao {

	@Autowired
	private BuildingRepo buildingRepo;

	public Building saveBuilding(Building building) {   //saving
		return buildingRepo.save(building);
	}
	
	public Building updateBuilding(Building building) {  //update
		if(buildingRepo.findById(building.getId()).isPresent()) {
		Optional<Building> db = buildingRepo.findById(building.getId());
		
		if(building.getBuilding_Name()!=null) {
			db.get().setBuilding_Name(building.getBuilding_Name());
		}
		if(building.getAddress()!=null) {
			db.get().setAddress(building.getAddress());
		}
		if(building.getAdmin()!=null) {
			db.get().setAdmin(building.getAdmin());
		}
		if(building.getManager()!=null) {
			db.get().setManager(building.getManager());
		}
		if(building.getRating()!=0) {
			db.get().setRating(building.getRating());
		}
		return buildingRepo.save(db.get());
			
		}
		else {
			return null;
		}
	}
	
	
	
	public List<Building> fetchBuildingByName(String buildingName) {  //find name
		
		List<Building> db = buildingRepo.fetchbuildingByName(buildingName);
		
		if(db.isEmpty()) {
			return null;
		}else {
			return db;
		}
	}
	
	public List<Building> findBuildingByAddress(String city){     //find by location
		
		
		List<Building> db = buildingRepo.findBuildingByAddress(city);
		
		if(db.isEmpty()) {
			return null;
		}else {
			return db;
		}
	}
	
	public Building findBuildingById(int id) {        //find by id
		  
//		  if(buildingRepo.findById(id).isPresent()) {
//			  Building db = buildingRepo.findById(id).get();
//
//			  return db;
//		  }else {
//			  return null;
//		  }
		if(buildingRepo.findById(id).isPresent()) {
			System.out.println("building id is "+id);
			 Optional<Building> dbBuildinding = buildingRepo.findById(id);
			 System.out.println("building details "+dbBuildinding.get());
			 System.out.println("hii");
			 return dbBuildinding.get();
			
		}
		else {
			System.out.println("null");
			return null;
		}
	}
	
	public Building findBuildingByManagerId(int managerId) {
		List<Building> dBlist=buildingRepo.findAll();
		if(dBlist.isEmpty()) {
			return null;
		}
		else {
			
		
		for (Building building : dBlist) {
			if(building.getManager()!=null) {
			if(building.getManager().getId()==managerId) {
				return building;
			}
			}
		}
		return null;
		
		}
			
		}
		
		
	
	
	public List<Building> findAllBuiding(){         //find all
		List<Building> list = buildingRepo.findAll();
	
		if(list.isEmpty()) {
			return null;
			
		}
		else {
			return list;
		}
	}
	public Building deleteBuilding(int id) {
		Optional<Building> building=buildingRepo.findById(id);
		if(building.isPresent()) {
			buildingRepo.deleteById(id);
			return building.get();
		}
		else {
			return null;
		}
}
	
	public Building updateNullValues(int id) {
		Optional<Building> building=buildingRepo.findById(id);
		if(building.isPresent()) {
			building.get().setAddress(null);
			building.get().setManager(null);
			building.get().setAdmin(null);
			Building dbBuilding = buildingRepo.save(building.get());
			return dbBuilding;
		}
		else {
			return null;
		}
		
		
	}

  

}




//json object 
//for saving
//{
//	 "address": {
//	      "city": "punjagutta",
//	      "district": "rangareddy",
//	      "door_No": "333",
//	     
//	      "landmark": "ameerpeta",
//	      "pincode": 531113,
//	      "state": "ts",
//	      "street": "punjagutta"
//	    },
//	 "building_Name": "jspiders",
//	 "floors": [
//	      {
//	        "floor_Number": 15,
//	      
//	        "spaces": [
//	          {
//	            "availability": "yes",
//	            "capacity": 440,
//	            "pricePerday": 1000,
//	            "squareFeet": "200",
//	            "type": "meetingRoom"
//	          }
//	        ]
//	      },
//	{
//	        "floor_Number": 25,
//	      
//	        "spaces": [
//	          {
//	            "availability": "no",
//	            "capacity": 220,
//	            "pricePerday": 500,
//	            "squareFeet": "210",
//	            "type": "meetingRoom"
//	          }
//	        ]
//	      }
//	    ]
//
//	           
//	}


