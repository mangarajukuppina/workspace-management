package com.jsp.workSpace.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Address;
import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.repo.AddressRepo;
import com.jsp.workSpace.repo.AdminRepo;

@Repository
public class AdminDao {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private AddressDao addressDao;

	public Admin saveAdmin(Admin admin) {
		Admin dbAdmin = adminRepo.save(admin);
		if(dbAdmin!=null) {
			return dbAdmin;
		}
		else {
			return null;
			
		}
	}

	public Admin findAdminById(int id) {
		

		if (adminRepo.findById(id).isPresent()) {
			Admin admin = adminRepo.findById(id).get();

			return admin;

		} else {
			return null;
		}
	}

	public List<Admin> findAdminByName(String name) {
		List<Admin> dbAdmin = adminRepo.findAdminByName(name);
		if (dbAdmin.isEmpty()) {
		
			return null;
		} else {
			return dbAdmin;
		}

	}
	public Admin findByEmail(String email) {
		Admin db = adminRepo.findAdminByEmail(email);
		if(db!=null) {
			return db;
		}
		else {
			return null;
		}
	}

	public List<Admin> fetchAllAdmin() {
		return adminRepo.fetchAllAdmin();
		
	}

	public Admin updateAdmin(Admin admin) {

		if (adminRepo.findById(admin.getId()).isPresent()) {

			Admin db = adminRepo.findById(admin.getId()).get();

			if (admin.getName() != null) {
				db.setName(admin.getName());
			}
			if (admin.getEmail() != null) {
				db.setEmail(admin.getEmail());
			}
			if (admin.getPhone() != 0) {
				db.setPhone(admin.getPhone());
			}
			if (admin.getPassword() != null) {
				db.setPassword(admin.getPassword());
			}

			if (admin.getGender() != null) {
				db.setGender(admin.getGender());
			}
			Admin updateDb = adminRepo.save(db);
			return updateDb;

		} else {
			return null;
		}

	}

//	public Admin assignAddress(int adminId, int addressId) {
//		Admin dbAdmin = adminRepo.findById(adminId).get();
//
//		Address dbAddress = addressDao.findById(addressId);
//		dbAdmin.setAddress(dbAddress);
//		Admin dbAdmin1 = adminRepo.save(dbAdmin);
//
//		if (dbAdmin1 != null) {
//			return dbAdmin1;
//		} else {
//			return null;
//		}
//
//	}

	public Admin deleteAdminById(int id) {
		if (adminRepo.findById(id).isPresent()) {
			Admin db = adminRepo.findById(id).get();
			adminRepo.deleteById(id);
			return db;

		} else {
			return null;
		}
	}

	public Admin deleteAllAdminById(int adminId) {
		if (adminRepo.findById(adminId).isPresent()) {
			Admin dbAdmin = adminRepo.findById(adminId).get();
			adminRepo.deleteById(adminId);
			return dbAdmin;

		} else {
			return null;
		}

	}
}
