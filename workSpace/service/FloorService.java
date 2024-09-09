package com.jsp.workSpace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.dao.FloorDao;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.exception.FloorNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class FloorService {
	@Autowired
	private FloorDao floorDao;
	
	public ResponseEntity<ResponceStructure<List<Floor>>> saveFloors(List<Floor> floors){
		List<Floor> dbFloors=floorDao.saveFloor(floors);
		ResponceStructure<List<Floor>> structure=new ResponceStructure<List<Floor>>();
		structure.setMessege("Floors saved successfully.....");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dbFloors);
		return new ResponseEntity<ResponceStructure<List<Floor>>>(structure, HttpStatus.CREATED);
		
		
	}
	public ResponseEntity<ResponceStructure<Floor>> findById(int id){
		Floor dbFloor=floorDao.findById(id);
		if(dbFloor!=null) {
			ResponceStructure<Floor>structure=new ResponceStructure<Floor>();
			structure.setMessege("Floor found successfully.....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbFloor);
			return new ResponseEntity<ResponceStructure<Floor>>(structure, HttpStatus.FOUND);
			
		}
		else {
			throw new FloorNotFound("Floor is not exist with id = "+id);
		}
		
	}

}
