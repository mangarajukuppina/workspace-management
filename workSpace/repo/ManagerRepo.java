package com.jsp.workSpace.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.workSpace.dto.Admin;
import com.jsp.workSpace.dto.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Integer>{
	@Query("select a from Manager a where a.experience=?1")
	public List<Manager> findManagerByExperience(int experience) ;
	@Query("select a from Manager a where a.email=?1")
	public Manager findManagerByEmail(String email);

}
