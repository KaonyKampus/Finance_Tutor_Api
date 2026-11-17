package com.kaonykk.financetutorapi.dto;

import com.kaonykk.financetutorapi.model.entity.User;

public class LoginResponseDTO {

	private final Long id;
	private final String name;
	private final String role;

	public LoginResponseDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.role = user.getRole().name();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

}
