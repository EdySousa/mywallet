package com.ed2nd.mywallet.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Budget> budgets = new ArrayList<>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Transaction> transactions = new ArrayList<>();

	public Account() {
	}

	public Account(Integer id, String name, Date date, Wallet wallet) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.wallet = wallet;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public List<Budget> getBudgets() {
		return budgets;
	}

	public void addBudgets(Budget budget) {
		this.budgets.add(budget);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void addTransactions(Transaction transaction) {
		this.transactions.add(transaction);
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
