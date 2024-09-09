package com.jsp.workSpace.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.FloorDao;
import com.jsp.workSpace.dao.ManagagerDao;
import com.jsp.workSpace.dao.WorkSpaceDao;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.enums.WorkSpaceType;
import com.jsp.workSpace.exception.BuildingNotFound;
import com.jsp.workSpace.exception.FloorNotFound;
import com.jsp.workSpace.exception.ManagerNotFound;
import com.jsp.workSpace.exception.WorkSpaceNotFound;
import com.jsp.workSpace.repo.WorkSpaceRepo;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class WorkSpaceService {
	@Autowired
	private WorkSpaceDao workSpaceDao;
	@Autowired
	private ManagagerDao managarDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private FloorDao floorDao;

	public ResponseEntity<ResponceStructure<WorkSpace>> saveWorkSpaces(WorkSpace workSpaces, int managerId,
			int floorId) {
		Manager dbManager = managarDao.findManagerById(managerId);
		if (dbManager != null) {

//			List<Floor> dbFloors=floorDao.findAllFloors();
			List<Building> dbBuilding = buildingDao.findAllBuiding(); // find all buidings
			List<WorkSpace> listWorkSpaces = new ArrayList<WorkSpace>();
			List<Floor> floors = new ArrayList<Floor>();

			for (Building building : dbBuilding) {
				if (building.getManager() != null) {

					if (building.getManager().getId() == managerId) {
						System.out.println("manager id is found");
						floors = building.getFloors(); // to get list of floors for particular building
						for (Floor floor : floors) {
							System.out.println("floor id is " + floor.getId());
							System.out.println("floor passed id is " + floorId);
							if (floor.getId() == floorId) { // to check flood id is present or not
								System.out.println("floor i is found");
								listWorkSpaces = floor.getSpaces();// to all workspaces in floor and added
																	// to list
								listWorkSpaces.add(workSpaces); // to add workspace to floor
								List<WorkSpace> updatedWorkSpace = workSpaceDao.saveWorkSpaces(listWorkSpaces); // to
																												// save
																												// workspace
																												// into
																												// database

								floor.setSpaces(updatedWorkSpace); // to update floor after saving workspace
								Floor updatedFloor = floorDao.saveFloor(floor);
//							
							}

						}
						if (listWorkSpaces.isEmpty()) {
							throw new FloorNotFound("floor is not found with id = " + floorId);
						}

					}
				}
			}
			if (floors.isEmpty()) {
				throw new ManagerNotFound("manager id = " + managerId + "not exist any building");
			} else {
				ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
				structure.setMessege("workspace saved successfully.....");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(workSpaces);
				return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.CREATED);
			}

		} else {
			throw new ManagerNotFound("Manager Not Exist with id = " + managerId);
		}

	}

	public ResponseEntity<ResponceStructure<WorkSpace>> findWorkSpaceById(int id) {
		WorkSpace dbWorkSpace = workSpaceDao.findWorkSpaceById(id);
		if (dbWorkSpace != null) {
			ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
			structure.setMessege("workSpace found successfully.....with id = " + id);
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbWorkSpace);
			return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.FOUND);
		} else {
			throw new WorkSpaceNotFound("WorkSpace not present with id = " + id);
		}
	}

	public ResponseEntity<ResponceStructure<List<Building>>> fetchWorkSpaceByLocation(String city) {
		List<Building> dbAllBuildings = buildingDao.findAllBuiding();
		List<Building> addressBuildingList = new ArrayList<Building>();
		if (dbAllBuildings != null) {
			for (Building building : dbAllBuildings) {
				if (building.getAddress() != null) { // to check building consist of address or not
					if (building.getAddress().getCity().equalsIgnoreCase(city)) {
						addressBuildingList.add(building);

					}
				}

			}
			if (addressBuildingList.isEmpty()) {
				throw new BuildingNotFound("WorkSpaces not available in " + city);
			} else {
				ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
				structure.setMessege(addressBuildingList.size() + " Buildings available in " + city);
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(addressBuildingList);
				return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
			}

		} else {
			throw new BuildingNotFound("building not present");
		}

	}

	public ResponseEntity<ResponceStructure<List<WorkSpace>>> findAllWorkSpaces() {
		List<WorkSpace> dbList = workSpaceDao.findAllWorkSpacess();
		if (dbList != null) {
			ResponceStructure<List<WorkSpace>> structure = new ResponceStructure<List<WorkSpace>>();
			structure.setMessege(dbList.size() + " List of WorkSpaces found successfully.....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbList);
			return new ResponseEntity<ResponceStructure<List<WorkSpace>>>(structure, HttpStatus.FOUND);

		} else {
			throw new WorkSpaceNotFound("WorkSpaces not present.....");

		}
	}

	public ResponseEntity<ResponceStructure<List<Building>>> findWorkSpacesByCapacity(int capacity) {
		List<Building> dbBuilding = buildingDao.findAllBuiding(); // all buildings
		List<Building> buildingsByCapacity = new ArrayList<Building>(); // to store buldings with same capacity
		int i = 0; // workaspaces
		if (dbBuilding != null) {
			for (Building building : dbBuilding) {
				if (building.getFloors().isEmpty()) { // to check buildings consist of floors or not
					continue;
				}
				i++;
				List<Floor> dbListFloors = building.getFloors(); // all floors in buildings
				for (Floor floor : dbListFloors) {
					i++;
					if (floor.getSpaces() != null) { // to check floor consist of workspaces or not
						List<WorkSpace> workSpaces = floor.getSpaces(); // all workspaces in floor
						for (WorkSpace workSpace : workSpaces) {
							i++;
							if (workSpace.getCapacity() == capacity) { // to find capacity workspaces
								buildingsByCapacity.add(building);
								i = buildingsByCapacity.size();
								break;

							}
						}

					}
					if (buildingsByCapacity.size() != 0) {
						if (buildingsByCapacity.size() == i) {
							break;
						} else {
							continue;
						}
					}

				}
//				if (buildingsByCapacity.size() != 0) {
//					if (buildingsByCapacity.size() == i) {
//						break;
//					} else {
//						continue;
//					}
//				}

			}

			if (buildingsByCapacity.isEmpty()) {
				throw new BuildingNotFound("Buildings not available with capacity = " + capacity);
			} else {
				ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
				structure.setMessege(buildingsByCapacity.size() + " Buildings are available");
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(buildingsByCapacity);
				return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
			}

		} else {
			throw new BuildingNotFound("Buildind not found...");
		}
	}

	public ResponseEntity<ResponceStructure<List<Building>>> findWorkSpacesBytype(WorkSpaceType workSpaceType) {
		List<Building> dbBuilding = buildingDao.findAllBuiding(); // all buildings
		List<Building> buildingsByCapacity = new ArrayList<Building>(); // to store buldings with same capacity
		int i = 0; // workaspaces

//      if(workSpaceType.toString().equalsIgnoreCase("low-to-high")||workSpaceType.toString().equalsIgnoreCase("high-to-low")) {
//    	  
//    	  
//    	  
//    	  
//      }

		if (dbBuilding != null) {
			for (Building building : dbBuilding) {
				i++;
				if (building.getFloors().isEmpty()) { // to check buildings consist of floors or not
					continue;
				}

				List<Floor> dbListFloors = building.getFloors(); // all floors in buildings
				for (Floor floor : dbListFloors) {
					i++;
					if (floor.getSpaces().isEmpty()) {
						continue;
					}
					if (floor.getSpaces() != null) { // to check floor consist of workspaces or not
						List<WorkSpace> workSpaces = floor.getSpaces(); // all workspaces in floor
						for (WorkSpace workSpace : workSpaces) {
							i++;
							if (workSpace.getType() == null) {
								continue;
							}
							if (workSpace.getType().equals(workSpaceType)) { // to find capacity workspaces
								buildingsByCapacity.add(building);
								i = buildingsByCapacity.size();
								break;

							}
						}
						if (buildingsByCapacity.size() == i) {
							break;
						} else {
							continue;
						}

					}

				}

			}
			if (buildingsByCapacity.isEmpty()) {
				throw new BuildingNotFound("Buildings not available with workspace type = " + workSpaceType);
			} else {
				ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
				structure.setMessege(buildingsByCapacity.size() + " Buildings are available");
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(buildingsByCapacity);
				return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
			}

		} else {
			throw new BuildingNotFound("Buildind not found...");
		}
	}

	public ResponseEntity<ResponceStructure<WorkSpace>> updateWorkSpace(WorkSpace workSpace) {
		WorkSpace dbWorkSpace = workSpaceDao.findWorkSpaceById(workSpace.getId());
		if (dbWorkSpace != null) {
			if (workSpace.getAvailability() != null) {
				dbWorkSpace.setAvailability(workSpace.getAvailability());

			}
			if (workSpace.getCapacity() != 0) {
				dbWorkSpace.setCapacity(workSpace.getCapacity());

			}

			if (workSpace.getPricePerday() != 0.0) {
				dbWorkSpace.setPricePerday(workSpace.getPricePerday());

			}
			if (workSpace.getSquareFeet() != null) {
				dbWorkSpace.setSquareFeet(workSpace.getSquareFeet());
			}
			if (workSpace.getType() != null) {
				dbWorkSpace.setType(workSpace.getType());
			}

			WorkSpace updatesDB = workSpaceDao.updateWorkSpace(dbWorkSpace);
			ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
			structure.setMessege("WorkSpace updated successfully......");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(updatesDB);
			return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.OK);

		} else {
			throw new WorkSpaceNotFound("WorkSpace not found with id = " + workSpace.getId());
		}
	}

	public ResponseEntity<ResponceStructure<List<Building>>> findWorkSpacesByCost(int cost) {
//		List<Building> dbBuilding = buildingDao.findAllBuiding(); // all buildings
//		List<Building> buildingsByCapacity = new ArrayList<Building>(); // to store buldings with same capacity
//					int i=0;	
//					System.out.println("dbBuildings are "+dbBuilding);// workaspaces
//		if (dbBuilding != null) {
//			
//			for (Building building : dbBuilding) {
//				i++;
//				if (building.getFloors().isEmpty()) { // to check buildings consist of floors or not
//					continue;
//				}
//
//				List<Floor> dbListFloors = building.getFloors(); // all floors in buildings
//				for (Floor floor : dbListFloors) {
//					i++;
//					if (floor.getSpaces() != null) { // to check floor consist of workspaces or not
//						List<WorkSpace> workSpaces = floor.getSpaces(); // all workspaces in floor
//						for (WorkSpace workSpace : workSpaces) {
//							i++;
//							if (workSpace.getPricePerday() == cost) { // to find capacity workspaces
//								buildingsByCapacity.add(building);
//								i=buildingsByCapacity.size();
//								break;
//
//							}
//						}
//						if(buildingsByCapacity.size()==i) {
//							break;
//						}
//						else {
//							continue;
//						}
//
//					}
//
//				}
//				if(buildingsByCapacity.size()==i) {
//					break;
//				}
//				else {
//					continue;
//				}
//
//			}
//			if (buildingsByCapacity.isEmpty()) {
//				throw new BuildingNotFound("Buildings not available with cost = " + cost);
//			} else {
//				ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
//				structure.setMessege(buildingsByCapacity.size() + " Buildings are available");
//				structure.setStatus(HttpStatus.FOUND.value());
//				structure.setData(buildingsByCapacity);
//				return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
//			}
//
//		} else {
//			throw new BuildingNotFound("Buildind not found with cost = " + cost);
//		}

		List<Building> dbBuilding = buildingDao.findAllBuiding(); // all buildings
		List<Building> buildingsByCapacity = new ArrayList<Building>(); // to store buldings with same capacity
		int i = 0; // workaspaces
		if (dbBuilding != null) {
			for (Building building : dbBuilding) {
				if (building.getFloors().isEmpty()) { // to check buildings consist of floors or not
					continue;
				}
				i++;
				List<Floor> dbListFloors = building.getFloors(); // all floors in buildings
				for (Floor floor : dbListFloors) {
					i++;
					if (floor.getSpaces() != null) { // to check floor consist of workspaces or not
						List<WorkSpace> workSpaces = floor.getSpaces(); // all workspaces in floor
						for (WorkSpace workSpace : workSpaces) {
							i++;
							if (workSpace.getPricePerday() == cost) { // to find capacity workspaces
								buildingsByCapacity.add(building);
								i = buildingsByCapacity.size();
								break;

							}
						}

					}
					if (buildingsByCapacity.size() != 0) {
						if (buildingsByCapacity.size() == i) {
							break;
						} else {
							continue;
						}
					}

				}
//				if (buildingsByCapacity.size() != 0) {
//					if (buildingsByCapacity.size() == i) {
//						break;
//					} else {
//						continue;
//					}
//				}

			}

			if (buildingsByCapacity.isEmpty()) {
				throw new BuildingNotFound("Buildings not available with cost = " + cost);
			} else {
				ResponceStructure<List<Building>> structure = new ResponceStructure<List<Building>>();
				structure.setMessege(buildingsByCapacity.size() + " Buildings are available");
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(buildingsByCapacity);
				return new ResponseEntity<ResponceStructure<List<Building>>>(structure, HttpStatus.FOUND);
			}

		} else {
			throw new BuildingNotFound("Buildind not found...");
		}
	}

	public ResponseEntity<ResponceStructure<WorkSpace>> deleteWorkSpaceById(int id) {
		List<Building> list = buildingDao.findAllBuiding();
		List<Floor> newFloors = new ArrayList<Floor>();
		List<WorkSpace> newWorkSpace = new ArrayList<WorkSpace>();
		int n = 0;
		for (Building building : list) {
			if (building.getFloors() != null) {
				List<Floor> listFloors = building.getFloors();
				for (Floor floor : listFloors) {
					if (floor.getSpaces() != null) {
						if (floor.getSpaces() != null) {
							List<WorkSpace> listWorksapce = floor.getSpaces();
							for (WorkSpace workSpace : listWorksapce) {
								if (workSpace.getId() == id) {
									for (WorkSpace workSpace2 : listWorksapce) {
										if (workSpace2.getId() == id) {
											workSpace.setClient(null);
											workSpaceDao.saveWorkSpaces(workSpace2);
											continue;
										} else {
											newWorkSpace.add(workSpace2);
										}

									}
									floor.setSpaces(newWorkSpace);

								}
							}

						}
					}
				}
			}
		}

		WorkSpace dbWorkSpace = workSpaceDao.deleteWorkSpaceById(id);
		if (dbWorkSpace != null) {
			ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
			structure.setMessege("WorkSapce deleted with id " + id + " successfully...");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbWorkSpace);
			return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.OK);
		}
		throw new WorkSpaceNotFound("workSpace not exist with id = " + id);
	}

	public ResponseEntity<ResponceStructure<Integer>> allWorkSpaceCost() {
		List<WorkSpace> dballworksapces = workSpaceDao.findAllWorkSpacess();
		List<WorkSpace> fil = dballworksapces.stream().filter(e -> e.getPricePerday() == 500)
				.collect(Collectors.toList());

		System.out.println("price per day*****************************" + fil);

		List<Double> mapp = dballworksapces.stream().map(e -> (e.getPricePerday() + 50)).collect(Collectors.toList());
		System.out.println("map************************************" + mapp); // [550, 1050,2050......]

		List<String> mapp1 = dballworksapces.stream().map(e -> e.getAvailability().toUpperCase())
				.collect(Collectors.toList());
		System.out.println("map availability ********************************" + mapp1); // [YES, YES,YES......]

		dballworksapces.stream().map(e -> e.getPricePerday() + 50)
				.forEach(y -> System.out.println("foreach******" + y)); // iterting
		Double red = dballworksapces.stream().map(e -> e.getPricePerday()).reduce(0.0, (fir, sec) -> fir + sec);
		System.out.println("reduced amount************************" + red); // total amount

		List<WorkSpace> redByCapacity = dballworksapces.stream().sorted((p1, p2) -> p1.getCapacity() - p2.getCapacity())
				.collect(Collectors.toList());
		System.out.println("sort by capicity ************************"+redByCapacity);
		boolean anyMatch = dballworksapces.stream().anyMatch(e->e.getPricePerday()==500);
		System.out.println("anymatch*************************"+anyMatch);
		boolean allMatch=dballworksapces.stream().allMatch(e->e.getPricePerday()==500);
		System.out.println("allmatch**************************"+allMatch);
		List<WorkSpace> distict = dballworksapces.stream().distinct().collect(Collectors.toList());
		System.out.println("distinct******************"+distict);
		WorkSpace max = dballworksapces.stream().max((p1,p2)->p1.getCapacity()-p2.getCapacity()).get();
		System.out.println("max***************************"+max);
		long count = dballworksapces.stream().count();
		System.out.println("count****************************"+count);

		Long st = dballworksapces.stream().filter(e -> e.getPricePerday() == 500).map(m -> m.getPricePerday())
				.collect(Collectors.counting());

		System.out.println("long filter is " + st);
		ResponceStructure<Integer> structure = new ResponceStructure<Integer>();
		structure.setMessege("success");
		structure.setData(1000000);
		structure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponceStructure<Integer>>(structure, HttpStatus.OK);
	}

}
