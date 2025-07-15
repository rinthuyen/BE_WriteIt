package com.writeit.write_it.security.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.writeit.write_it.entity.User;
import com.writeit.write_it.entity.enums.Role;
import com.writeit.write_it.entity.enums.Status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String displayedName;
    private final Status status;
    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
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
        return status == Status.ACTIVE;
    }

    public static CustomUserDetails build(User user) {
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getDisplayedName(),
                user.getStatus(), user.getRole());
    }
}
