package edu.sjsu.cmpe275.lab2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.cmpe275.lab2.dao.FriendshipDAO;


@Controller
public class FriendshipController {
	// Initialized via Bean
	@Autowired
	private FriendshipDAO friendshipDao;

	
	//Add friend
	@RequestMapping(value = "/friends/{id1}/{id2}", method = RequestMethod.PUT, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> addFriend(
			@PathVariable("id1") long id1,@PathVariable("id2") long id2) {
		HttpHeaders responseHeaders = new HttpHeaders();
		String responseMessage=friendshipDao.add(id1, id2);
		
		if(responseMessage.equals("InvalidReq"))
			return new ResponseEntity<String>("Either of the persons do not exist!", responseHeaders,
				HttpStatus.NOT_FOUND);
		
	
		return new ResponseEntity<String>(responseMessage, responseHeaders,
				HttpStatus.OK);
	}
	
	
	//Delete friend
	@RequestMapping(value = "/friends/{id1}/{id2}", method = RequestMethod.DELETE, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> deleteFriend(
			@PathVariable("id1") long id1,@PathVariable("id2") long id2) {
		HttpHeaders responseHeaders = new HttpHeaders();
	
		String responseMessage=friendshipDao.delete(id1, id2);
		
		if(!responseMessage.equals("DELETED"))
			return new ResponseEntity<String>(responseMessage, responseHeaders,
				HttpStatus.BAD_REQUEST);
		
	
		return new ResponseEntity<String>(responseMessage, responseHeaders,
				HttpStatus.OK);
	}
	
	
}
