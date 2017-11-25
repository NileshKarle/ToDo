package com.bridgelab.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TODO_USER")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usergen")
	@GenericGenerator(name = "usergen", strategy = "native")
	private int id;

	@Column(name = "FIRST_TIME_LOGIN")
	private String firstTimeLogin;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(unique = true, name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CONTACT")
	private String contact;
	
	@Column(name = "PROFILE")
	private String profile;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Notes> notes = new HashSet<Notes>();

	public String getFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(String firstTimeLogin) {
		if (firstTimeLogin == "true" || firstTimeLogin == "false") {
			this.firstTimeLogin = firstTimeLogin;
		}
	}

	public Set<Notes> getNotes() {
		return notes;
	}

	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		String Id = "[0-9]{10}";

		if (contact.matches(Id)) {
			this.contact = contact;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		String nameFormat = "^[a-zA-Z\\s]+$";

		if (firstName.matches(nameFormat)) {
			this.firstName = firstName;
		}

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		String nameFormat = "^[a-zA-Z]+$";

		if (lastName.matches(nameFormat)) {
			this.lastName = lastName;
		}

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String emailFormat = "[a-zA-Z0-9\\.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$";

		if (email.matches(emailFormat)) {
			this.email = email;
		}

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != "") {
			this.password = password;
		}

	}

}
