package com.ed2nd.mywallet.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Budget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Double budget;
	private Double spent;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date date;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToMany(mappedBy = "budget")
	private List<Transaction> transactions = new ArrayList<>();

	public Budget() {
	}

	public Budget(Integer id, String name, Double budget, Double spent, Date date, Account account) {
		super();
		this.id = id;
		this.name = name;
		this.budget = budget;
		this.spent = spent;
		this.date = date;
		this.account = account;
	}
	
	public Double getTotalAvailable() {
		return budget - spent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getSpent() {
		return spent;
	}

	public void setSpent(Double spent) {
		this.spent = spent;
	}
	
	public void addSpent(Double spent) {
		this.spent += spent;
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
	
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
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
		Budget other = (Budget) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
