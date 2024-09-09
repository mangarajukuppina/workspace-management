package com.jsp.workSpace.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.jsp.workSpace.dto.ClientBooking;
import com.jsp.workSpace.util.ResponceStructure;

public interface ClientBookingRepo extends JpaRepository<ClientBooking, Integer>{
//	@Query("select a from ClientBooking a where a.pending=?1")
//	public List<ClientBooking> findPendingClientBooking(String pending);
//
}
