package com.ed2nd.mywallet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WalletNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;
	private String description;
	
	@Positive(message = "The User ID should be numeric and positive")
	private Integer userId;
	
	public WalletNewDTO() {
	}

	public WalletNewDTO(Date date, String description, Integer userId) {
		super();
		this.date = date;
		this.description = description;
		this.userId = userId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
