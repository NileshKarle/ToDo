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
import com.bridgelab.service.UserService;
import com.bridgelab.token.VerifyToken;

@RestController
public class NotesController {
	@Autowired
	NotesService notesService;
	
	@Autowired
	ErrorMessage errorMessage;

	@Autowired
	VerifyToken verifyToken; 
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = "/AddNotes", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> addNotes(@RequestBody Notes notes, HttpSession session) {
		System.out.println("yes it has arrived in  add notes");
		String token=(String) session.getAttribute("AccessToken");
		int userId=verifyToken.parseJWT(token);
		User user=userService.userValidated(userId);
		System.out.println("user in add notes "+user);
		
		if(user==null){
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

	@RequestMapping(value = "/DeleteNotes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ErrorMessage> deleteNotes(@PathVariable("id") int id,HttpSession session) {
		
		String token=(String) session.getAttribute("AccessToken");
		int userId=verifyToken.parseJWT(token);
		User user=userService.userValidated(userId);
		System.out.println("user in add notes "+user);
		
		if(user==null){
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

	@RequestMapping(value = "/Update", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> updateNote( @RequestBody Notes note, HttpSession session) {
		
		String token=(String) session.getAttribute("AccessToken");
		int userId=verifyToken.parseJWT(token);
		User user=userService.userValidated(userId);
		System.out.println("user in add notes "+user);
		
		if(user==null){
			errorMessage.setResponseMessage("invalid ...!!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		
		Notes oldNote = notesService.getNote(note);
		if(oldNote!=null){
			
			Date date = new Date();
			
			note.setModifiedDate(date);
			note.setCreatedDate(oldNote.getCreatedDate());
			
			note.setUser(user);
			
			notesService.updateNote(note);
			
			errorMessage.setResponseMessage("note updated.");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage);
		}
		
		errorMessage.setResponseMessage("no note found.");
		errorMessage.setAllNotes(null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/AllNodes", method = RequestMethod.GET)
	public ResponseEntity<List> listAllUsers(HttpSession session) {
		
		/*boolean tokenvarification=false;
		try{
			
			VerifyToken verifyToken=new VerifyToken();
			String compactToken=(String) session.getAttribute("token");
			 tokenvarification=verifyToken.parseJWT(compactToken);*/
		
		String token=(String) session.getAttribute("AccessToken");
		int userId=verifyToken.parseJWT(token);
		User user=userService.userValidated(userId);
		System.out.println("user in add notes "+user);
		
		if(user==null){
			errorMessage.setAllNotes(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.getAllNotes());
		}
		
		//User currentUser = (User) session.getAttribute("currentUser");
		List<Notes> allNotes = notesService.listAllNotes(user);
		
		if (allNotes.isEmpty()) {
			
			errorMessage.setResponseMessage("no note found.");
			errorMessage.setAllNotes(allNotes);
			return ResponseEntity.ok(errorMessage.getAllNotes());
		} else {
			
			errorMessage.setResponseMessage("note found.");
			errorMessage.setAllNotes(allNotes);
			return ResponseEntity.ok(errorMessage.getAllNotes());
		}
		/*}catch (Exception e){
			errorMessage.setResponseMessage("something went wrong ");
			errorMessage.setAllNotes(null);
			return ResponseEntity.ok(errorMessage.getAllNotes());
		}finally{
			System.out.println("------>"+tokenvarification+"<-------");
		}*/
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) {
		return "Exception" + e;
	}
}
