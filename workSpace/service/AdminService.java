package com.jsp.workSpace.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.clone.AdminClone;
import com.jsp.workSpace.dao.AddressDao;
import com.jsp.workSpace.dao.AdminDao;
import com.jsp.workSpace.dao.BuildingDao;
import com.jsp.workSpace.dao.FloorDao;
import com.jsp.workSpace.dao.ManagagerDao;
import com.jsp.workSpace.dao.WorkSpaceDao;
import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.dto.Floor;
import com.jsp.workSpace.dto.Manager;
import com.jsp.workSpace.dto.WorkSpace;
import com.jsp.workSpace.exception.AddressNotFound;
import com.jsp.workSpace.exception.AdminNotFound;
import com.jsp.workSpace.exception.IncorrectEmail;
import com.jsp.workSpace.exception.IncorrectPassword;
import com.jsp.workSpace.repo.AddressRepo;
import com.jsp.workSpace.repo.AdminRepo;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class AdminService {
	@Autowired
	private AdminDao admindao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private ManagagerDao managagerDao;
	@Autowired
	private FloorDao floorDao;
	@Autowired
	private WorkSpaceDao workSpaceDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponceStructure<AdminClone>> saveAdmin(Admin admin)
			throws SQLIntegrityConstraintViolationException {
//		if(admin.getAddress()!=null) {
//		
//		addressDao.saveAddress(admin.getAddress());
//		}

		Admin db = admindao.saveAdmin(admin);
		if (db != null) {
			
//			AdminClone adminClone=new AdminClone();
//			adminClone.setEmail(db.getEmail());
//			adminClone.setId(db.getId());
//			adminClone.setName(db.getName());
//			adminClone.setPhone(db.getPhone());
			AdminClone adminClone=this.modelMapper.map(db, AdminClone.class);  //map() is used to convert normal class member to clone class members add one dependecy(Mmodelmapper) for ModelMapper class present org.modelmapper package 

			ResponceStructure<AdminClone> structure = new ResponceStructure<AdminClone>();

			structure.setMessege("admin save successfully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(adminClone);
			return new ResponseEntity<ResponceStructure<AdminClone>>(structure, HttpStatus.CREATED);
		} else {
			throw new SQLIntegrityConstraintViolationException("Admin already exist with email = " + admin.getEmail());
		}
	}

	public ResponseEntity<ResponceStructure<Admin>> login(String email, String password) {
		Admin dbAdmin = admindao.findByEmail(email);
		if (dbAdmin != null) {
			if (dbAdmin.getPassword().equals(password)) {
				ResponceStructure<Admin> structure = new ResponceStructure<Admin>();
				structure.setMessege("Login successfully.....");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(dbAdmin);
				return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.OK);

			} else {
				throw new IncorrectPassword("Entered Password is incorrect..");

			}

		} else {
			throw new IncorrectEmail("Entered Email is incorrect..");
		}
	}

	public ResponseEntity<ResponceStructure<Admin>> findAdminById(int id) {
		Admin dbAdmin = admindao.findAdminById(id);

		if (dbAdmin != null) {

			ResponceStructure<Admin> structure = new ResponceStructure<>();
			structure.setMessege("Admin id " + id + "  found successfully.");
			// structure.setMessege("Admin id " + id + " found successfully.");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbAdmin);

			return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.FOUND);
		}

		else {
			throw new AdminNotFound("Admin is not present with id = " + id);
		}
	}

	public ResponseEntity<ResponceStructure<List<Admin>>> findAdminByName(String name) {
		List<Admin> dbAdmin = admindao.findAdminByName(name);
		System.out.println("database list is " + dbAdmin);
		if (dbAdmin != null) {
			ResponceStructure<List<Admin>> structure = new ResponceStructure<List<Admin>>();
			structure.setMessege(dbAdmin.size() + " Admin accounts found successfully with name " + name);
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbAdmin);
			return new ResponseEntity<ResponceStructure<List<Admin>>>(structure, HttpStatus.FOUND);

		} else {
			throw new AdminNotFound("Admin is not present with name " + name);

		}
	}

	public ResponseEntity<ResponceStructure<List<Admin>>> fetchAllAdmin() {
		List<Admin> dbAdmin = admindao.fetchAllAdmin();
		if (dbAdmin != null) {
			ResponceStructure<List<Admin>> listStructure = new ResponceStructure<List<Admin>>();
			listStructure.setMessege(dbAdmin.size() + " Admin list found successufully.....");
			listStructure.setStatus(HttpStatus.FOUND.value());
			listStructure.setData(dbAdmin);
			return new ResponseEntity<ResponceStructure<List<Admin>>>(listStructure, HttpStatus.FOUND);
		} else {
			throw new AdminNotFound("Admin is not present");
		}
	}

	public ResponseEntity<ResponceStructure<Admin>> updateAdmin(Admin admin) {
		Admin dbAdmin = admindao.findAdminById(admin.getId());

		if (dbAdmin != null) {

			if (dbAdmin.getAddress() != null) {
				admin.getAddress().setId(dbAdmin.getAddress().getId()); // to set address id from admin address id
				Address updatedDBAddress = addressDao.updateAddress(admin.getAddress());
				Admin updatedDBAdmin = admindao.updateAdmin(admin);
				if (updatedDBAdmin != null) {

					ResponceStructure<Admin> structure = new ResponceStructure<>();
					structure.setMessege("Admin id " + admin.getId() + " updated successfully.");
					structure.setStatus(HttpStatus.FOUND.value());
					structure.setData(dbAdmin);

					return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.FOUND);

				} else {
					throw new DataIntegrityViolationException("Alredy exist admin with email = " + admin.getEmail());
				}

			} else {
				throw new AddressNotFound("Address is not mapped with Admin id = " + dbAdmin.getId()
						+ " and  Admin Name = " + dbAdmin.getName());

			}
		} else {
			throw new AdminNotFound("Admin is not ther with id " + admin.getId());
		}

	}
