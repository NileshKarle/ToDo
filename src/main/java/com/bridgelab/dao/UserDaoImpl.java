package com.bridgelab.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//This method is called when the user data is to be added to the database or the user details are to be modified.
	@Override
	public void saveUserData(User user){
		
		Session session =this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		try{
		session.saveOrUpdate(user);
		transaction.commit();
		session.close();
		}catch(Exception e){
			transaction.rollback();
			session.close();
		}
	
	}

	// This method retuns user data if the email exist's in the database else it retuns null.
	@SuppressWarnings("deprecation")
	@Override
	public User emailValidation(String email) {		
		Session session =this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", email));
		User user=(User) criteria.uniqueResult();
		session.close();
		return user;	
	}

	//This method retuns the user object for the perticular user id.
	@SuppressWarnings("deprecation")
	@Override
	public User userValidated(int UserId) {
		Session session =this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("id", UserId));
		User user=(User) criteria.uniqueResult();
		session.close();
		return user;
			
	}

}
