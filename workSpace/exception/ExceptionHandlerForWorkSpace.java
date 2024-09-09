package com.jsp.workSpace.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.jsp.workSpace.dto.Building;
import com.jsp.workSpace.util.ResponceStructure;

@RestControllerAdvice   //if exception is occurs spring search for this annotaion class
public class ExceptionHandlerForWorkSpace {
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ResponceStructure<String>> sqlExceptionHandler(SQLIntegrityConstraintViolationException e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(e.getMessage());
		structure.setMessege("you can't perform this operation...!");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponceStructure<String>> dataIntigrityViolationException(DataIntegrityViolationException e){
	
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(e.getMessage());
		structure.setMessege("your account already registered!");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public  ResponseEntity<ResponceStructure<String>> methodArgumentsTypeMissmatchExceptions(MethodArgumentTypeMismatchException e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("check details with datatype correctly and try again");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData("check details");
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ResponceStructure<String>> missingServletRequestParameterException(MissingServletRequestParameterException e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("MissMatching Arguments check number of argumens and position of arguments");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData("check passed values");
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.FOUND);
	}
	
	
	
	@ExceptionHandler(AdminNotFound.class)
	public ResponseEntity<ResponceStructure<String>> adminNotFound(AdminNotFound e){
		
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(e.getMsg());
		structure.setMessege("Admin not found so you can't perform this operation...!");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(ManagerNotFound.class)
	public ResponseEntity<ResponceStructure<String>> managerNotFound(ManagerNotFound e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Manager Not Found");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ManagerAlredyAssingnAnotherBuilding.class)
	public ResponseEntity<ResponceStructure<String>> managerAlredyassingAnotherBuilding(ManagerAlredyAssingnAnotherBuilding e){
		
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(e.getMsg());
		structure.setMessege("Manager Alredy assingn another building");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(BuildingNotFound.class)
	public ResponseEntity<ResponceStructure<String>> buildingNotFound(BuildingNotFound b){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(b.getMsg());
		structure.setMessege("Building not found so you cann't perform this operation");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
		
		
	}
	@ExceptionHandler(ManagerAlreadyExist.class)
	public ResponseEntity<ResponceStructure<String>> managerAlreadyExsist(ManagerAlreadyExist e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setData(e.getMsg());
		structure.setMessege("Managaer Account already exist");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponceStructure<String>> missMatchMethodSelection(HttpRequestMethodNotSupportedException e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("your method is not supported Plzz check method type and try adain");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AddressNotFound.class)
	public ResponseEntity<ResponceStructure<String>> addressNotFound(AddressNotFound e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Address not found");
		structure.setData(e.getMsg());
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(FloorNotFound.class)
	public ResponseEntity<ResponceStructure<String>> floorNotFound(FloorNotFound e){
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Floor not found");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(WorkSpaceNotFound.class)
	public ResponseEntity<ResponceStructure<String>> workSpaceNotFound(WorkSpaceNotFound e){
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("WorkSpace not exist");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IncorrectEmail.class)
	public ResponseEntity<ResponceStructure<String>> incorrectEmail(IncorrectEmail e){
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Incorrect Email");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IncorrectPassword.class)
	public ResponseEntity<ResponceStructure<String>> incorrectPassword(IncorrectPassword e){
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Incorrect Password");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ClientNotFound.class)
	public ResponseEntity<ResponceStructure<String>> clientNotFound(ClientNotFound e){
		
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Client not found");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ClientBookingNotFound.class)
	public ResponseEntity<ResponceStructure<String>> clientBookingNotFound(ClientBookingNotFound e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setMessege("Client Booking not found");
		structure.setData(e.getMsg());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InCorrectCost.class)
	public ResponseEntity<ResponceStructure<String>> incorrectCost(InCorrectCost e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("Entered amount is incorrect.....");
		structure.setData(e.getMsg());
		structure.setStatus(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.OK);
		
	}
	@ExceptionHandler(InCorrectRating.class)
	public ResponseEntity<ResponceStructure<String>> inCorrectRating(InCorrectRating e){
		ResponceStructure<String> structure=new ResponceStructure<String>();
		structure.setMessege("InCorrect rating");
		structure.setData(e.getMsg());
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	


}
