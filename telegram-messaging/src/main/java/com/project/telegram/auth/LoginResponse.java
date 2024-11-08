package com.project.telegram.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String phone;
    private String fullName;
    private String token;
}
