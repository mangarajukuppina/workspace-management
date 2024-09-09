package com.jsp.workSpace.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.clone.ClientClone;
import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.ClientBookingDao;
import com.jsp.workSpace.dao.ClientDao;
import com.jsp.workSpace.dao.WorkSpaceDao;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.dto.Clint;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.exception.ClientAlreadyExist;
import com.jsp.workSpace.exception.ClientNotFound;
import com.jsp.workSpace.exception.IncorrectEmail;
import com.jsp.workSpace.exception.IncorrectPassword;
import com.jsp.workSpace.exception.WorkSpaceNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private WorkSpaceDao workSpaceDao;
	@Autowired
	private ClientBookingDao clientBookingDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponceStructure<ClientClone>> saveClient(Clint client)
			throws SQLIntegrityConstraintViolationException {

		Clint dbClient = clientDao.saveClient(client);
		if (dbClient != null) {
			ResponceStructure<ClientClone> structure = new ResponceStructure<ClientClone>();
			ClientClone clientClone=this.modelMapper.map(dbClient, ClientClone.class);
			structure.setData(clientClone);
			structure.setMessege("Client Data Saved successfully");
			structure.setStatus(HttpStatus.CREATED.value());

			return new ResponseEntity<ResponceStructure<ClientClone>>(structure, HttpStatus.CREATED);

		} else {
			throw new SQLIntegrityConstraintViolationException(
					"Your acoount already registered with email = " + client.getEmail());
		}

	}

	public ResponseEntity<ResponceStructure<Clint>> loginClient(String email, String password) {
		Clint dbClints = clientDao.findClientByEmail(email);
		int i=0;
		if (dbClints != null) {
			System.out.println("enter password "+password);
			System.out.println("db password "+dbClints.getPassword());
			
			if (dbClints.getPassword().equals(password)) {
//				List<Building> dbBuildings1 = buildingDao.findAllBuiding();
//				List<Building> dbBuildings = new ArrayList<Building>();
//				for (Building building : dbBuildings1) {
//					if (building.getFloors() != null) {
//						List<Floor> dbFloors = building.getFloors();
//						for (Floor floor : dbFloors) {
//							if (floor.getSpaces() != null) {
//								List<WorkSpace> dbWorkSpaces = floor.getSpaces();
//								for (WorkSpace workSpace : dbWorkSpaces) {
//									if (workSpace.getAvailability().equalsIgnoreCase("yes")) {
//										building.setAdmin(null);
//
//										building.setManager(null);
//										dbBuildings.add(building);
//										i++;
//										break;
//
//									}
//								}
//							}
//							if(dbBuildings.size()!=0) {    
//							if(dbBuildings.size()==i) {
//								break;
//								
//							}
//							else {
//								continue;
//							}
//						}
//						}
//					}
//					if(dbBuildings.size()!=0) {
//					if(dbBuildings.size()==i) {
//						break;
//						
//					}
//					else {
//						continue;
//					}
//					}
//				}
//
				ResponceStructure<Clint> structure = new ResponceStructure<Clint>();
				structure.setMessege("Login Successfully.......");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(dbClints);
//
				return new ResponseEntity<ResponceStructure<Clint>>(structure, HttpStatus.OK);
			} else {
				throw new IncorrectPassword(
						"Dear client you entered in correct password check password and try again.....");
			}

		} else {
			throw new IncorrectEmail("Dear client you entered in correct email check email and try again.....");
		}
	}

	public ResponseEntity<ResponceStructure<ClientClone>> findClientByOId(int id) {
		Clint dbClient = clientDao.findbyId(id);
		if (dbClient != null) {
			ResponceStructure<ClientClone> structure = new ResponceStructure<ClientClone>();
			ClientClone clientClone=this.modelMapper.map(dbClient, ClientClone.class);
			structure.setMessege("client found successfully....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(clientClone);
			return new ResponseEntity<ResponceStructure<ClientClone>>(structure, HttpStatus.FOUND);
		} else {
			throw new ClientNotFound("Client not exist with id = " + id);
		}
	}

	public ResponseEntity<ResponceStructure<ClientClone>> updateClient(Clint client) throws SQLIntegrityConstraintViolationException {
		Clint dbClient = clientDao.findbyId(client.getId());
		if (dbClient != null) {
			if (client.getAge() != 0) {
				dbClient.setAge(client.getAge());
			}
			if (client.getEmail() != null) {
				dbClient.setEmail(client.getEmail());

			}
			if (client.getGender() != null) {
				dbClient.setGender(client.getGender());

			}
			if(client.getAddress()!=null) {
				dbClient.setAddress(client.getAddress());
				
			}
			if(client.getId_proof()!=null) {
				dbClient.setId_proof(client.getId_proof());
			}
			if(client.getName()!=null) {
				dbClient.setName(client.getName());
			}
			if(client.getPassword()!=null) {
				dbClient.setPassword(client.getPassword());
			}
			if(client.getPhone()!=0) {
				dbClient.setPhone(client.getPhone());
			}
			
			Clint updatedClient = clientDao.saveClient(dbClient);
			if(updatedClient!=null) {
				ResponceStructure<ClientClone> structure=new ResponceStructure<ClientClone>();
				ClientClone clientClone=this.modelMapper.map(dbClient, ClientClone.class);
				structure.setMessege("Client updated successfully....");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(clientClone);
				return new ResponseEntity<ResponceStructure<ClientClone>>(structure, HttpStatus.OK);
				
				
			}
			else {
				throw new SQLIntegrityConstraintViolationException("Email already exist...");
				
			}
				
				
			
		}
			else {
				throw new ClientNotFound("client not found with id = " + client.getId());
			}
		
	}
	public ResponseEntity<ResponceStructure<ClientClone>> deleteClientById(int id){
		Clint dbClient = clientDao.deleteClientById(id);
		if(dbClient!=null) {
			ResponceStructure<ClientClone> structure=new ResponceStructure<ClientClone>();
			ClientClone  clientClone=this.modelMapper.map(dbClient, ClientClone.class);
			structure.setMessege("client with id "+id+" deleted successfully...");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(clientClone);
			return new ResponseEntity<ResponceStructure<ClientClone>>(structure, HttpStatus.OK);
			
		}
		else {
			throw new ClientNotFound("Client not found with id "+id);
		}
	}

	public ResponseEntity<ResponceStructure<Clint>> findClientByEmail(String client_email) {
		Clint dbClient=clientDao.findClientByEmail(client_email);
		if(dbClient!=null) {
			ResponceStructure<Clint> structure=new ResponceStructure<Clint>();
			structure.setData(dbClient);
			structure.setMessege("client found successfully....");
			structure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponceStructure<Clint>>(structure, HttpStatus.OK);
		}
		else {
			throw new ClientNotFound("Client not exist with email");
		}
		
	}
}
