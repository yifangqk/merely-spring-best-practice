package com.project.telegram.admin;

import com.project.telegram.common.PhoneAware;
import com.project.telegram.validation.Phone;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminChangePasswordRequest implements PhoneAware {

    @Phone
    private String phone;

    @NotBlank(message = "New password cannot be blank")
    private String newPassword;

}
