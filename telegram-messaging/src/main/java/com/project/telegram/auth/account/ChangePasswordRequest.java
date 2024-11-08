package com.project.telegram.auth.account;

import com.project.telegram.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @Password
    private String oldPassword;

    @Password
    private String newPassword;

}
