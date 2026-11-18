package com.kaonykk.financetutorapi.dto;

import com.kaonykk.financetutorapi.model.entity.User;

public class UserResponseDTO {
    private final Long id;
    private final String name;
    private final String email;
    private final String role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}