package edu.sjsu.cmpe275.lab2.dao;


/**
 * Class: PersonDAOImpl
 * Implements: PersonDAO
 * isTransactional: Yes
 * Isolation level :Default
 * Transaction Propagation: REQUIRED
 * Dependencies : Uses Hibernate to access DB
 * 
 * 
 * Methods:
 * 1> addPerson
 * 2> updatePerson
 * 3> getPerson
 * 4> deletePerson
 * 5> getFriends
 * 
 * 
 */


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.transaction.annotation.*;

import edu.sjsu.cmpe275.lab2.entities.Friendship;
import edu.sjsu.cmpe275.lab2.entities.Organization;
import edu.sjsu.cmpe275.lab2.entities.Person;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class PersonDAOImpl implements PersonDAO {

	private SessionFactory sessionFactory;

	public PersonDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	//1> Create a Person in the database
	@Override
	public Person addPerson(Person person) {
		System.out.println("IN CreatePerson");
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Organization organizationNew = new Organization();

			if (person.getOrganization() != null) {
				// If person's organization is not required to be set
				long orgId = person.getOrganization().getOrganizationId();
				organizationNew = (Organization) session.get(
						Organization.class, orgId);
				if (organizationNew == null)
					// If Organization does not exist in database return NULL
					return null;
				else
					// else set the organization properties to the Person
					person.setOrganization(organizationNew);
			}

			session.save(person);
			session.getTransaction().commit();
			System.out.println("create person result: success");
		} catch (ConstraintViolationException e) {
			System.out
					.println("EmailID has to be a unique value. This value already exists.");
			person.setDescription("EmailError");
			session.getTransaction().rollback();
			return person;
		} catch (JDBCConnectionException e) {
			System.out.println("Connection lost");
			session.getTransaction().rollback();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return person;

	}

	
	
	//2> Update Person in database
	@Override
	public Person updatePerson(Person person) {

		Session session = sessionFactory.getCurrentSession();
		try {
			System.out.println("IN UpdatePerson");
			session.beginTransaction();
			Organization organization = new Organization();
			Person personBeforeUpdate = (Person) session.get(Person.class,
					person.getPersonId());

			if (personBeforeUpdate == null)
				return null;
			if (person.getOrganization() != null) {
				// If person's organization is not required to be set
				long orgId = person.getOrganization().getOrganizationId();

				organization = (Organization) session.get(Organization.class,
						person.getOrganization().getOrganizationId());
				if (organization == null)
					// If Organization does not exist in database return NULL
					return null;
				else
					// else set the organization properties to the Person
					person.setOrganization(organization);
			}

			personBeforeUpdate.setAddress(person.getAddress());
			personBeforeUpdate.setDescription(person.getDescription());
			personBeforeUpdate.setEmail(person.getEmail());
			personBeforeUpdate.setFirstName(person.getFirstName());
			personBeforeUpdate.setLastName(person.getLastName());
			personBeforeUpdate.setOrganization(person.getOrganization());
			session.saveOrUpdate(personBeforeUpdate);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return person;
	}

	
	
	//3> GET person from database
	@Override
	public Person getPerson(Long id) {
		System.out.println("IN GetPerson");
		Session session = sessionFactory.getCurrentSession();
		Person person = null;
		try {

			session.beginTransaction();
			person = (Person) session.get(Person.class, id);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return person;
	}

	
	// 4> Delete person from database 
	@Override
	public Person deletePerson(Long id) {
		System.out.println("IN DeletePerson");
		Session session = sessionFactory.getCurrentSession();
		Person person = null;
		try {
			session.beginTransaction();
			person = (Person) session.get(Person.class, id);
			if (person != null) {
				session.createQuery(
						"delete Friendship where PersonID = :id OR FriendID = :id")
						.setParameter("id", id).executeUpdate();
				session.delete(person);
				session.flush();
				session.getTransaction().commit();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return person;
	}

	
	
	//5> Get friends list from database
	@Override
	public List<Person> getFriends(Long id) {
		// TODO Auto-generated method stub

		System.out.println("IN GetFriends");
		Session session = sessionFactory.getCurrentSession();
		List<Person> friends = new ArrayList<Person>();

		try {

			session.beginTransaction();
			List<Friendship> friendsList = session
					.createQuery("from Friendship where PersonID = :id")
					.setParameter("id", id).list();

			for (int i = 0; i < friendsList.size(); i++)
				friends.add((Person) session.get(Person.class,
						friendsList.get(i).getFriendId()));

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return friends;
	}

}