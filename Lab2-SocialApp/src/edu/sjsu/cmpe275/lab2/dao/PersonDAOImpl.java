package edu.sjsu.cmpe275.lab2.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import edu.sjsu.cmpe275.lab2.entities.Organization;
import edu.sjsu.cmpe275.lab2.entities.Person;

@Transactional
public class PersonDAOImpl implements PersonDAO {

	private SessionFactory sessionFactory;

	public PersonDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

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
						Organization.class, person.getOrganization()
								.getOrganizationId());
				if (organizationNew == null)
					// If Organization does not exist in database return NULL
					return null;
				else
					// else set the organization properties to the Person
					person.setOrganization(organizationNew);
			}

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
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.getTransaction().commit();
		return person;
	}

	@Override
	public Person getPerson(Long id) {
		System.out.println("IN GetPerson");
		Session session = sessionFactory.getCurrentSession();
		Person person = null;
		try {
			
			session.beginTransaction();
			person = (Person) session.get(Person.class, id);
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.getTransaction().commit();
		return person;
	}

	@Override
	public Person deletePerson(Long id) {
		System.out.println("IN DeletePerson");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Person person = (Person) session.get(Person.class, id);
		if (person != null) {		
			session.createQuery("delete Friendship where PersonID = :id OR FriendID = :id") 
		    .setParameter("id", id)
		    .executeUpdate();
			session.delete(person);
			session.flush();
		}
		session.getTransaction().commit();
		return person;
	}

	@Override
	public long[] getFriends(Long id) {
		// TODO Auto-generated method stub
		
		System.out.println("IN GetFriends");
		Session session = sessionFactory.getCurrentSession();
		List<Person> friends = new ArrayList<Person>();
		session.beginTransaction();
		long[] friendIDs =null;;
		Person person=null;
		try {
			
			person = (Person) session.get(Person.class, id);
			friends.addAll(person.getFriends());
			friendIDs=new long[friends.size()];
			int i=0;
			for(Person friend:friends){
				friendIDs[i]=friend.getPersonId();
				i++;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.getTransaction().commit();
		return friendIDs;
	}

}