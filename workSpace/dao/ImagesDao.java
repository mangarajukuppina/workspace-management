package com.jsp.workSpace.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.workSpace.dto.Image;
import com.jsp.workSpace.repo.ImageRepo;


@Repository

public class ImagesDao {
	
	@Autowired
	private ImageRepo imageRepo;

	public Image saveImage(Image image) {
		if (image != null) {
			return imageRepo.save(image);

		} else {
			return null;
		}
	}
	
	public Image findImageById(int id) {
		if(imageRepo.findById(id).isPresent()) {
			Image image = imageRepo.findById(id).get();
			return image;
		}else {
			return null;
		}
		
	}

}
