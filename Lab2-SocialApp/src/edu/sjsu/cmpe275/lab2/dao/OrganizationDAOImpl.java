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
	public boolean add(Organization organization) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(organization);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;

		}

		return true;

	}

	@Override
	public boolean update(Organization organization) {

		Session session = sessionFactory.getCurrentSession();
		try {
			System.out.println("IN Update Organization");
			session.beginTransaction();
			Organization existingOrganization = (Organization) session.get(
					Organization.class, organization.getOrganizationId());
			if (existingOrganization == null)
				return false;
			existingOrganization.setName(organization.getName());
			existingOrganization.setDescription(organization.getDescription());
			existingOrganization.setAddress(organization.getAddress());

			session.saveOrUpdate(existingOrganization);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			System.out
					.println("Exception occured, refer logs for more details");
			return false;
		}

		return true;
	}

	// Get organization details
	@Override
	public Organization getOrganization(Long id) {
		System.out.println("IN GetOrganization");
		Session session = sessionFactory.getCurrentSession();
		Organization organization = null;
		try {

			session.beginTransaction();
			organization = (Organization) session.get(Organization.class, id);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			System.out.println("Exception occured");
			return null;
		}

		return organization;
	}

	
	//Delete Organization
	@Override
	public Object delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Organization organization = (Organization) session.get(
					Organization.class, id);
			if (organization == null)
				return null;
			boolean hasNoPerson = session
					.createQuery("from Person where OrganizationID = :id")
					.setParameter("id", id).list().isEmpty();

			if (hasNoPerson) {
				session.delete(organization);
				session.flush();
				session.getTransaction().commit();
				return organization;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return false;

	}

}