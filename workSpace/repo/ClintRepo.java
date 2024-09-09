package com.jsp.workSpace.repo;

import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.workSpace.dto.Clint;

public interface ClintRepo extends JpaRepository<Clint, Integer>{
	@Query("select a from Clint a where a.email=?1")
	public Clint findClientByEmail(String email) ;
}
