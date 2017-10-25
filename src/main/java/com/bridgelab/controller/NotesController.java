package com.bridgelab.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.model.ErrorMessage;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;
import com.bridgelab.service.NotesService;

@RestController
public class NotesController {

	@Autowired
	NotesService notesService;
	@Autowired
	ErrorMessage errorMessage;

	@RequestMapping(value = "/AddNotes", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> addNotes(@RequestBody Notes notes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Date date = new Date();
		notes.setCreatedDate(date);
		notes.setModifiedDate(date);
		notes.setUser(currentUser);
		notesService.addUserNotes(notes);
		errorMessage.setResponseMessage("Successfully added the node.");
		return ResponseEntity.ok(errorMessage);
	}

	@RequestMapping(value = "/DeleteNotes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ErrorMessage> deleteNotes(@PathVariable("id") int id) {
		Notes note=new Notes();
		note.setId(id);
		notesService.deleteNote(note);
		errorMessage.setResponseMessage("Successfully deleted the note.");
		return ResponseEntity.ok(errorMessage);
	}
	
	@RequestMapping(value = "/Update/{id}", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> updateNode(@PathVariable("id") int id,@RequestBody Notes note) {
		Date date=new Date();
		note.setId(id);
		note.setModifiedDate(date);
		notesService.updateNote(note);
		errorMessage.setResponseMessage("note updated.");
		return ResponseEntity.ok(errorMessage);
	}
	
	@RequestMapping(value = "/AllNodes", method = RequestMethod.GET)
    public ResponseEntity<ErrorMessage> listAllUsers( HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
        List<Notes> allNotes = notesService.listAllNotes(currentUser);
        if(allNotes.isEmpty()){
        	errorMessage.setResponseMessage("no notes present.");
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        errorMessage.setAllNotes(allNotes);
        return ResponseEntity.ok(errorMessage);
    }
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception" + e;
	}
}
