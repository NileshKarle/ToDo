package com.bridgelab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelab.dao.NotesDao;
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
	public List<Notes> listAllNotes(User userId) {
		List<Notes> allNotes=notesDao.listAllNotes(userId);
		return allNotes;
	}

}
