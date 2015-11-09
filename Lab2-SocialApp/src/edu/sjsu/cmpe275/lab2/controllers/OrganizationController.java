package edu.sjsu.cmpe275.lab2.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import edu.sjsu.cmpe275.lab2.dao.OrganizationDAO;
import edu.sjsu.cmpe275.lab2.entities.Address;
import edu.sjsu.cmpe275.lab2.entities.Organization;

@Controller
public class OrganizationController {
	
	//Initialized via Bean
	@Autowired
	private OrganizationDAO organizationDao;

	@RequestMapping(value = "/org", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> create(
			@RequestParam(value = "name", required = true) String name,
			@ModelAttribute("description") String description,
			@ModelAttribute("street") String street,
			@ModelAttribute("city") String city,
			@ModelAttribute("state") String state,
			@ModelAttribute("zip") String zip, BindingResult result) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (result.hasErrors()) {
			return new ResponseEntity<String>(
					"Invalid Request, Missing required parameter",
					responseHeaders, HttpStatus.BAD_REQUEST);
		}

		Organization organization = new Organization();

		organization.setName(name);
		organization.setDescription(description);
		organization.setAddress(new Address(street, city, state, zip));

		boolean isCreated = organizationDao.add(organization);
		if (!isCreated)
			return new ResponseEntity<String>("Organization creation failed",
					responseHeaders, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Organization>(organization, responseHeaders,
				HttpStatus.OK);

	}

	// Get Organization

	@RequestMapping(value = "/org/{id}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<?> get(@PathVariable("id") long id) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Organization organization = organizationDao.getOrganization(id);
		if (organization == null)
			return new ResponseEntity<String>(
					"No organization exists with this OrgId", responseHeaders,
					HttpStatus.NOT_FOUND);

		return new ResponseEntity<Organization>(organization, responseHeaders,
				HttpStatus.OK);
	}

	// Update Organization
	@RequestMapping(value = "/org/{id}", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody
	ResponseEntity<?> update(@PathVariable("id") long id,
			@RequestParam(value = "name", required = true) String name,
			@ModelAttribute("description") String description,
			@ModelAttribute("street") String street,
			@ModelAttribute("city") String city,
			@ModelAttribute("state") String state,
			@ModelAttribute("zip") String zip, BindingResult result) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (result.hasErrors()) {
			return new ResponseEntity<String>(
					"Invalid Request, Missing required parameter",
					responseHeaders, HttpStatus.BAD_REQUEST);
		}

		Organization organization = new Organization();
		organization.setOrganizationId(id);
		organization.setName(name);
		organization.setDescription(description);
		organization.setAddress(new Address(street, city, state, zip));
		boolean isUpdated = organizationDao.update(organization);
		if (!isUpdated)
			return new ResponseEntity<String>("Organization update failed",
					responseHeaders, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Organization>(organization, responseHeaders,
				HttpStatus.OK);
	}

	// Delete organization
	@RequestMapping(value = "/org/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		HttpHeaders responseHeaders = new HttpHeaders();

		Object organization = organizationDao.delete(id);
		if (organization == null)
			return new ResponseEntity<String>(
					"No Organization exists with this OrgId", responseHeaders,
					HttpStatus.NOT_FOUND);
		if (organization.equals(false))
			return new ResponseEntity<String>(
					"This Organization has people associated with it.",
					responseHeaders, HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Organization>((Organization) organization,
				responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/org/{id}", method = RequestMethod.GET, produces = { "text/html" })
	public Object getOrgHTML(@PathVariable("id") Long id, ModelAndView model,
			HttpServletRequest request) {
		model.setViewName("organization");
		Organization organization = organizationDao.getOrganization(id);
		if (organization == null) {
			model.addObject("message", "No organization exists with this OrgId");
			return new ResponseEntity<String>(
					"No organization exists with this OrgId",
					HttpStatus.NOT_FOUND);
		}
		model.addObject("message", "Organization Details");
		model.addObject("organization", organization);
		return model;
	}

}
