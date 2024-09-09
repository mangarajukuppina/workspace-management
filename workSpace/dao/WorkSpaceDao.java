package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.repo.WorkSpaceRepo;

@Repository
public class WorkSpaceDao {
	
	@Autowired
	private WorkSpaceRepo workSpaceRepo;
	
	public WorkSpace saveWorkSpaces(WorkSpace workSpace){
       WorkSpace dbWorkSpaces = workSpaceRepo.save(workSpace);
		if(dbWorkSpaces!=null) {
			return dbWorkSpaces;
			
		}
		else {
			return null;
		}
	}
	public List<WorkSpace> saveWorkSpaces(List<WorkSpace> workSpaces){
		List<WorkSpace> dbListWorkSpaces = workSpaceRepo.saveAll(workSpaces);
		if(dbListWorkSpaces.isEmpty()) {
			return null;
		}
		else {
			return dbListWorkSpaces;
		}
	}
	public WorkSpace findWorkSpaceById(int id) {
		Optional<WorkSpace> dbWorkSpace = workSpaceRepo.findById(id);
		if(dbWorkSpace.isPresent()) {
			return dbWorkSpace.get();	
			}
		else {
			return null;
		}
	}
	
	public List<WorkSpace> findAllWorkSpacess(){
		List<WorkSpace> dbList=workSpaceRepo.findAll();
		if(dbList.isEmpty()) {
			return null;		
	}
	else {
		return dbList;
	}

}
	public WorkSpace updateWorkSpace(WorkSpace workSpace){
		WorkSpace dbWorkSpace=workSpaceRepo.save(workSpace);
		if(dbWorkSpace!=null) {
			return dbWorkSpace;
		}
		else {
			return null;
		}
	}
	public WorkSpace deleteWorkSpaceById(int id) {
		Optional<WorkSpace> dbWorkSpace = workSpaceRepo.findById(id);
		if(dbWorkSpace.isPresent()) {
			workSpaceRepo.deleteById(id);
			return dbWorkSpace.get();
		}
		else {
			return null;
		}
	}
}
