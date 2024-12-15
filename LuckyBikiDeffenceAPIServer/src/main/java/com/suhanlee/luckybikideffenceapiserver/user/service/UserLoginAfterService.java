package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginAfterService {
    private final UserRepository userRepository;

    public void updateUserLoginStatus(long userId){
        userRepository.findByUserId(userId).ifPresent(user -> {
            user.setStatus(UserStatus.NORMAL);
            user.setLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }
}
