package com.ed2nd.mywallet.dto;

import java.io.Serializable;

import com.ed2nd.mywallet.domain.Menu;

public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

	public MenuDTO() {

	}

	public MenuDTO(Menu obj) {
		this.id = obj.getId();
		this.name = obj.getName();
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

}
