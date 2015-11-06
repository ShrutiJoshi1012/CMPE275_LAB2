package edu.sjsu.cmpe275.lab2.dao;



import edu.sjsu.cmpe275.lab2.entities.Organization;
 
public interface OrganizationDAO {
 
    public void add(Organization organization);
    public void  update(Organization organization);
    public Organization  getOrganization(Long id);
    public void  delete(Long id);
 
}