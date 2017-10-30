package com.bridgelab.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

public class NotesDaoImpl implements NotesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addUserNotes(Notes notes){
		Session session =this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		try{
		session.persist(notes);
		transaction.commit();
		session.close();
		}catch(Exception e){
			transaction.rollback();
			session.close();
		}
		
	}

	@Override
	public void deleteNote(Notes notes) {
		Session session =this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		try{
		session.delete(notes);
		transaction.commit();
		session.close();
		}catch (Exception e){
			transaction.rollback();
			session.close();
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public Notes getNote(Notes note) {
		
		Session session=this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Notes.class).add(Restrictions.eq("id", note.getId()));
		Notes notes=(Notes) criteria.uniqueResult();
		session.close();
		return notes;
	
	}
	
	@Override
	public void updateNote(Notes notes) {
		
		Session session=this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		try{
			System.out.println(notes);
		session.saveOrUpdate(notes);
		transaction.commit();
		session.close();
		}catch(Exception e){
			transaction.rollback();
			session.close();
		}		
	
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Notes> listAllNotes(User userId) {
		
		List<Notes> results = null;
		Session session=this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Notes.class);
		criteria.add(Restrictions.eq("user",userId));
		results = criteria.list();
		session.close();
		return results;
	}

}
