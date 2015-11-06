package edu.sjsu.cmpe275.lab2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import edu.sjsu.cmpe275.lab2.dao.OrganizationDAO;
import edu.sjsu.cmpe275.lab2.entities.Address;
import edu.sjsu.cmpe275.lab2.entities.Organization;


@Controller
public class OrganizationController {
	@Autowired
	private OrganizationDAO organizationDao;

	@RequestMapping(value = "/org", method = RequestMethod.POST,produces={"application/json"})
	public  @ResponseBody ModelAndView create(
			@RequestParam(value="name", required=true) String name,
			@ModelAttribute("description") String description,
			@ModelAttribute("street") String street,
			@ModelAttribute("city") String city,
			@ModelAttribute("state") String state,
			@ModelAttribute("zip") String zip,
			BindingResult result){
			HttpHeaders responseHeaders = new HttpHeaders();
			ModelAndView model = new ModelAndView();
		if(result.hasErrors()){
			return  model.addObject("response", new ResponseEntity<String>("Invalid Request", responseHeaders, HttpStatus.BAD_REQUEST));

		}
		Organization organization=new Organization();
	
		
		organization.setName(name);
		organization.setDescription(description);
		organization.setAddress(new Address(street,city,state,zip));
		
		organizationDao.add(organization);
		model.setView(new MappingJackson2JsonView());
		
		return model.addObject("createdOrganization", organization);
		
	}
}
