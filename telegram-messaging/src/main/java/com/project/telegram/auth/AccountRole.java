package com.project.telegram.auth;

import com.project.telegram.auth.account.Account;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private RoleFixture role;
}
