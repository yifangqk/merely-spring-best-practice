package com.project.telegram.auth;

import com.project.telegram.common.PhoneAware;
import com.project.telegram.validation.FullName;
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
public class RegisterRequest implements PhoneAware {

    @Phone
    private String phone;

    @FullName
    private String fullName;

    @Password
    private String password;

}
