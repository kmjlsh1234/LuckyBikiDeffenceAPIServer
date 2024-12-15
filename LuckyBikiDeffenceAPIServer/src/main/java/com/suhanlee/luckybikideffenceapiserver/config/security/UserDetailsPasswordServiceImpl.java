package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsPasswordServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByEmail : " + username);
        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));
        log.info("5 : " + user.getEmail());
        UserStatus status = getStatus(user);

        return UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(status)
                .build();
    }

    private UserStatus getStatus(Users user){
        UserStatus status = user.getStatus();
        switch(status){
            //정지
            //밴
            case EXIT -> throw new RestException(ErrorCode.USER_NOT_FOUND);
            default -> {}
        }
        return status;
    }


}
