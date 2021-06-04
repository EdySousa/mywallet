package com.ed2nd.mywallet.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ed2nd.mywallet.domain.User;
import com.ed2nd.mywallet.services.validation.UserUpdate;

@UserUpdate
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Mandatory Field")
	@Length(min = 1, max = 120, message = "The size should be between 1 and 120 characters")
	private String firstName;

	@NotEmpty(message = "Mandatory Field")
	@Length(min = 1, max = 120, message = "The size should be between 1 and 120 characters")
	private String lastName;

	@NotEmpty(message = "Mandatory Field")
	@Email(message = "Invalid email")
	private String email;

	public UserDTO() {

	}

	public UserDTO(User obj) {
		this.id = obj.getId();
		this.firstName = obj.getFirstName();
		this.lastName = obj.getLastName();
		this.email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
