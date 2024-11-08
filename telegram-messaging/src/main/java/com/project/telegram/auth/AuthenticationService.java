package com.project.telegram.auth;

import com.project.telegram.admin.AdminChangePasswordRequest;
import com.project.telegram.application.RequestContext;
import com.project.telegram.auth.account.Account;
import com.project.telegram.auth.account.AccountRepository;
import com.project.telegram.auth.account.ChangePasswordRequest;
import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenHandler jwtTokenHandler;
    private final RequestContext requestContext;
    private final PasswordEncoder encoder;

    public void registerAccount(RegisterRequest request) {
        var phone = request.getPhone();
        request.setPhone(phone);

        accountRepository.findByPhone(phone)
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

    public LoginResponse authenticate(LoginRequest request) {
        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getStandardizedPhone(), request.getPassword()));
        var userDetails = (AppUserDetails) authentication.getPrincipal();

        return LoginResponse.builder()
                .phone(userDetails.getPhone())
                .fullName(userDetails.getUsername())
                .token(jwtTokenHandler.issueToken(userDetails))
                .build();
    }

    public void changePassword(ChangePasswordRequest request) {
        var currentUser = requestContext.getCurrentUser();

        var changePasswordCount = currentUser.getChangePasswordCount() + 1;

        accountRepository.save(currentUser.toBuilder()
                .changePasswordCount(changePasswordCount)
                .locked(changePasswordCount > 5)
                .build());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(currentUser.getPhone(), request.getOldPassword()));

        accountRepository.save(currentUser.toBuilder()
                .password(encoder.encode(request.getNewPassword()))
                .changePasswordCount(0)
                .build());
    }

    public void adminChangePassword(AdminChangePasswordRequest request) {
        var user = accountRepository.findByPhone(request.getStandardizedPhone())
                .orElseThrow(() -> BusinessException.of(ExceptionType.ACCOUNT_NOT_EXISTS));

        user = user.toBuilder()
                .password(encoder.encode(request.getNewPassword()))
                .changePasswordCount(0)
                .build();

        accountRepository.save(user);
    }

    public SimpleAccount remoteAuthenticate() {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getAuthorities().stream()
                .map(o -> new SimpleAccount(userDetails.getUsername(), o.getAuthority()))
                .findFirst().orElse(new SimpleAccount(userDetails.getUsername(), "NO_ROLE"));
    }
}
