package com.jsp.workSpace.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.workSpace.dto.Address;

public interface AddressRepo  extends JpaRepository<Address, Integer>{
	@Query("select a from Address a where a.city=?1")
	public List<Address> findByCityName(String cityName);

}
