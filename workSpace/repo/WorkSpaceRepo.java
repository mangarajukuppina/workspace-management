package com.jsp.workSpace.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.workSpace.dto.WorkSpace;

public interface WorkSpaceRepo extends JpaRepository<WorkSpace, Integer> {

}
