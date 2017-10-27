package com.bridgelab.service;

import java.util.List;

import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

public interface NotesService {

	public void addUserNotes(Notes notes);
	
	public void deleteNote(Notes note);
	
	public void updateNote(Notes note);
	
	public Notes getNote(Notes note);
	
	public List<Notes> listAllNotes(User userId);
}
