package edu.sjsu.cmpe275.lab2.dao;



import edu.sjsu.cmpe275.lab2.entities.Organization;
 
public interface OrganizationDAO {
 
    public boolean add(Organization organization);
    public boolean  update(Organization organization);
    public Organization  getOrganization(Long id);
    public Object  delete(Long id);
 
}