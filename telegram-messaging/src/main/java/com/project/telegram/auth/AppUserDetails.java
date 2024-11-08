package com.project.telegram.auth;

import com.project.telegram.auth.account.Account;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@EqualsAndHashCode
public class AppUserDetails implements UserDetails {

    @Delegate(excludes = Builder.class)
    private final Account account;

    private final Collection<? extends GrantedAuthority> authorities;


    public AppUserDetails(Account account) {
        this.account = account;
        this.authorities = CollectionUtils.emptyIfNull(account.getRoles())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
