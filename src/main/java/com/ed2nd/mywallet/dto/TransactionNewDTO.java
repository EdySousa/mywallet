package com.ed2nd.mywallet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.ed2nd.mywallet.services.validation.TransactionInsert;
import com.fasterxml.jackson.annotation.JsonFormat;

@TransactionInsert
public class TransactionNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private TransactionType type;
	
	@NotEmpty(message = "Mandatory Field")
	@Length(min = 2, max = 120, message = "The size should be between 5 and 120 characters")
	private String name;
	
	@Positive(message = "The Value should be numeric positive")
	private Double value;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;

	@Positive(message = "The Account ID should be numeric positive")
	private Integer accountId;

	private Integer budgetId;

	public TransactionNewDTO() {
	}

	public TransactionNewDTO(TransactionType type, String name, Double value, Date date, Integer accountId,	Integer budgetId) {
		this.type = type;
		this.name = name;
		this.value = value;
		this.date = date;
		this.accountId = accountId;
		this.budgetId = budgetId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

	public Integer getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}
}