//
//	public ResponseEntity<ResponceStructure<Admin>> assignAddress(int adminId, int addressId) {
//
//		if (admindao.findAdminById(adminId) != null) {
//
//			if (admindao.findAdminById(adminId).getAddress() == null) {
//
//				if (addressDao.findById(addressId) != null) {
//
//					Admin dbAdmin = admindao.assignAddress(adminId, addressId);
//					if (dbAdmin != null) {
//
//						ResponceStructure<Admin> structure = new ResponceStructure<>();
//						structure.setMessege("Admin assigneda address with id " + addressId + "successfully.");
//						structure.setStatus(HttpStatus.FOUND.value());
//						structure.setData(dbAdmin);
//
//						return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.FOUND);
//
//					} else {
//						throw new AdminNotFound("Admin is not present with id = " + adminId);
//
//					}
//				} else {
//					throw new AdminNotFound("Address is not present with id = " + addressId);
//				}
//
//			} else {
//				throw new AdminNotFound("admin is already assignes with address id = "
//						+ admindao.findAdminById(adminId).getAddress().getId());
//
//			}
//
//		} else {
//			throw new AdminNotFound("Admin is not present with id = " + adminId);
//		}
//	}

	public ResponseEntity<ResponceStructure<Admin>> deleteAdminById(int id) {

		Admin dbAdmin = admindao.deleteAdminById(id);
		if (dbAdmin != null) {
			if (dbAdmin.getAddress() != null) {
				addressDao.deleteAddressById(dbAdmin.getAddress().getId());
			}

			ResponceStructure<Admin> structure = new ResponceStructure<Admin>();
			structure.setMessege("Admin id " + id + " deleted successfully");
			structure.setStatus(HttpStatus.ACCEPTED.value());
			structure.setData(dbAdmin);
			return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.ACCEPTED);
		} else {
			throw new AdminNotFound("Admin is not present with id = " + id);
		}

	}

	public ResponseEntity<ResponceStructure<Admin>> deleteAllAdminById(int adminId) {
		Admin dbAdmin = admindao.findAdminById(adminId);
		if (dbAdmin != null) {
			if (dbAdmin.getAddress() != null) {
				addressDao.deleteAddressById(dbAdmin.getAddress().getId()); // to delete admin address

			}
			admindao.deleteAllAdminById(adminId);
			// to delete admin
			List<Building> listBuildings = buildingDao.findAllBuiding();
			if (listBuildings != null) {
				for (Building building : listBuildings) {
					if (building.getAdmin() != null) {
						if (building.getAdmin().getId() == dbAdmin.getId()) {

							if (building.getManager() != null) {
								if (building.getManager().getAddress() != null) {
									addressDao.deleteAddressById(building.getManager().getAddress().getId()); // to
																												// delete
																												// manager
																												// address
								}
								managagerDao.deleteManager(building.getManager().getId()); // to delete manager
							}
							if (building.getFloors().isEmpty()) {

							} else {
								List<Floor> listFloors = building.getFloors();
								for (Floor floor : listFloors) {
									if (floor.getSpaces().isEmpty()) {

									} else {
										List<WorkSpace> listSpaces = floor.getSpaces();
										for (WorkSpace workSpace : listSpaces) {
											workSpaceDao.deleteWorkSpaceById(workSpace.getId()); // to delete workSpaces
										}

									}
									floorDao.deleteFloorById(floor.getId()); // to delete foors
								}
							}
							if (building.getAddress() != null) {
								addressDao.deleteAddressById(building.getAddress().getId()); // to delete building
																								// address
							}
							buildingDao.deleteBuilding(building.getId()); // to delete buildings
						}
					}

				}
			}

			ResponceStructure<Admin> structure = new ResponceStructure<Admin>();
			structure.setMessege("Admin deleted successfully......");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbAdmin);
			return new ResponseEntity<ResponceStructure<Admin>>(structure, HttpStatus.OK);
		} else {
			throw new AdminNotFound("Admin not found with id = " + adminId);

		}

	}
}