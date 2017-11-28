package com.bridgelab.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.model.Collaborator;
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
	public Set<Notes> listAllNotes(User user) {
		
		List<Notes> results = null;
		Session session=this.sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Notes.class);
		criteria.add(Restrictions.eq("user",user));
		results = criteria.list();
		Set<Notes> allNotes=new HashSet<Notes>(results);
		session.close();
		return allNotes;
	}

	@Override
	public List<User> getListOfUser(int noteId) {
		Session session = this.sessionFactory.openSession();
		Query querycollab = session.createQuery("select c.shareWithId from Collaborator c where c.note= " + noteId);
		List<User> listOfSharedCollaborators =  querycollab.list();
		System.out.println("listOfSharedCollaborators "+listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;
	}

	@Override
	public Set<Notes> getCollboratedNotes(int userId) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select c.note from Collaborator c where c.shareWithId= " + userId);
		List<Notes> colllboratedNotes =  query.list();
		Set<Notes> notes=new HashSet<Notes>(colllboratedNotes);
		
		session.close();
		return notes;
	}

	@Override
	public int saveCollborator(Collaborator collaborate) {
		int collboratorId=0;
		Session session= this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		collboratorId=(Integer) session.save(collaborate);
		transaction.commit();
		}catch(HibernateException e){
			e.printStackTrace();
			transaction.rollback();
		}finally{
			session.close();
		}
		return collboratorId;
	}

}
