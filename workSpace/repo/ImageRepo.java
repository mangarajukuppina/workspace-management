package com.jsp.workSpace.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.workSpace.dto.Image;

public interface ImageRepo extends JpaRepository<Image, Integer>{

}
