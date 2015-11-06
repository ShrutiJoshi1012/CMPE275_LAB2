package edu.sjsu.cmpe275.lab2.controllers;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import javax.validation.*;

import edu.sjsu.cmpe275.lab2.entities.*;
import edu.sjsu.cmpe275.lab2.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.Model;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Controller
public class PersonController {

	@Autowired
	private PersonDAO personDao;
	//ArrayList<MediaType> mediaTypes = new ArrayList<MediaType>();

	@RequestMapping(value = "/person", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> create(
			@RequestParam(value = "firstname", required = true) String firstname,
			@RequestParam(value = "lastname", required = true) String lastname,
			@RequestParam(value = "email", required = true) String email,
			@ModelAttribute("description") String description,
			@ModelAttribute("street") String street,
			@ModelAttribute("city") String city,
			@ModelAttribute("state") String state,
			@ModelAttribute("zip") String zip,
			@RequestParam(value = "orgid", required = false) Long organizationid,
			BindingResult result) {
		HttpHeaders responseHeaders = new HttpHeaders();
		ModelAndView model = new ModelAndView();
		model.setView(new MappingJackson2JsonView());
		if (result.hasErrors()) {
			return new ResponseEntity<String>(
					"Invalid Request, Missing required parameter",
					responseHeaders, HttpStatus.BAD_REQUEST);
		}

		Person person = new Person();
		person.setFirstName(firstname);
		person.setLastName(lastname);
		person.setEmail(email);
		person.setDescription(description);
		person.setAddress(new Address(street, city, state, zip));
		if (organizationid != null)
			person.setOrganization(new Organization(organizationid));
		Person createdPerson = personDao.addPerson(person);

		if (createdPerson == null) {
			return new ResponseEntity<String>(
					"CreatePerson Failed, Org ID does not exist in Organization",
					responseHeaders, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Person>(createdPerson, responseHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET, produces = {
			"text/html", "application/json", "application/xml" })
	public @ResponseBody ResponseEntity<?> get(@PathVariable("id") long id) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Person person = personDao.getPerson(id);
		if (person == null)
			return new ResponseEntity<String>(
					"No person exists with this PersonId", responseHeaders,
					HttpStatus.NOT_FOUND);
		
		
		ArrayList<Object> obj= new ArrayList<Object>();
		obj.add(0,person);
		obj.add(1, person.getFriends());
		return new ResponseEntity<ArrayList<Object>>(obj, responseHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> update(
			@RequestParam(value = "firstname", required = true) String firstname,
			@RequestParam(value = "lastname", required = true) String lastname,
			@RequestParam(value = "email", required = true) String email,
			@ModelAttribute("description") String description,
			@ModelAttribute("street") String street,
			@ModelAttribute("city") String city,
			@ModelAttribute("state") String state,
			@ModelAttribute("zip") String zip,
			@RequestParam(value = "orgid", required = false) Long organizationid,
			@PathVariable("id") long id, BindingResult result) {
			HttpHeaders responseHeaders = new HttpHeaders();

		if (result.hasErrors()) {
			return new ResponseEntity<String>(
					"Invalid Request, Missing required parameter",
					responseHeaders, HttpStatus.BAD_REQUEST);
		}

		Person person = new Person(id, firstname, lastname, email);
		person.setDescription(description);
		person.setAddress(new Address(street, city, state, zip));

		if (organizationid != null)
			person.setOrganization(new Organization(organizationid));
		Person updatedPerson = personDao.updatePerson(person);
		if (updatedPerson == null) {
			return new ResponseEntity<String>(
					"updatePerson Failed, either the PersonId or OrganizationId does not exist",
					responseHeaders, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Person>(updatedPerson, responseHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		HttpHeaders responseHeaders = new HttpHeaders();

		Person person = personDao.deletePerson(id);
		if (person == null)
			return new ResponseEntity<String>(
					"No person exists with this PersonId", responseHeaders,
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<Person>(person, responseHeaders,
				HttpStatus.OK);
	}

}