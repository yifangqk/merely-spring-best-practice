package com.project.telegram.auth;

import com.project.telegram.common.PhoneAware;
import com.project.telegram.validation.Password;
import com.project.telegram.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest implements PhoneAware {

    @Phone
    private String phone;

    @Password
    private String password;
}
