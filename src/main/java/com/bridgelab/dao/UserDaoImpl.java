package com.bridgelab.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
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

	@Override
	public void saveUser(User user){
		Session session =this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		session.persist(user);
		transaction.commit();
		}catch(Exception e){
			transaction.rollback();
		}
		session.close();
	}
	
	@SuppressWarnings({ "deprecation" })
	@Override
	public User loginUser(String email,String password){
		Session session =this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		Criterion emailVerify = Restrictions.eq("email", email);
		Criterion passwordVerify = Restrictions.eq("password", password);
		LogicalExpression andExp = Restrictions.and(emailVerify, passwordVerify);
		criteria.add(andExp);
		User user = (User) criteria.uniqueResult();
		session.close();
		return user;
	}

	@Override
	public User emailValidation(String email) {
		Session session =this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("email", email));
		User user=(User) criteria.uniqueResult();
		return user;
	}
}
