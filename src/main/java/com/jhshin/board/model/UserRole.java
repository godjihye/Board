package com.jhshin.board.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
    UserRole(String value) {
        this.value = value;
    }
}
