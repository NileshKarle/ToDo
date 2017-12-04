package com.bridgelab.model;

import java.util.Set;

public class ErrorMessage {
	
	private String responseMessage;

	private Set<Notes> allNotes;

	public Set<Notes> getAllNotes() {
		return allNotes;
	}
	
	public void setAllNotes(Set<Notes> allNotes) {
		this.allNotes = allNotes;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
}
