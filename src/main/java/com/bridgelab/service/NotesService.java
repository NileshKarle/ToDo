package com.bridgelab.service;

import java.util.List;
import java.util.Set;

import com.bridgelab.model.Collaborator;
import com.bridgelab.model.Label;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

public interface NotesService {

	public void addUserNotes(Notes notes);
	
	public void deleteNote(Notes note);
	
	public void updateNote(Notes note);
	
	public Notes getNote(Notes note);
	
	public List<User> getListOfUser(int noteId);
	
	public Set<Notes> getCollboratedNotes(int userId);
	
	public int saveCollborator(Collaborator collaborate);
	
	public int removeCollborator(int shareWith,int noteId);
	
	public Set<Notes> listAllNotes(User userId);
	
	public void addLabel(Label label);
	
	public void updateLabel(Label label);
	
}
