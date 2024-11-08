package com.project.telegram.auth;

import com.project.telegram.auth.account.ChangePasswordRequest;
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
@RequestMapping(path = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<GeneralResponse> registerAccount(@RequestBody @Valid RegisterRequest request) {
        authenticationService.registerAccount(request);

        return ResponseEntity.ok(GeneralResponse.success("Account registered successfully"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> authenticateAccount(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("change-password")
    public ResponseEntity<GeneralResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        authenticationService.changePassword(request);
        return ResponseEntity.ok(GeneralResponse.success("Password changed successfully"));
    }


    @PostMapping("/authen")
    public ResponseEntity<SimpleAccount> remoteAuthenticate() {
        return ResponseEntity.ok(authenticationService.remoteAuthenticate());
    }

}
