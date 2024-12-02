package com.suhanlee.luckybikideffenceapiserver.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String id) throws UsernameNotFoundException;
}
