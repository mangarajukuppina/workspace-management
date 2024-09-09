package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.repo.ManagerRepo;

@Repository
public class ManagagerDao {
	
	@Autowired
	private ManagerRepo managerRepo;
	
	
	public Manager saveManager(Manager manager) {
		return managerRepo.save(manager);
	}
	
	public Manager findManagerById(int id) {
		if(managerRepo.findById(id).isPresent()) {
			Manager manager = managerRepo.findById(id).get();
			return manager;

		}else {
			return null;
		}

	}
	public List<Manager> findManagerByExperience(int experience){
		List<Manager> listManagers = managerRepo.findManagerByExperience(experience);
		if(listManagers.isEmpty()) {
			return null;
		}
		else {
			return listManagers;
		}
	}
	public Manager findManagerByEmail(String email) {
		Manager dbManager=managerRepo.findManagerByEmail(email);
		if(dbManager!=null) {
			return dbManager;
		}
		else {
			return null;
		}
	}
	public List<Manager> findAllManager(){
		List<Manager> list=managerRepo.findAll();
		if(managerRepo.findAll().isEmpty()) {
			return null;
		}
		else {
			return list;
		}
		
	}
	public Manager updateManager(Manager manager) {
	
		Optional<Manager> dbManager = managerRepo.findById(manager.getId());
		if(dbManager.isPresent()) {
			if(manager.getName()!=null) {
				dbManager.get().setName(manager.getName());
			}
			if(manager.getEmail()!=null) {
				dbManager.get().setEmail(manager.getEmail());
			}
			if(manager.getPhone()!=0) {
				dbManager.get().setPhone(manager.getPhone());
			}
			if(manager.getPassword()!=null) {
				dbManager.get().setPassword(manager.getPassword());
			}
			if(manager.getGender()!=null) {
				dbManager.get().setGender(manager.getGender());
			}
			if(manager.getExperience()!=0) {
				dbManager.get().setExperience(manager.getExperience());
				
			}
			if(manager.getAddress()!=null) {
				dbManager.get().setAddress(manager.getAddress());
			}
			Manager dbUpdatedManager = managerRepo.save(dbManager.get());
			return dbUpdatedManager;
		}
		else {
			return null;
		}
		
	}
	public Manager deleteManager(int id) {
		Optional<Manager> dbManager = managerRepo.findById(id);
		if(dbManager.isPresent()) {
			managerRepo.delete(dbManager.get());
			return dbManager.get();
			
			
		}
		else {
			return null;
		}
	}


}
