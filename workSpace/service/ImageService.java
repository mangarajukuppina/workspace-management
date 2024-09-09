package com.jsp.workSpace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.workSpace.dao.ImagesDao;
import com.jsp.workSpace.dto.Image;
import com.jsp.workSpace.exception.ImageNotFound;
import com.jsp.workSpace.util.ResponceStructure;

@Service
public class ImageService {
	
	@Autowired
	private ImagesDao imageDao;

	public ResponseEntity<ResponceStructure<Image>> saveImage(byte[] imageData) {

		if (imageData != null) {
			Image image = new Image();

			image.setImageData(imageData);
			Image dbImage = imageDao.saveImage(image);
			ResponceStructure<Image> structure = new ResponceStructure<>();
			structure.setMessege("image saved successfully.");
			structure.setData(dbImage);
			structure.setStatus(HttpStatus.CREATED.value());

			return new ResponseEntity<ResponceStructure<Image>>(structure, HttpStatus.CREATED);
		} else {

			throw new ImageNotFound("Please select an Image first");
		}

	}

	public ResponseEntity<ResponceStructure<Image>> findImageById(int imageId) {
		if (imageDao.findImageById(imageId) != null) {

			Image dbimage = imageDao.findImageById(imageId);
			ResponceStructure<Image> structure = new ResponceStructure<>();
			structure.setData(dbimage);
			structure.setMessege("Id " + imageId + " found successfully");
			structure.setStatus(HttpStatus.FOUND.value());

			return new ResponseEntity<ResponceStructure<Image>>(structure, HttpStatus.FOUND);

		} else {
			throw new ImageNotFound("image with particular id is not there. ");
		}
	}


}
