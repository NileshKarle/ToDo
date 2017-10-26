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
		}catch(Exception e){
			transaction.rollback();
		}
		session.close();
	}

	@Override
	public void deleteNote(Notes notes) {
		Session session =this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		session.delete(notes);
		transaction.commit();
		}catch (Exception e){
			transaction.rollback();
		}
		session.close();
	}

	@Override
	public void updateNote(Notes notes) {
		Session session=this.sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
		Notes note=(Notes)session.get(Notes.class, notes.getId());
		if(notes.getTitle()!=null){
		note.setTitle(notes.getTitle());
		}
		if(notes.getDescription()!=null){
		note.setDescription(notes.getDescription());
		}
		note.setModifiedDate(notes.getModifiedDate());
		transaction.commit();
		session.update(note);
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
		return results;
	}
}
