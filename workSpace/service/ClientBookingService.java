package com.jsp.workSpace.service;

//import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.ClientBookingDao;
import com.jsp.workSpace.dao.ClientDao;
import com.jsp.workSpace.dao.WorkSpaceDao;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.dto.Clint;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.exception.BuildingNotFound;
import com.jsp.workSpace.exception.ClientBookingNotFound;
import com.jsp.workSpace.exception.ClientNotFound;
import com.jsp.workSpace.exception.InCorrectCost;
import com.jsp.workSpace.exception.WorkSpaceNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class ClientBookingService {

	@Autowired
	private ClientBookingDao clientBookingDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private WorkSpaceDao workSpaceDao;
	@Autowired
	private BuildingDao buildingDao;

	public ResponseEntity<ResponceStructure<List<WorkSpace>>> bookWotkSpaces(ClientBooking clientBooking, int clientId,
			List<Integer> list) {
		Clint dbClient = clientDao.findbyId(clientId);

		List<Clint> listClient = new ArrayList<Clint>();
		listClient.add(dbClient);
		List<WorkSpace> listWorkSpaces = new ArrayList<WorkSpace>();
		if (dbClient != null) {
			for (Integer integer : list) {
				WorkSpace workSpace = workSpaceDao.findWorkSpaceById(integer);
				if (workSpace != null) {
					workSpace.setClient(listClient);
					listWorkSpaces.add(workSpace);
					clientBookingDao.save(clientBooking);

				} else {
					throw new WorkSpaceNotFound("workSpace not exist with id = " + integer);
				}

			}
			workSpaceDao.saveWorkSpaces(listWorkSpaces);
			ResponceStructure<List<WorkSpace>> structure = new ResponceStructure<List<WorkSpace>>();
			structure.setMessege("Request successfully sent waiting for manager conformation");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(listWorkSpaces);
			return new ResponseEntity<ResponceStructure<List<WorkSpace>>>(structure, HttpStatus.OK);

		}
		throw new ClientNotFound("Client not found with id = " + clientId);

	}

	public ResponseEntity<ResponceStructure<WorkSpace>> bookOneWorkSpace(int clientId, int workSpaceId,
			ClientBooking clientBooking) {
		Clint dbClient = clientDao.findbyId(clientId);
		WorkSpace dbWorkSpace = workSpaceDao.findWorkSpaceById(workSpaceId);
		if (dbClient != null) {
			if (dbWorkSpace != null) {
				if (dbWorkSpace.getAvailability().equalsIgnoreCase("yes")) {
					List<ClientBooking> listClientBookings = dbClient.getBookings();

					double pricePerDay = dbWorkSpace.getPricePerday();
					int day = (clientBooking.getEntryDate().getDate());
					int day1 = (clientBooking.getExitdate().getDate());

					int month = clientBooking.getEntryDate().getMonth();
					int month1 = clientBooking.getExitdate().getMonth();

					int year = clientBooking.getEntryDate().getYear();
					int year1 = clientBooking.getExitdate().getYear();

					int totalDays = ((day1 - day) + ((month1 - month) * 30) + ((year1 - year) * 365));
					System.out.println("total days " + totalDays);
					double cost = dbWorkSpace.getPricePerday() * totalDays;
					System.out.println("price per day" + dbWorkSpace.getPricePerday());

					System.out.println("price per day * total days = " + cost);
					clientBooking.setPayment("pending");

					clientBooking.setCost(cost);
					dbWorkSpace.setAvailability("no");

					listClientBookings.add(clientBooking);
					dbClient.setBookings(listClientBookings); // to set clientBookings to client
					List<Clint> dbWorkSpaceClients = dbWorkSpace.getClient();
					dbWorkSpaceClients.add(dbClient);
					dbWorkSpace.setClient(dbWorkSpaceClients); // to set client to workSPace
					WorkSpace updatedDbWorkSpace = workSpaceDao.saveWorkSpaces(dbWorkSpace);
					System.out.println(updatedDbWorkSpace);
				

					ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
					structure.setMessege("Request successfully sent waiting for manager conformation");
					structure.setStatus(HttpStatus.OK.value());
					structure.setData(updatedDbWorkSpace);
					return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.OK);

				} else {
					throw new WorkSpaceNotFound(
							"this workspace not available select another workSpace which are availabe");
				}
			} else {
				throw new WorkSpaceNotFound("workSpace not exist with id " + workSpaceId);
			}

		} else {
			throw new ClientNotFound("Client not exist with id " + clientId);
		}

	}
