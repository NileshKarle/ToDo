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
	
	//This method save the new note in the database.
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

	//This method delete's the note from the database.
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

	//Retun's the note by id.
	@SuppressWarnings("deprecation")
	@Override
	public Notes getNote(Notes note) {
		System.out.println(note);
		System.out.println(note.getId());
		Session session=this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Notes.class).add(Restrictions.eq("id", note.getId()));
		Notes notes=(Notes) criteria.uniqueResult();
		System.out.println(notes);
		System.out.println(notes.getDescription());
		
		session.close();
		return notes;
	
	}
	
	//This method is called when any field of the note is modified. 
	//So the changes can be reflected in the database.
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
	
	
	//This method retun's list of all the note's of a particular user based on the user id.
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Notes> listAllNotes(User user) {
		
		List<Notes> results = null;
		Session session=this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Notes.class);
		criteria.add(Restrictions.eq("user",user));
		results = criteria.list();
		session.close();
		return results;
	}

}
