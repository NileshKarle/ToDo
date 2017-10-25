package com.bridgelab.dao;

import java.util.List;

import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

public interface NotesDao {

	public void addUserNotes(Notes notes);
	
	public void deleteNote(Notes notes);
	
	public void updateNote(Notes notes);
	
	public List<Notes> listAllNotes(User userId);
	
}
