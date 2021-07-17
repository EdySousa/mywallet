package com.ed2nd.mywallet.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.ed2nd.mywallet.services.validation.AccountInsert;
import com.fasterxml.jackson.annotation.JsonFormat;

@AccountInsert
public class AccountNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Mandatory Field")
	@Length(min = 1, max = 120, message = "The size should be between 1 and 120 characters")
	private String name;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;

	@NotNull(message = "The Wallet ID should not be null or whitespace")
	@Positive(message = "The Wallet ID should be numeric positive")
	private Integer walletId;

	public AccountNewDTO() {
	}

	public AccountNewDTO(String name, Date date, Integer walletId) {
		super();
		this.name = name;
		this.date = date;
		this.walletId = walletId;
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

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

}
