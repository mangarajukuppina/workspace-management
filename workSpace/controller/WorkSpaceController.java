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

import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.enums.WorkSpaceType;
import com.jsp.workSpace.service.WorkSpaceService;
import com.jsp.workSpace.util.ResponceStructure;

@RestController
@CrossOrigin
public class WorkSpaceController {
	@Autowired
	private WorkSpaceService workSpaceService;

	@PostMapping("/workspace")
	public ResponseEntity<ResponceStructure<WorkSpace>> saveWorkSpace(@RequestBody WorkSpace workSpace,
			@RequestParam int managerId, @RequestParam int floorId) {
		return workSpaceService.saveWorkSpaces(workSpace, managerId, floorId);
	}

	@GetMapping("/workspace")
	public ResponseEntity<ResponceStructure<WorkSpace>> findWorkSPaceById(@RequestParam int id) {
		return workSpaceService.findWorkSpaceById(id);
	}

	@GetMapping("workspacebylocaton")
	public ResponseEntity<ResponceStructure<List<Building>>> findWorkSpaceByLocation(@RequestParam String city) {
		return workSpaceService.fetchWorkSpaceByLocation(city);
	}

	@GetMapping("/workspacesbycapacity")
	public ResponseEntity<ResponceStructure<List<Building>>> findworkSpacesByCapacity(@RequestParam int capacity) {
		return workSpaceService.findWorkSpacesByCapacity(capacity);
	}

//	@GetMapping("/workspacesbycost")
//	public ResponseEntity<ResponceStructure<List<Building>>> findworkSpaceByCost(@RequestParam int cost) {
//		return workSpaceService.findWorkSpacesByCost(cost);
//	}

	@GetMapping("/workspacesbytype")
	public ResponseEntity<ResponceStructure<List<Building>>> findworkSpaceByType(
			@RequestParam WorkSpaceType workSpaceType) {
		return workSpaceService.findWorkSpacesBytype(workSpaceType);
	}
	@GetMapping("/workspacebycost")
	public ResponseEntity<ResponceStructure<List<Building>>> findWorkspaceByCost(int cost){
		return workSpaceService.findWorkSpacesByCost(cost);
	}

	@GetMapping("/workspaceall")
	public ResponseEntity<ResponceStructure<List<WorkSpace>>> findAllWorkSpaces() {
		return workSpaceService.findAllWorkSpaces();
	}

	@PutMapping("/workspace")
	public ResponseEntity<ResponceStructure<WorkSpace>> updateWorkSpaceById(@RequestBody WorkSpace workSpace) {
		return workSpaceService.updateWorkSpace(workSpace);
	}

	@DeleteMapping("/deleteWorkSpace")
	public ResponseEntity<ResponceStructure<WorkSpace>> deleteWorkSpaceById(@RequestParam int workSpaceId) {
		System.out.println("workpsace id is "+workSpaceId);
		return workSpaceService.deleteWorkSpaceById(workSpaceId);
	}
	
	@GetMapping("streamworkspacebycost")
	public ResponseEntity<ResponceStructure<Integer>> allWorkSpaceCost(){
		return workSpaceService.allWorkSpaceCost();
	}

}
