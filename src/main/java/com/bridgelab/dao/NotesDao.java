package com.bridgelab.dao;

import java.util.List;
import java.util.Set;

import com.bridgelab.model.Collaborator;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;

/**
 * @author Nilesh
 *
 * @Description All notes related database operation are done in NoteDao.
 * 
 */
public interface NotesDao {

	public void addUserNotes(Notes notes);
	
	public void deleteNote(Notes notes);
	
	public void updateNote(Notes notes);
	
	public Notes getNote(Notes note);
	
	public List<User> getListOfUser(int noteId);
	
	public Set<Notes> getCollboratedNotes(int userId);
	
	public int saveCollborator(Collaborator collaborate);
	
	public int removeCollborator(int shareWith,int noteId);
	
	public Set<Notes> listAllNotes(User userId);
	
}
