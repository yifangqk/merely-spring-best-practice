package com.project.telegram.auth.account;

import com.project.telegram.auth.RegisterRequest;
import com.project.telegram.dto.GeneralResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<GeneralResponse> createAccount(@Valid @RequestBody RegisterRequest request) {
        accountService.registerAccount(request);

        return ResponseEntity.ok().body(GeneralResponse.success("Account registered successfully!"));
    }

    @GetMapping("{phone}")
    public ResponseEntity<Account> getAccount(@PathVariable String phone) {
        var account = accountService.getAccountByPhone(phone);

        return ResponseEntity.ok().body(account);
    }
}
