package edu.sjsu.cmpe275.lab2.dao;



import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.entities.Organization;
import edu.sjsu.cmpe275.lab2.entities.Person;
 
@Transactional
public class PersonDAOImpl implements PersonDAO {
 
     private SessionFactory sessionFactory;
 
        public PersonDAOImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }
 
    @Override
    public Person add(Person person) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Organization organizationNew=new Organization();
           
            
            if(person.getOrganization() !=null){
            	//If person's organization is not required to be set
            	long orgId=person.getOrganization().getOrganizationId();
          
            	organizationNew=  (Organization) session.get(Organization.class, person.getOrganization().getOrganizationId());
            }
            if(organizationNew==null)
            	//If Organization does not exist in database return NULL
				return null;
			else
				//else set the organization properties to the Person
				 person.setOrganization(organizationNew);
           
            session.save(person);
            System.out.println("create person result: success");
          } catch (HibernateException e) {
              e.printStackTrace();
              session.getTransaction().rollback();
        }
            session.getTransaction().commit();
			return person;
    }
 
    @Override
    public void update(Person person) {
 
        Session session = sessionFactory.getCurrentSession();
        try {
            System.out.println("IN Update");
            session.beginTransaction();
            session.saveOrUpdate(person);
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        session.getTransaction().commit();
    }
 
    @Override
    public Person getPerson(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Person person=null;
        try {
            System.out.println("IN GetPerson");
            session.beginTransaction();
            person = (Person) session.get(Person.class,id);
           // System.out.println("Person name: "+person.getFirstName());
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.getTransaction().commit();
        return person;
    }
 
    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Person item = (Person) session.get(Person.class, id);
        if(null != item) {
            session.delete(item);
        }
        session.getTransaction().commit();
        //return item;
    }
 
 
 
}