package com.suhanlee.luckybikideffenceapiserver.config.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 추가 필드를 받기 위한 커스텀 인증 토큰 (자격 증명)
 */
@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {


    public CustomAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
        super.setAuthenticated(false);
    }
}
