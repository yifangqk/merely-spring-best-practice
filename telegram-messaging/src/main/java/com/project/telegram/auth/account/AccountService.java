package com.project.telegram.auth.account;

import com.project.telegram.auth.AccountRole;
import com.project.telegram.auth.RegisterRequest;
import com.project.telegram.auth.RoleFixture;
import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;

    public void registerAccount(RegisterRequest request) {
        var phone = request.getStandardizedPhone();

        accountRepository.findByPhone(request.getPhone())
                .ifPresent(existAccount -> {
                    throw BusinessException.of(ExceptionType.ACCOUNT_EXISTS);
                });
        var account = Account.builder()
                .phone(phone)
                .password(encoder.encode(request.getPassword()))
                .build()
                .addRoles(AccountRole.builder().role(RoleFixture.ROLE_USER).build());
        accountRepository.save(account);
    }

    public Account getAccountByPhone(String phone) {
        return accountRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessException.of(ExceptionType.ACCOUNT_NOT_FOUND));
    }
}