//		Clint dbClient = clientDao.findbyId(clientId);
//		List<Clint> listClient = new ArrayList<Clint>();
//		WorkSpace dbWorkSpace = workSpaceDao.findWorkSpaceById(workSpaceId);
//		if (dbClient != null) {
//			if (dbWorkSpace != null) {
//				if (dbWorkSpace.getAvailability().equalsIgnoreCase("yes")) {
//
//					double pricePerDay = dbWorkSpace.getPricePerday();
//					int day = (clientBooking.getEntryDate().getDate());
//					int day1 = (clientBooking.getExitdate().getDate());
//
//					int month = clientBooking.getEntryDate().getMonth();
//					int month1 = clientBooking.getExitdate().getMonth();
//
//					int year = clientBooking.getEntryDate().getYear();
//					int year1 = clientBooking.getExitdate().getYear();
//
//					int totalDays = ((day1 - day) + ((month1 - month) * 30) + ((year1 - year) * 365));
//					System.out.println("total days " + totalDays);
//					double cost = dbWorkSpace.getPricePerday() * totalDays;
//					System.out.println("price per day" + dbWorkSpace.getPricePerday());
//
//					System.out.println("price per day * total days = " + cost);
//					clientBooking.setPayment("pending");
//				   
//				    	List<ClientBooking> dbclientBookings = dbClient.getBookings();     //list of client booking of specific client
//				    	dbclientBookings.add(clientBooking);                               //add new client booking to list
//				    	dbClient.setBookings(dbclientBookings);                            //dbclient set clientBooking of specfic client
//				    	listClient.add(dbClient);                                          // client added to list
//						
//					
//                    
//					clientBooking.setCost(cost);                                            // to set client booking cost
//					dbWorkSpace.setAvailability("no");
//
//					dbWorkSpace.setClient(listClient);                                     // to set client to workSpace
//					System.out.println(clientBooking);
//					workSpaceDao.saveWorkSpaces(dbWorkSpace);                              // save workSpace in db (workSPace,client, bookings)
//					//ClientBooking dbclientBooking = clientBookingDao.save(clientBooking);
//					System.out.println("client booking object is "+dbClient);
//
//					ResponceStructure<WorkSpace> structure = new ResponceStructure<WorkSpace>();
//					structure.setMessege("Request successfully sent waiting for manager conformation");
//					structure.setStatus(HttpStatus.OK.value());
//					structure.setData(dbWorkSpace);
//					return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.OK);
//				} else {
//					throw new WorkSpaceNotFound("this workspace not available");
//				}
//			} else {
//				throw new WorkSpaceNotFound("workSpace not exist with id = " + workSpaceId);
//			}
//
//		} else {
//			throw new ClientNotFound("Client not exist with id = " + clientId);
//		}
//	}

	public ResponseEntity<ResponceStructure<ClientBooking>> payment(int client_id, int clientBooking_id,
			String payment, double cost) {
		Clint dbClient = clientDao.findbyId(client_id);
		ClientBooking dbClientBooking = clientBookingDao.findById(clientBooking_id);

		if (dbClient != null) {
			if (dbClientBooking != null) {
				if(dbClientBooking.getCost()==cost) {
				dbClientBooking.setPayment(payment);
				ClientBooking updatedDbClientBooking = clientBookingDao.save(dbClientBooking);
				ResponceStructure<ClientBooking> structure = new ResponceStructure<ClientBooking>();
				structure.setMessege("your payment successfully completed.....");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(updatedDbClientBooking);
				return new ResponseEntity<ResponceStructure<ClientBooking>>(structure, HttpStatus.OK);

				
				}
				else {
					throw new InCorrectCost("WorkSpace total amount "+dbClientBooking.getCost()+" so enter amount = "+dbClientBooking.getCost()+" try again");
				}
			} else {
				throw new ClientBookingNotFound("Client Booking not found with id = " + clientBooking_id);
			}

		} else {
			throw new ClientNotFound("Dear client Your not not exist with id = " + client_id);
		}

	}
	
	public ResponseEntity<ResponceStructure<WorkSpace>> removeWorkSpaceFromClient(int workSPace_id, int client_id, int clientBooking_id){
		
		WorkSpace dbWorkSPace = workSpaceDao.findWorkSpaceById(workSPace_id);
		Clint dbClient = clientDao.findbyId(client_id);
		ClientBooking dbClientBooking = clientBookingDao.findById(clientBooking_id);
		if(dbWorkSPace!=null) {
			if(dbClient!=null) {
				if(dbClientBooking!=null) {
					List<ClientBooking> dbClientBookings = dbClient.getBookings();   //total clientBookings of client
					System.out.println("total bookings of databseClient"+dbClientBookings);
					if(dbClientBookings!=null) {
						for (ClientBooking clientBooking : dbClientBookings) {
							
							if(clientBooking.getBooking_id()==clientBooking_id) {
								dbClientBookings.remove(clientBooking);                 // remove clientBooking from list of clientBookings
							  clientBookingDao.deleteClientBooking(clientBooking_id);  // to remove clientBooking from database
							}
							
						}
					}
				
					dbClient.setBookings(dbClientBookings);                       //set clientBookings to client
					List<Clint> dbClients = dbWorkSPace.getClient();              // list of clients from workSpace
					if(dbClients.isEmpty()) {
					System.out.println("getClients are "+dbClients);
					
					}
					else {
						for (Clint client : dbClients) {
							if(client.getId()==client_id) {
								dbClients.remove(client);                             //remover client
							}
							
						}
					}
					dbWorkSPace.setClient(dbClients);                             //set clients to workSpace
					dbWorkSPace.setAvailability("yes");
					workSpaceDao.saveWorkSpaces(dbWorkSPace);
					clientDao.saveClient(dbClient);
					
					ResponceStructure<WorkSpace> structure=new ResponceStructure<WorkSpace>();
					structure.setMessege("dear Manager WorkSpace updated as available successfully......");
					structure.setStatus(HttpStatus.OK.value());
					structure.setData(dbWorkSPace);
					return new ResponseEntity<ResponceStructure<WorkSpace>>(structure, HttpStatus.OK);
				
					
				}
				else {
					throw new ClientBookingNotFound("ClientBooking not exist with id = "+clientBooking_id);
				}
				
			}
			else {
				throw new ClientNotFound("client not exist with id = "+client_id);
			}
			
		}
		else {
			throw new WorkSpaceNotFound("WorkSpace not exist with id = "+workSPace_id);
		}
		
		
	}
	
	

	public ResponseEntity<ResponceStructure<ClientBooking>> findById(int id) {
		ClientBooking dbClientBooking = clientBookingDao.findById(id);
		if (dbClientBooking != null) {
			ResponceStructure<ClientBooking> structure = new ResponceStructure<ClientBooking>();
			structure.setMessege("ClientBooking found successfully.....");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbClientBooking);
			return new ResponseEntity<ResponceStructure<ClientBooking>>(structure, HttpStatus.FOUND);
		} else {
			throw new ClientBookingNotFound("ClientBooking not found with id = " + id);
		}
	}
