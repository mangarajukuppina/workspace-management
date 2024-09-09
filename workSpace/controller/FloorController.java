package com.jsp.workSpace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.service.FloorService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class FloorController {
	@Autowired
	private FloorService floorService;
	
	@PostMapping("/floor")
	public ResponseEntity<ResponceStructure<List<Floor>>> saveFloors(@RequestBody List<Floor> floors){
		return floorService.saveFloors(floors);
	}
	@GetMapping("/floor")
	public ResponseEntity<ResponceStructure<Floor>> findFloorById(@RequestParam int id){
		return floorService.findById(id);
	}

}
