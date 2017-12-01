package com.bridgelab.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "LABELS")
public class Label {

	@Id
	@GenericGenerator(name = "abc", strategy = "increment")
	@GeneratedValue(generator = "abc")
	private int id;
	
	@ManyToOne
	private User userLabel;
	
	@ManyToMany
	private Set<Notes> noteId;
	
	private String labelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabelName() {
		return labelName;
	}

	public User getUser() {
		return userLabel;
	}

	public void setUserId(User userLabel) {
		this.userLabel = userLabel;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public User getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(User userLabel) {
		this.userLabel = userLabel;
	}

	public Set<Notes> getNoteId() {
		return noteId;
	}

	public void setNoteId(Set<Notes> noteId) {
		this.noteId = noteId;
	}
	
}
