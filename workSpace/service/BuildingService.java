package com.jsp.workSpace.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.clone.BuildingClone;
import com.jsp.workSpace.dao.AdminDao;
import com.jsp.workSpace.dao.ManagagerDao;
import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.Clint;
import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.exception.AdminNotFound;
import com.jsp.workSpace.exception.BuildingNotFound;
import com.jsp.workSpace.exception.ClientNotFound;
import com.jsp.workSpace.exception.InCorrectRating;
import com.jsp.workSpace.exception.ManagerAlredyAssingnAnotherBuilding;
import com.jsp.workSpace.exception.ManagerNotFound;
import com.jsp.workSpace.util.ResponceStructure;
import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.ClientDao;

@Service
public class BuildingService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private ManagagerDao managerDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponceStructure<BuildingClone>> saveBuilding(Building building, int AdminId, int managerId) {

		Admin dbAdmin = adminDao.findAdminById(AdminId);
		Manager dbManager = managerDao.findManagerById(managerId);

		if (dbAdmin != null) {
			if (dbManager != null) {
				List<Building> list = buildingDao.findAllBuiding();
				if (list != null) {

					for (Building building2 : list) {
						if (building2.getManager() == null) {
							continue;
						}
						if (building2.getManager().getId() == managerId) {
							throw new ManagerAlredyAssingnAnotherBuilding(
									"Manager id = " + managerId + " is Already assigned with building name = "
											+ building.getBuilding_Name() + " try with Another Manager id");

						}

					}

				}
				building.setAdmin(dbAdmin);
				building.setManager(dbManager);
				Building dbbuilding = buildingDao.saveBuilding(building);
				if (dbbuilding != null) {
					ResponceStructure<BuildingClone> structure = new ResponceStructure<BuildingClone>();
					BuildingClone buildingClone =this.mapper.map(dbManager, BuildingClone.class);
					structure.setData(buildingClone);
					structure.setMessege("Building Saved Successfully.");
					structure.setStatus(HttpStatus.CREATED.value());

					return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.CREATED);

				} else {
					throw new BuildingNotFound("Building not saved check datatypes");
				}
			} else {
				throw new ManagerNotFound("Manager Account not exist with id = " + managerId);
			}

		} else {

			throw new AdminNotFound("Admin not found with id = " + AdminId);

		}
	}

	public ResponseEntity<ResponceStructure<BuildingClone>> findBuildingById(int id) {
		Building dbBuilding = buildingDao.findBuildingById(id);
		if (dbBuilding != null) {
			dbBuilding.setAdmin(null);
			dbBuilding.setManager(null);
			ResponceStructure<BuildingClone> structure = new ResponceStructure<BuildingClone>();
			BuildingClone buildingClone=this.mapper.map(dbBuilding, BuildingClone.class);
			structure.setMessege("Building details found successfully with id = " + id);
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(buildingClone);
			return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.FOUND);
		} else {
			throw new BuildingNotFound("building detail are not present with id = " + id);
		}

	}

	public ResponseEntity<ResponceStructure<List<BuildingClone>>> findBuildingByName(String buildingName) {

		List<Building> dbbuilding = buildingDao.fetchBuildingByName(buildingName);
		List<BuildingClone> list1=new ArrayList<BuildingClone>();
		ResponceStructure<List<BuildingClone>> structure = new ResponceStructure<List<BuildingClone>>();
		if (dbbuilding != null) {
			for(Building building:dbbuilding) {
				list1.add(this.mapper.map(building, BuildingClone.class));
				
				
				
			}
			
			
		//	ResponceStructure<List<Building>> structure = new ResponceStructure<>();
			structure.setData(list1);
			structure.setMessege("building found successfully. with name = " + buildingName);
			structure.setStatus(HttpStatus.FOUND.value());

			return new ResponseEntity<ResponceStructure<List<BuildingClone>>>(structure, HttpStatus.FOUND);
		} else {

			throw new BuildingNotFound("building not found with name = " + buildingName);
		}
	}

	public ResponseEntity<ResponceStructure<List<BuildingClone>>> findBuildingByAddress(String city) {
		List<Building> dbbuBuildings = buildingDao.findAllBuiding();
		System.out.println(dbbuBuildings);

		List<BuildingClone> list2 = new ArrayList<BuildingClone>();
		List<BuildingClone> list3=new ArrayList<BuildingClone>();
		ResponceStructure<List<BuildingClone>> structure = new ResponceStructure<List<BuildingClone>>();
		if (dbbuBuildings != null) {

			for (Building building : dbbuBuildings) {
				if (building.getAddress() == null) { // to avoid null address referce for city name
					continue;
				}

				if (building.getAddress().getCity().equalsIgnoreCase(city)) {
					list2.add(this.mapper.map(building, BuildingClone.class));
//					building.setAdmin(null);
//					building.setManager(null);
//					list2.add(building);

				}

			}
			if (list2.isEmpty()) {
				throw new BuildingNotFound("Buildings not found in location = " + city);

			} else {
				structure.setMessege(list2.size()+" Buildings founded in address = " + city);
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(list2);
				return new ResponseEntity<ResponceStructure<List<BuildingClone>>>(structure, HttpStatus.FOUND);

			}

		} else {
			throw new BuildingNotFound("buildings not found");
		}

	}

	public ResponseEntity<ResponceStructure<List<Building>>> findAllbuildings() {
		List<Building> list = buildingDao.findAllBuiding();
		if (list != null) {
			for(Building building:list) {
				building.setAdmin(null);
				building.setManager(null);
			}
			
			ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
			structure.setMessege(list.size()+" Buildings List found successfully.....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(list);
			return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
		} else {
			throw new BuildingNotFound("Buildings not found ....");
		}
	}

	public ResponseEntity<ResponceStructure<Building>> assignManagerToBuilding(int buildingId, int managerId,
			int adminId) {

		Admin admin = adminDao.findAdminById(adminId);

		if (admin != null) {

			Manager manager = managerDao.findManagerById(managerId);
			System.out.println("manager id is "+managerId+" "+manager);
			if (manager != null) {

				Building building = buildingDao.findBuildingById(buildingId);

				if (building != null) {
					building.setManager(manager);
					building=buildingDao.saveBuilding(building);
					ResponceStructure<Building> structure = new ResponceStructure<>();
					structure.setMessege("manager assigned to the buildin");
					structure.setStatus(HttpStatus.ACCEPTED.value());
					structure.setData(building);

					return new ResponseEntity<ResponceStructure<Building>>(structure, HttpStatus.ACCEPTED);
				} else {
					throw new BuildingNotFound("Building is not there to assign manager!");
				}

			} else {
				throw new ManagerNotFound("Manager is not there to assign it to building");
			}

		} else {
			throw new AdminNotFound("Admin not found to assign it to building! ");
		}
	}

	public ResponseEntity<ResponceStructure<BuildingClone>> updateBuilding(Building building) {
		Building dbBuiilding = buildingDao.findBuildingById(building.getId());
		if (dbBuiilding != null) {
			Building updateddbBuiilding = buildingDao.updateBuilding(building);
			ResponceStructure<BuildingClone> structure = new ResponceStructure<BuildingClone>();
			BuildingClone buildingClone=this.mapper.map(updateddbBuiilding, BuildingClone.class);
			structure.setMessege("your building details successsfully updated with id = " + building.getId());
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(buildingClone);
			return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.FOUND);
		} else {
			throw new BuildingNotFound("Building not found with id = " + building.getId());
		}

	}

	public ResponseEntity<ResponceStructure<BuildingClone>> deleteBuildingById(int id) {
		Building dbBuilding = buildingDao.findBuildingById(id);
		if (dbBuilding != null) {
			Building building = buildingDao.updateNullValues(id);
			ResponceStructure<BuildingClone> structure = new ResponceStructure<BuildingClone>();
			Building building2 = buildingDao.deleteBuilding(id);
			BuildingClone buildingClone =this.mapper.map(dbBuilding, BuildingClone.class);
			structure.setMessege("building with id " + id + " is successfully deleted");
			structure.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
			structure.setData(buildingClone);
			return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.MOVED_PERMANENTLY);

		} else {
			throw new BuildingNotFound("building not found with id = " + id);
		}

	}
	
	public ResponseEntity<ResponceStructure<BuildingClone>> buildingRating(int clients_id, int rating, int building_id){
		Building dbBuilding = buildingDao.findBuildingById(building_id);
		Clint dbClient = clientDao.findbyId(clients_id);
		if(dbBuilding!=null) {
			if(dbClient!=null) {
				if(rating>=0||rating<=5) {
					int dbRating = dbBuilding.getRating();
					int avgRating=(dbRating+rating)/2;
					dbBuilding.setRating(avgRating);
					Building updatedRating = buildingDao.saveBuilding(dbBuilding);
					ResponceStructure<BuildingClone> structure=new ResponceStructure<BuildingClone>();
					BuildingClone buildingClone =this.mapper.map(dbBuilding, BuildingClone.class);
					structure.setMessege("rating given successfully.....");
					structure.setData(buildingClone);
					structure.setStatus(HttpStatus.OK.value());
					return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.OK);
					
				}
				else throw new InCorrectRating("rating must be 1-5 range");
				
			}
			else {
				throw new ClientNotFound("Client not exist with id = "+clients_id);
			}
			
		}
		else {
			throw new BuildingNotFound("Building not exist with id = "+building_id);
		}
		
	}
	public ResponseEntity<ResponceStructure<BuildingClone>> findBuildingByManagerId(int manager_id){
		Building building = buildingDao.findBuildingByManagerId(manager_id);
		if(building!=null) {
			ResponceStructure<BuildingClone> structure=new ResponceStructure<BuildingClone>();
			BuildingClone clone=this.mapper.map(building, BuildingClone.class);
			structure.setMessege("Building Found successfully.....");
			structure.setData(clone);
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponceStructure<BuildingClone>>(structure, HttpStatus.FOUND);
			
		}
		else {
			throw new BuildingNotFound("Manager Not Assigned to any building");
		}
	}

	public ResponseEntity<ResponceStructure<Building>> findBuildingByAdmin(int admin_id) {
		List<Building> dbAllBuildings=buildingDao.findAllBuiding();
		System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		for(Building building:dbAllBuildings) {
			if(building.getAdmin().getId()==admin_id) {
				ResponceStructure<Building> structure=new ResponceStructure<Building>();
				structure.setData(building);
				structure.setMessege("Building found successfully...");
				structure.setStatus(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponceStructure<Building>>(structure, HttpStatus.FOUND);
			}
					}
		throw new BuildingNotFound("Building not found...");
	}

}
