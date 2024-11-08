package com.project.telegram.auth;

import com.project.telegram.auth.account.AccountRepository;
import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        var account = accountRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> BusinessException.of(ExceptionType.ACCOUNT_EXISTS));

        return new AppUserDetails(account);
    }
}
