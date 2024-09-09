package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.repo.FloorRepo;

@Repository
public class FloorDao {
	@Autowired
	private FloorRepo floorRepo;
	
	public Floor saveFloor(Floor floor) {
		Floor dbFloor = floorRepo.save(floor);
		if(dbFloor!=null) {
			return dbFloor;
		}
		else {
			return null;
		}
	}
	
	
	public List<Floor> saveFloor(List<Floor> floors){
		List<Floor> dbFloors=floorRepo.saveAll(floors);
		return dbFloors;
	}
	
	public Floor findById(int id) {
		Optional<Floor> dbFloor=floorRepo.findById(id);
		if(dbFloor.isPresent()) {
			return dbFloor.get();
		}
		else {
			return null;
		}
	}
	public List<Floor> findAllFloors(){
		List<Floor> dbFloors = floorRepo.findAll();
		if(dbFloors.isEmpty()) {
			return null;
		}
		else {
			return dbFloors;
		}
	}
	public Floor deleteFloorById(int id) {
		Optional<Floor> dbFloor = floorRepo.findById(id);
		if(dbFloor.isPresent()) {
			floorRepo.deleteById(id);
			return dbFloor.get();
		}
		else {
			return null;
		}
	}

}
