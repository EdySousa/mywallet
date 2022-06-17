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

import com.ed2nd.mywallet.domain.enums.TransactionType;
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
	
	private Double value;

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

	public Account(Integer id, String name, Date date, Double value, Wallet wallet) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.value = value;
		this.wallet = wallet;
	}

	public Double getIncome() {
		return transactions.stream().filter(x -> x.getType().equals(TransactionType.INCOME))
				.mapToDouble(a -> a.getValue()).sum();
	}

	public Double getExpense() {
		return transactions.stream().filter(x -> x.getType().equals(TransactionType.EXPENSE))
				.mapToDouble(a -> a.getValue()).sum();
	}

	public Double getBalance() {
		return getIncome() - getExpense();
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
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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

	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void seTransactions(List<Transaction> transactions) {
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
