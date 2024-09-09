package com.jsp.workSpace.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.workSpace.dto.Admin;

public interface AdminRepo  extends JpaRepository<Admin, Integer>{
	@Query("select a from Admin a where a.name=?1")
	public List<Admin> findAdminByName(String name) ;
	
    @Query("select b from Admin b")
    public List<Admin> fetchAllAdmin();
    
    @Query("select a from Admin a where a.email=?1")
	public Admin findAdminByEmail(String email) ;
	
	

}
