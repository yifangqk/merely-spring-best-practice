package com.project.telegram.admin;


import com.project.telegram.auth.AuthenticationService;
import com.project.telegram.dto.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminUserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/change-password")
    public ResponseEntity<GeneralResponse> changePassword(@RequestBody @Valid AdminChangePasswordRequest request) {
        authenticationService.adminChangePassword(request);
        return ResponseEntity.ok(GeneralResponse.success("Change password successfully!"));
    }

}
