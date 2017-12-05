package com.bridgelab.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.dao.NotesDao;
import com.bridgelab.model.Collaborator;
import com.bridgelab.model.Label;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

public class NotesServiceImpl implements NotesService{

	@Autowired
	NotesDao notesDao;
	
	@Override
	public void addUserNotes(Notes notes) {
		notesDao.addUserNotes(notes);
	}

	@Override
	public void deleteNote(Notes note) {
		notesDao.deleteNote(note);
	}

	@Override
	public void updateNote(Notes note) {
		notesDao.updateNote(note);
	}

	@Override
	public Set<Notes> listAllNotes(User userId) {
		Set<Notes> allNotes=notesDao.listAllNotes(userId);
		return allNotes;
	}

	@Override
	public Notes getNote(Notes note) {
		Notes notes=notesDao.getNote(note);
		return notes;
	}

	@Override
	public List<User> getListOfUser(int noteId) {
		
		return notesDao.getListOfUser(noteId);
	}

	@Override
	public Set<Notes> getCollboratedNotes(int userId) {
		
		return notesDao.getCollboratedNotes(userId);
	}

	@Override
	public int saveCollborator(Collaborator collaborate) {
		
		return notesDao.saveCollborator(collaborate);
	}

	@Override
	public int removeCollborator(int shareWith, int noteId) {
		
		return notesDao.removeCollborator(shareWith,noteId);
	}

	@Override
	public void addLabel(Label label) {
		notesDao.addLabel(label);
	}
	
	@Override
	public void removeLabel(Label label){
		notesDao.removeLabel(label);
	}
	
	@Override
	public void updateLabel(Label label) {
		notesDao.updateLabel(label);
	}

}
