package com.project.telegram.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO extract to auth service
@AllArgsConstructor
@Getter
public class SimpleAccount {
    private String phone;
    private String role;
}