//	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findPendingClientBookins(){
//		  List<ClientBooking> listReqClientBookings = clientBookingDao.findAllClientBookings();
//		 List<ClientBooking> reqList=new ArrayList<ClientBooking>();
//		if(listReqClientBookings!=null) {
//			for(ClientBooking clientBooking:listReqClientBookings) {
//				if(clientBooking.getPayment()!=null) {
//				if(clientBooking.getPayment().equals("pending")) {
//					reqList.add(clientBooking);
//				}
//				}
//			}
//			ResponceStructure<List<ClientBooking>> structure=new ResponceStructure<List<ClientBooking>>();
//			structure.setMessege(reqList.size()+" client bookings requisted...");
//			structure.setData(reqList);
//			structure.setStatus(HttpStatus.FOUND.value());
//			
//			
//			return new ResponseEntity<ResponceStructure<List<ClientBooking>>>(structure, HttpStatus.FOUND);
//		}
//		else {
//			throw new ClientBookingNotFound("requists not present");
//		}
//	}
	
	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findPendingClientBookins(int building_id){
		int i=0;
//		System.out.println("Building id is "+building_id);
		Building dbBuilding = buildingDao.findBuildingById(building_id);
//		System.out.println(dbBuilding+" databse bui;ding");
//		System.out.println(building_id+" building id");
		List<ClientBooking> pendindClientBookings=new ArrayList<ClientBooking>();
//		System.out.println(dbBuilding+" workspace");
		if(dbBuilding!=null) {
			
			if(dbBuilding.getFloors().isEmpty()) {
				
				
			}
			else {
				List<Floor> dbFloors = dbBuilding.getFloors();
				for(Floor floor:dbFloors) {
					if(floor.getSpaces().isEmpty()) {
						
					}
					else {
						List<WorkSpace> dbSapces = floor.getSpaces();
						
						for(WorkSpace workSpace:dbSapces) {
						
							if(workSpace.getClient().isEmpty()) {
								continue;
							}
							else {
								List<Clint> dbClients=workSpace.getClient();
							
//								System.out.println("total client ids are "+dbClients);
								for(Clint client:dbClients) {
									if(i==client.getId()) {
										break;
									}
									else {
										i=client.getId();
									}
									System.out.println("clients  are "+client.getId());
								
									if(client.getBookings().isEmpty()) {
										continue;
									}
									else {
										List<ClientBooking> dbClientBookings = client.getBookings();
										
										for(ClientBooking clientBooking:dbClientBookings) {
//											System.out.println("total client ids are "+clientBooking.getBooking_id());
										
											if(clientBooking.getPayment().equals("pending")||clientBooking.getPayment().equals("accepted")) {
												pendindClientBookings.add(clientBooking);
											
//												System.out.println(dbClientBookings+"Hiii");
												continue;
												
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if(pendindClientBookings.isEmpty()) {
				throw new ClientBookingNotFound("client bookings not found");
			}
			else {
				ResponceStructure<List<ClientBooking>> structure=new ResponceStructure<List<ClientBooking>>();
				structure.setData(pendindClientBookings);
				structure.setMessege("client bookings found");
				structure.setStatus(HttpStatus.FOUND.value());
				
				return new ResponseEntity<ResponceStructure<List<ClientBooking>>>(structure, HttpStatus.FOUND);
				
			}
			
		}
		
		
		else {
			throw new BuildingNotFound("Building not exist with id "+building_id);
		}
	}
		
	
	public ResponseEntity<ResponceStructure<ClientBooking>> acceptClientBooking(int id, String msg){
		ClientBooking dbClientBooking=clientBookingDao.findById(id);
		if(dbClientBooking!=null) {
			dbClientBooking.setPayment(msg);
		dbClientBooking = clientBookingDao.save(dbClientBooking);
			ResponceStructure<ClientBooking> structure=new ResponceStructure<ClientBooking>();
			structure.setData(dbClientBooking);
			structure.setMessege("ClientBooking requist accepted successfully");
			structure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponceStructure<ClientBooking>>(structure, HttpStatus.OK);
		}
		else {
			throw new ClientBookingNotFound("Client booking not found with id "+id);
		}
	}

	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findClientBookingbyClientId(int client_id) {
		Clint dbClient = clientDao.findbyId(client_id);
		if(dbClient!=null) {
			List<ClientBooking> dbClientBooking = dbClient.getBookings();
			if(dbClientBooking.isEmpty()) {
				throw new ClientBookingNotFound("dear client your boonkgs not there");
			}
			else {
				ResponceStructure<List<ClientBooking>> structure=new ResponceStructure<List<ClientBooking>>();
				structure.setData(dbClientBooking);
				structure.setMessege("client bookings found succesfully...");
				structure.setStatus(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponceStructure<List<ClientBooking>>>(structure, HttpStatus.FOUND);
			}
			
		}
		else {
			throw new ClientBookingNotFound("client not founf");
		}
	}

	public ResponseEntity<ResponceStructure<ClientBooking>> removeClientBookingById(int client_id, int clientBooking_id) {
		Clint dbClient = clientDao.findbyId(client_id);
		ClientBooking deleteCleintBooking=new ClientBooking();
		if(dbClient!=null) {
			List<ClientBooking> dbClientBookings = dbClient.getBookings();
			List<ClientBooking> dbClientbookingsnew=new ArrayList<ClientBooking>();
			if(dbClientBookings.isEmpty()) {
				throw new ClientBookingNotFound("client bookings not found");
			}
			else {
				for(ClientBooking clientBooking:dbClientBookings) {
					if(clientBooking.getBooking_id()==clientBooking_id) {
						 deleteCleintBooking = clientBooking;
						 dbClient.setBookings(null);
						 clientDao.saveClient(dbClient);
						 clientBookingDao.deleteClientBooking(clientBooking_id);
						 System.out.println("rajuuuuuuuuuuuuuuuuuuuuuuu");
					}
					else {
						dbClientbookingsnew.add(clientBooking);
					}
				}
				dbClient.setBookings(dbClientbookingsnew);
				dbClient=	clientDao.saveClient(dbClient);
				
				ResponceStructure<ClientBooking>  structure=new ResponceStructure<ClientBooking>();
				structure.setMessege("deleted successfuly...");
				structure.setData(deleteCleintBooking);
				structure.setStatus(HttpStatus.OK.value());
				return new ResponseEntity<ResponceStructure<ClientBooking>>(structure, HttpStatus.OK);
				
			}
		}
		else {
			throw new ClientNotFound("client not exist");
		}
	}

	public ResponseEntity<ResponceStructure<List<ClientBooking>>> findAcceptedClientBookings(int client_id) {
		Clint dbClient = clientDao.findbyId(client_id);
		List<ClientBooking> dbnewClientBookings=new ArrayList<ClientBooking>();
		if(dbClient!=null) {
			List<ClientBooking> dbClientBookings = dbClient.getBookings();
			if(dbClientBookings.isEmpty()) {
				throw new ClientBookingNotFound("clientBookings not found");
			}
			else {
				for(ClientBooking clientBooking:dbClientBookings) {
					
					if(clientBooking.getPayment().equals("accepted")) {
					System.out.println(clientBooking.getPayment());
					dbnewClientBookings.add(clientBooking);
					
				}
				}
				if(dbnewClientBookings.isEmpty()) {
					throw new ClientBookingNotFound("clientBookings not found");
				}
				else {
					
				
			
			ResponceStructure<List<ClientBooking>> structure=new ResponceStructure<List<ClientBooking>>();
			structure.setData(dbnewClientBookings);
			structure.setMessege("clientbookings found suceesfully..");
			structure.setStatus(HttpStatus.OK.value());
			
			return new ResponseEntity<ResponceStructure<List<ClientBooking>>>(structure, HttpStatus.OK);
			}
		}
		}
		throw new ClientNotFound("client not exist");
	}

}
