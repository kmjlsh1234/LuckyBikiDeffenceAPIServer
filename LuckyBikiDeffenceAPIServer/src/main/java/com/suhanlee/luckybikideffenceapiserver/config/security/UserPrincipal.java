package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserPrincipal implements UserDetails {

    @Builder
    public UserPrincipal(long userId, String email, String password, String nickName, int status) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.status = status;
    }

    private final long userId;
    @Getter
    private final String email;
    private final String password;
    private final String nickName;
    private final int status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.status == UserStatus.NORMAL.getStatus()
                || this.status == UserStatus.LOGOUT.getStatus();
    }


}
