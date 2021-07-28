package com.ed2nd.mywallet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ed2nd.mywallet.services.validation.UserInsert;

@UserInsert
public class UserNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Mandatory Field")
	@Length(min = 1, max = 120, message = "The size should be between 1 and 120 characters")
	private String firstName;

	@NotEmpty(message = "Mandatory Field")
	@Length(min = 1, max = 120, message = "The size should be between 1 and 120 characters")
	private String lastName;

	@NotEmpty(message = "Mandatory Field")
	@Email(message = "Email should be valid")
	private String email;
	
	@NotEmpty(message = "Mandatory Field")
	private String password;

	private Date date;
	private String description;

	public UserNewDTO() {

	}

	public UserNewDTO(String firstName, String lastName, String email, String password, Date date, String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.setPassword(password);
		this.date = date;
		this.description = description;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
