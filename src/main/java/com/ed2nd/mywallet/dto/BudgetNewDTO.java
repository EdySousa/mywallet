package com.ed2nd.mywallet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.ed2nd.mywallet.services.validation.BudgetInsert;
import com.fasterxml.jackson.annotation.JsonFormat;

@BudgetInsert
public class BudgetNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Mandatory Field")
	@Length(min = 5, max = 120, message = "The size should be between 5 and 120 characters")
	private String name;
	
	@Positive(message = "The Budget value should be numeric positive")
	private Double budget;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;

	@Positive(message = "The Account ID should be numeric positive")
	private Integer accountId;
	
	
	public BudgetNewDTO() {
	}


	public BudgetNewDTO(String name, Double budget, Date date, Integer accountId) {
		super();
		this.name = name;
		this.budget = budget;
		this.date = date;
		this.accountId = accountId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getBudget() {
		return budget;
	}


	public void setBudget(Double budget) {
		this.budget = budget;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Integer getAccountId() {
		return accountId;
	}


	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	
	
	

}
