package com.ed2nd.mywallet.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ed2nd.mywallet.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private TransactionType type;
	private String name;
	private Double value;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date date;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "budget_id")
	private Budget budget;

	public Transaction() {
	}

	public Transaction(Integer id, TransactionType type, String name, Double value, Date date, Account account, Budget budget) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.value = value;
		this.date = date;
		this.account = account;
		this.budget = budget;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
