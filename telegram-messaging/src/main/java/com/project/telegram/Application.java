package com.project.telegram;

import com.project.telegram.auth.AccountRole;
import com.project.telegram.auth.RoleFixture;
import com.project.telegram.auth.account.Account;
import com.project.telegram.auth.account.AccountRepository;
import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            String admin = "0979439886";
//            accountRepository.findByPhone(admin)
//                    .ifPresent(account -> {
//                        throw BusinessException.of(ExceptionType.ACCOUNT_ADMIN_CREATED);
//                    });
//            var adminAccount = Account.builder()
//                    .phone("0979439886")
//                    .password(passwordEncoder.encode("123123123"))
//                    .changePasswordCount(0)
//                    .build()
//                    .addRoles(AccountRole.builder().role(RoleFixture.ROLE_ADMIN).build());
//            accountRepository.save(adminAccount);

            System.out.println("Application started successfully");
            // TODO send notification to group
        };
    }

}
