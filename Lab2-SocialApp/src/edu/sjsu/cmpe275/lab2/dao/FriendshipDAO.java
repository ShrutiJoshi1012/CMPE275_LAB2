package edu.sjsu.cmpe275.lab2.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.sjsu.cmpe275.lab2.entities.Friendship;
import edu.sjsu.cmpe275.lab2.entities.Organization;
import edu.sjsu.cmpe275.lab2.entities.Person;

public class FriendshipDAO {
	private SessionFactory sessionFactory;

	public FriendshipDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String add(Long id1, Long id2) {
		String responseMessage = "";
		Session session = sessionFactory.getCurrentSession();
		try {

			session.beginTransaction();
			Person PersonA = (Person) session.get(Person.class, id1);
			Person PersonB = (Person) session.get(Person.class, id2);

			if (PersonA == null || PersonB == null){
				return "InvalidReq";
			}

			// If they are not friends
			boolean addFriendship = session
					.createQuery(
							"from Friendship where PersonId = :id1 AND FriendId= :id2")
					.setParameter("id1", id1).setParameter("id2", id2).list()
					.isEmpty();

			if (addFriendship) {
				session.save(new Friendship(id1, id2));
				session.save(new Friendship(id2, id1));
				responseMessage = "Congratulations.. Friendship is added!";
			} else
				responseMessage = "They are already friends!!";

			session.getTransaction().commit();

		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return "Exception Occured.. Please try again!";

		}

		return responseMessage;

	}

	public String delete(Long id1, Long id2) {
		String responseMessage = "";
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Person PersonA = (Person) session.get(Person.class, id1);
			Person PersonB = (Person) session.get(Person.class, id2);

			if (PersonA == null || PersonB == null){
				responseMessage = "Either of the persons do not exist";
				session.getTransaction().commit();
				return responseMessage;
			}

			Friendship friendA = (Friendship) session.get(Friendship.class,
					new Friendship(id1, id2));
			Friendship friendB = (Friendship) session.get(Friendship.class,
					new Friendship(id2, id1));

			if (friendA == null || friendB == null){
				responseMessage = "These two persons are not friends";
				session.getTransaction().commit();
				return responseMessage;
			}
			else {
				session.delete(friendA);
				session.delete(friendB);
				session.flush();
				responseMessage = "DELETED";
			}

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return "Exception Occured.. Please try again!";

		}

		return responseMessage;

	}

}
