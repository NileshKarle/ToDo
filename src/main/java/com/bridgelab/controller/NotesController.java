package com.bridgelab.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.model.Collaborator;
import com.bridgelab.model.ErrorMessage;
import com.bridgelab.model.Label;
import com.bridgelab.model.Notes;
import com.bridgelab.model.User;
import com.bridgelab.service.NotesService;
import com.bridgelab.service.UserService;
import com.bridgelab.token.VerifyToken;

/**
 * @author Nilesh
 *
 * @Description This controller is called when any operation related to notes has to be performed.
 *
 */
@RestController
@RequestMapping(value="/note")
public class NotesController {
	@Autowired
	NotesService notesService;

	@Autowired
	VerifyToken verifyToken;

	@Autowired
	UserService userService;

	/**
	 * @param notes(note to be added)
	 * @param user(current user who has logined)
	 * @return ResponseEntity
	 * 
	 * @Description This method Add's a new Note. For the respective user who is
	 *              logged in.
	 * 
	 */
	@RequestMapping(value = "/AddNotes", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> addNotes(@RequestBody Notes notes,
			@RequestAttribute("loginedUser") User user) {
		

		// Collect the token(headers) from the local storage and verify the
		// token.

		// ErrorMessage object creation to display customise message.
		ErrorMessage errorMessage = new ErrorMessage();
			
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
	 * @param id (note id)
	 * @param user (current user who is logined)
	 * @return ResponseEntity (HTTP status)
	 * 
	 * @Description This method delete's an existing Note. For the respective
	 *              user who is logged in. when ever the /DeleteNotes/{id} API
	 *              is called.
	 */
	@RequestMapping(value = "/DeleteNotes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ErrorMessage> deleteNotes(@PathVariable("id") int id,
			@RequestAttribute("loginedUser") User user) {
		
		ErrorMessage errorMessage = new ErrorMessage();
		
		Notes note = new Notes();
		note.setId(id);
		notesService.deleteNote(note);
		errorMessage.setResponseMessage("Successfully deleted the note.");
		errorMessage.setAllNotes(null);

		return ResponseEntity.ok(errorMessage);
	}

	
	/**
	 * @param note(note who's color has to be changes)
	 * @param user(user who is login)
	 * @return OK Status
	 */
	@RequestMapping(value = "/changeColor", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> updateColor(@RequestBody Notes note,
			@RequestAttribute("loginedUser") User user) {

		ErrorMessage errorMessage = new ErrorMessage();
		
		Notes oldNote = notesService.getNote(note);
		
		if (oldNote != null) {
			
			note.setUser(oldNote.getUser());
			notesService.updateNote(note);

			errorMessage.setResponseMessage("note updated.");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage);
		}
		errorMessage.setResponseMessage("The note you are trying to update dose not exist's.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
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
			@RequestAttribute("loginedUser") User user) {

		ErrorMessage errorMessage = new ErrorMessage();

		Notes oldNote = notesService.getNote(note);
		
		if (oldNote != null) {
			Date date = new Date();
			note.setModifiedDate(date);
			note.setCreatedDate(oldNote.getCreatedDate());
			note.setUser(oldNote.getUser());
			notesService.updateNote(note);

			errorMessage.setResponseMessage("note updated.");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage);
		}

		// If the node dose not exist's in the database.
		errorMessage.setResponseMessage("The note you are trying to update dose not exist's.");
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
	public ResponseEntity<Set> listAllUsers(@RequestAttribute("loginedUser") User user) {

		ErrorMessage errorMessage = new ErrorMessage();

		Set<Notes> collaboratedNote = notesService.getCollboratedNotes(user.getId());
		Set<Notes> allNotes = notesService.listAllNotes(user);
		allNotes.addAll(collaboratedNote);
		
		errorMessage.setResponseMessage("note found.");
		errorMessage.setAllNotes(allNotes);

		return ResponseEntity.ok(errorMessage.getAllNotes());
	}

	
	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<User>> getNotes(@RequestBody Collaborator collborator, HttpServletRequest request,@RequestAttribute("loginedUser") User ownerUser){
		List<User> users=new ArrayList<User>();
		System.out.println("inside the collaborate"+collborator);
		Collaborator collaborate =new Collaborator();
		Notes note= (Notes) collborator.getNote();
		User shareWith = (User) collborator.getShareWithId();
		shareWith=userService.emailValidation(shareWith.getEmail());
		User owner= (User) collborator.getOwnerId();
		users=	notesService.getListOfUser(note.getId());
//		User user=userService.getUserById(tokenService.verifyToken(token));
		
				if(shareWith!=null && shareWith.getId()!=owner.getId()) {
					int i=0;
					int flag=0;
					while(users.size()>i) {
						if(users.get(i).getId()==shareWith.getId()) {
							flag=1;
						}
						i++;
					}
					if(flag==0) {
						collaborate.setNote(note);
						collaborate.setOwnerId(owner);
						collaborate.setShareWithId(shareWith);
						if(notesService.saveCollborator(collaborate)>0) {
						  	users.add(shareWith);
						}else {
							 ResponseEntity.ok(users);
						}
					}
		}
		
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Notes note, HttpServletRequest request,@RequestAttribute("loginedUser") User user){
		
		System.out.println("inside the get owner");
		Notes noteComplete=notesService.getNote(note);
		User owner=noteComplete.getUser();
		return ResponseEntity.ok(owner);
		
	}
	
	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> removeCollborator(@RequestBody Collaborator collborator, HttpServletRequest request){
		ErrorMessage response=new ErrorMessage();
		int shareWith=collborator.getShareWithId().getId();
		Notes note=notesService.getNote(collborator.getNote());
		User owner=note.getUser();
		
				if(owner.getId()!=shareWith){
					if(notesService.removeCollborator(shareWith, note.getId())>0){
						response.setResponseMessage("Collborator removed");
						
						return ResponseEntity.ok(response);
					}else{
						response.setResponseMessage("database problem");
						return ResponseEntity.ok(response);
					}
				}else{
					response.setResponseMessage("Can't remove owner");
					return ResponseEntity.ok(response);
				}
		
	}
	
	
	@RequestMapping(value = "/AddLabel", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> Addlabel(@RequestAttribute("loginedUser") User user,@RequestBody Label label) {
		System.out.println(label);
		ErrorMessage errorMessage = new ErrorMessage();
		label.setUserId(user);
		label.setNoteId(null);
		notesService.addLabel(label);
		return ResponseEntity.ok(errorMessage);
	}

	
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		e.printStackTrace();
		System.out.println();
		return "Exception" + e;
	}

}
