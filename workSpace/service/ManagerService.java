package com.jsp.workSpace.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.clone.ManagerClone;
import com.jsp.workSpace.dao.AddressDao;
import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.ManagagerDao;
import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.exception.IncorrectEmail;
import com.jsp.workSpace.exception.IncorrectPassword;
import com.jsp.workSpace.exception.ManagerAlreadyExist;
import com.jsp.workSpace.exception.ManagerNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class ManagerService {

	@Autowired
	private ManagagerDao managerDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponceStructure<ManagerClone
	>> saveManager(Manager manager) {
		Manager dbManager = managerDao.saveManager(manager);
		if (dbManager != null) {
			ResponceStructure<ManagerClone> structure = new ResponceStructure<ManagerClone>();
			ManagerClone managerClone=this.modelMapper.map(dbManager, ManagerClone.class);
			structure.setData(managerClone);
			structure.setMessege("Manager Saved successfully...");
			structure.setStatus(HttpStatus.CREATED.value());

			return new ResponseEntity<ResponceStructure<ManagerClone>>(structure, HttpStatus.CREATED);
		} else {
			// new ManagerAlreadyExist("manager already exist");
			 throw new ManagerAlreadyExist("Manager Already Exist With your email");
		}

	}
	public ResponseEntity<ResponceStructure<ManagerClone>> findManagerById(int id){
		   Manager dbManager = managerDao.findManagerById(id);
		   if(dbManager!=null) {
			   ResponceStructure<ManagerClone> structure=new ResponceStructure<ManagerClone>();
			   ManagerClone managerClone=this.modelMapper.map(dbManager, ManagerClone.class);
			   structure.setMessege("Mangager details found successfully witd id = "+id);
			   structure.setStatus(HttpStatus.FOUND.value());
			   structure.setData(managerClone);
			   return new ResponseEntity<ResponceStructure<ManagerClone>>(structure, HttpStatus.FOUND);
		   }
		   else {
			   throw new ManagerNotFound("Mangager not details not present with id = "+id);
		   }
	}
	
	public ResponseEntity<ResponceStructure<List<ManagerClone>>> findManagerByExperience(int experience){
		List<Manager> dbList=managerDao.findManagerByExperience(experience);
		
		if(dbList!=null) {
			ResponceStructure<List<ManagerClone>> structure=new ResponceStructure<List<ManagerClone>>();
			 ManagerClone managerClone = this.modelMapper.map(dbList, ManagerClone.class);
			structure.setMessege(dbList.size()+" Manager list found successfully......");
			structure.setStatus(HttpStatus.FOUND.value());
		//	structure.setData(managerClone);
			return new ResponseEntity<ResponceStructure<List<ManagerClone>>>(structure, HttpStatus.FOUND);
			
		}
		else {
			throw new ManagerNotFound("Manager list not present with experience = "+experience);
		}
	}
	
	
	public ResponseEntity<ResponceStructure<ManagerClone>> login(String email, String password){
		Manager dbManager=managerDao.findManagerByEmail(email);
		
		if(dbManager!=null) {
			if(dbManager.getPassword().equals(password)) {
				
				ResponceStructure<ManagerClone> structure=new ResponceStructure<ManagerClone>();
				ManagerClone clone=this.modelMapper.map(dbManager, ManagerClone.class);
				structure.setMessege("login successfully...");
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(clone);
				return new ResponseEntity<ResponceStructure<ManagerClone>>(structure, HttpStatus.FOUND);
			}
			else {
				throw new IncorrectPassword("Password is incorrect");
			}
			
			
		}
		else {
			throw new IncorrectEmail("Email is incorrect");
		}
	}
	
	
	
	public ResponseEntity<ResponceStructure<List<Manager>>> findAllManager(){
		List<Manager> dbList = managerDao.findAllManager();
		if(dbList!=null) {
			ResponceStructure<List<Manager>> structure=new ResponceStructure<List<Manager>>();
			structure.setMessege(dbList.size()+" All manager detais found successfully.....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbList);
			return new ResponseEntity<ResponceStructure<List<Manager>>>(structure, HttpStatus.FOUND);
			}
		else {
			throw new ManagerNotFound("manager list not found");
		}
	}
	public ResponseEntity<ResponceStructure<Manager>>  updateManager(Manager manager){
		Manager dbManager=managerDao.findManagerById(manager.getId());
		if(manager.getAddress()!=null) {
			Address address = manager.getAddress();
			address.setId(dbManager.getAddress().getId());
			addressDao.updateAddress(address);
		}
		
		
		if(dbManager!=null) {
			Manager updatedManager=managerDao.updateManager(manager);
			ResponceStructure<Manager> structure=new ResponceStructure<Manager>();
//			ManagerClone clone=this.modelMapper.map(dbManager, ManagerClone.class);
			structure.setMessege("Manager Details are updated");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(updatedManager);
			return new ResponseEntity<ResponceStructure<Manager>>(structure, HttpStatus.OK);
		}
		else {
			throw new ManagerNotFound("Manager Not Exist with id = "+manager.getId());
		}
	}
	public ResponseEntity<ResponceStructure<ManagerClone>> deleteManagerById(int id){
		Building dbBuilding = buildingDao.findBuildingByManagerId(id);
		
		if(dbBuilding!=null) {
			dbBuilding.setManager(null);
			buildingDao.updateBuilding(dbBuilding);
		}
		
		Manager deletedManager = managerDao.deleteManager(id);
		if(deletedManager.getAddress()!=null) {
			addressDao.deleteAddressById(deletedManager.getAddress().getId());
		}
		if(deletedManager!=null) {
			ResponceStructure<ManagerClone> structure=new ResponceStructure<ManagerClone>();
			ManagerClone clone=this.modelMapper.map(deletedManager, ManagerClone.class);
			structure.setMessege("Manager with id = "+id+" is successfully deleted....");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(clone);
			return new ResponseEntity<ResponceStructure<ManagerClone>>(structure, HttpStatus.OK);
		}
		else {
			throw new ManagerNotFound("Manager not found with id = "+id);
		}
	}
	public ResponseEntity<ResponceStructure<ManagerClone>> findManagerByemail(String email) {
		 Manager dbManager = managerDao.findManagerByEmail(email);
		   if(dbManager!=null) {
			   ResponceStructure<ManagerClone> structure=new ResponceStructure<ManagerClone>();
			   ManagerClone managerClone=this.modelMapper.map(dbManager, ManagerClone.class);
			   structure.setMessege("Mangager details found successfully witd id = "+email);
			   structure.setStatus(HttpStatus.FOUND.value());
			   structure.setData(managerClone);
			   System.out.println("manager is "+managerClone);
			   return new ResponseEntity<ResponceStructure<ManagerClone>>(structure, HttpStatus.FOUND);
		   }
		   else {
			   throw new ManagerNotFound("Mangager not details not present with id = "+email);
		   }
	}
	public ResponseEntity<ResponceStructure<List<Manager>>> unAssignedManagers() {
		List<Building> dbBuildings = buildingDao.findAllBuiding();
		List<Manager> dbManagers = managerDao.findAllManager();
		List<Manager> tempManagers = new ArrayList<Manager>();
		for(Manager manager:dbManagers) {
			int count=0;
			for(Building building:dbBuildings) {
				if(building.getManager().getId()==manager.getId()) {
					count++;
				}
				
			}
			if(count==0) {
				tempManagers.add(manager);
			}
			
		}
		
		if(tempManagers.isEmpty()) {
			throw new ManagerNotFound("Managers are not available");
		}else {
			 ResponceStructure<List<Manager>> structure=new ResponceStructure<List<Manager>>();
			  
			   structure.setMessege("Mangagers Available");
			   structure.setStatus(HttpStatus.FOUND.value());
			   structure.setData(tempManagers);
			  
			   return new ResponseEntity<ResponceStructure<List<Manager>>>(structure, HttpStatus.FOUND);
		}
	}
	}
	

