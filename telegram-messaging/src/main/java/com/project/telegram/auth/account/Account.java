package com.project.telegram.auth.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.telegram.auth.AccountRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "accounts")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "change_pwd_cnt")
    private Integer changePasswordCount;

    @Column(name = "is_locked")
    private Boolean locked;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AccountRole> roles;

    public Account addRoles(AccountRole role) {
        if (CollectionUtils.isEmpty(roles)) {
            roles = new HashSet<>();
        }

        roles.add(role);
        role.setAccount(this);
        return this;
    }

}
