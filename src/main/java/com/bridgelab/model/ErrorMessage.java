package com.bridgelab.model;

import java.util.List;

public class ErrorMessage {
	
	private String responseMessage;

	private List<Notes> allNotes;

	public List<Notes> getAllNotes() {
		return allNotes;
	}
	public void setAllNotes(List<Notes> allNotes) {
		this.allNotes = allNotes;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	

}
