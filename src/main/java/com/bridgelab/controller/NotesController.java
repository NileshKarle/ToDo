package com.bridgelab.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.model.ErrorMessage;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;
import com.bridgelab.service.NotesService;
import com.bridgelab.service.UserService;
import com.bridgelab.token.VerifyToken;

@RestController
public class NotesController {
	@Autowired
	NotesService notesService;

	@Autowired
	VerifyToken verifyToken;

	@Autowired
	UserService userService;

	/**
	 * @param notes
	 * @param headers
	 * @return ResponseEntity
	 * 
	 * @Description This method Add's a new Note. For the respective user who is
	 *              logged in.
	 * 
	 */
	@RequestMapping(value = "/AddNotes", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> addNotes(@RequestBody Notes notes,
			@RequestHeader(value = "token") String headers) {
		
		// Collect the token(headers) from the local storage and verify the
		// token.
		int userId = verifyToken.parseJWT(headers);
		User user = userService.userValidated(userId);

		// ErrorMessage object creation to display customise message.
		ErrorMessage errorMessage = new ErrorMessage();

		if (user == null || user.getLoginStatus().equals("false")) {
			errorMessage.setResponseMessage("invalid ...!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		Date date = new Date();
		notes.setCreatedDate(date);
		notes.setModifiedDate(date);
		notes.setUser(user);
		notesService.addUserNotes(notes);

		errorMessage.setAllNotes(null);
		errorMessage.setResponseMessage("Successfully added the node.");

		return ResponseEntity.ok(errorMessage);

	}

	/**
	 * @param id
	 *            (note id)
	 * @param headers
	 *            (JWT)
	 * @return ResponseEntity (HTTP status)
	 * 
	 * @Description This method delete's an existing Note. For the respective
	 *              user who is logged in. when ever the /DeleteNotes/{id} API
	 *              is called.
	 */
	@RequestMapping(value = "/DeleteNotes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ErrorMessage> deleteNotes(@PathVariable("id") int id,
			@RequestHeader(value = "token") String headers) {
		// Collect the token(headers) from the local storage and verify the
		// token.
		int userId = verifyToken.parseJWT(headers);
		User user = userService.userValidated(userId);
		ErrorMessage errorMessage = new ErrorMessage();

		if (user == null || user.getLoginStatus().equals("false")) {
			errorMessage.setResponseMessage("invalid ...!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		Notes note = new Notes();
		note.setId(id);
		notesService.deleteNote(note);
		errorMessage.setResponseMessage("Successfully deleted the note.");
		errorMessage.setAllNotes(null);

		return ResponseEntity.ok(errorMessage);
	}

	
	@RequestMapping(value = "/changeColor", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> updateColor(@RequestBody Notes note,
			@RequestHeader(value = "token") String headers) {
		
		// Collect the token(headers) from the local storage and verify the
		// token.
		int userId = verifyToken.parseJWT(headers);
		
		User user = userService.userValidated(userId);
		ErrorMessage errorMessage = new ErrorMessage();

		if (user == null || user.getLoginStatus().equals("false")) {
			errorMessage.setResponseMessage("invalid ...!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		note.setUser(user);
		notesService.updateNote(note);

			errorMessage.setResponseMessage("note updated.");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage);
		}	
	
	
	/**
	 * @param notes
	 * @param headers
	 *            (JWT)
	 * @return ResponseEntity (HTTP Status)
	 * 
	 * @Description This method Update's an existing Note. For the respective
	 *              user who is logged in.
	 * 
	 */
	@RequestMapping(value = "/noteUpdate", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> updateNote(@RequestBody Notes note,
			@RequestHeader(value = "token") String headers) {
		
		System.out.println("its entered in the update field");
		
		// Collect the token(headers) from the local storage and verify the
		// token.
		int userId = verifyToken.parseJWT(headers);
		System.out.println("user id is "+userId);
		User user = userService.userValidated(userId);
		ErrorMessage errorMessage = new ErrorMessage();

		if (user == null || user.getLoginStatus().equals("false")) {
			errorMessage.setResponseMessage("invalid ...!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		Notes oldNote = notesService.getNote(note);
		System.out.println(oldNote);
		if (oldNote != null) {
			Date date = new Date();
			note.setModifiedDate(date);
			note.setCreatedDate(oldNote.getCreatedDate());
			note.setUser(user);
			notesService.updateNote(note);

			errorMessage.setResponseMessage("note updated.");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage);
		}

		// If the node dose not exist's in the database.
		errorMessage.setResponseMessage("The note you are trying to update dose not exist's.");
		errorMessage.setAllNotes(null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	/**
	 * @param headers
	 *            (JWT)
	 * @return ResponseEntity<List> (List of notes)
	 * 
	 * @Description This method display's all notes for the user who has
	 *              requested. only the notes created by that respective user.
	 *              The notes which the user has the access.
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/AllNodes", method = RequestMethod.GET)
	public ResponseEntity<List> listAllUsers(@RequestHeader(value = "token") String headers) {

		// Collect the token(headers) from the local storage and verify the
		// token.
		int userId = verifyToken.parseJWT(headers);

		User user = userService.userValidated(userId);
		ErrorMessage errorMessage = new ErrorMessage();

		if (user == null || user.getLoginStatus().equals("false")) {
			errorMessage.setAllNotes(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.getAllNotes());
		}

		List<Notes> allNotes = notesService.listAllNotes(user);

		/*
		 * if (allNotes.isEmpty()) {
		 * errorMessage.setResponseMessage("no note found.");
		 * errorMessage.setAllNotes(allNotes); return
		 * ResponseEntity.ok(errorMessage.getAllNotes()); }
		 */

		errorMessage.setResponseMessage("note found.");
		errorMessage.setAllNotes(allNotes);

		return ResponseEntity.ok(errorMessage.getAllNotes());
	}

	/*
	 * This method catche's any exception that exist's.
	 *
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception" + e;
	}

}
