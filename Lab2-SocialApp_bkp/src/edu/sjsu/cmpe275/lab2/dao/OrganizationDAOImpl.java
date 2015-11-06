package edu.sjsu.cmpe275.lab2.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Organization;
import edu.sjsu.cmpe275.lab2.entities.Person;
 
@Transactional
public class OrganizationDAOImpl implements OrganizationDAO {
 
     private SessionFactory sessionFactory;
 
        public OrganizationDAOImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }
 
    @Override
    public void add(Organization organization) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(organization);
            System.out.println("create organization result: success");
          } catch (HibernateException e) {
              e.printStackTrace();
              session.getTransaction().rollback();
        }
            session.getTransaction().commit();
    }
 
    @Override
    public void update(Organization organization) {
 
        Session session = sessionFactory.getCurrentSession();
        try {
            System.out.println("IN Update");
            session.beginTransaction();
            session.saveOrUpdate(organization);
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        session.getTransaction().commit();
    }
 
    @Override
    public Organization  getOrganization(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Organization organization=null;
        try {
            System.out.println("IN GetOrganization");
            session.beginTransaction();
            organization = (Organization) session.get(Organization.class, id);
           // System.out.println("Person name: "+person.getFirstName());
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return  organization;
    }
 
    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Organization organization = (Organization) session.get(Organization.class, id);
        if(null != organization) {
            session.delete(organization);
        }
        session.getTransaction().commit();
  
    }
 
 
 
}