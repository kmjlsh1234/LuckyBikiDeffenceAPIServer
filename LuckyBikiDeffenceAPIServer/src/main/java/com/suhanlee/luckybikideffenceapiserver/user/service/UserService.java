package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRepository;
import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.param.UserJoinParam;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GoldRepository goldRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    @Transactional(rollbackFor = Exception.class)
    public void joinUser(UserJoinParam userJoinParam) {
        checkJoinParameter(userJoinParam);
        log.info(userJoinParam.getEmail());
        log.info(userJoinParam.getPassword());
        Users user = userRepository.save(Users.builder()
                .status(UserStatus.LOGOUT)
                .email(userJoinParam.getEmail())
                .password(bCryptPasswordEncoder.encode(userJoinParam.getPassword()))
                .build());

        //회원가입 시 재화 테이블에 등록
        goldRepository.save(Gold.builder()
                .userId(user.getUserId())
                .amount(0)
                .build());
    }

    //로그아웃
    @Transactional(rollbackFor = Exception.class)
    public void logoutUser(long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setStatus(UserStatus.LOGOUT);
            user.setLogoutAt(LocalDateTime.now());
        });

        //TODO : REDIS 토큰 Invalid 처리하기
    }

    //탈퇴
    @Transactional(rollbackFor = Exception.class)
    public void leaveUser(long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setStatus(UserStatus.EXIT);
            user.setLogoutAt(LocalDateTime.now());
            //TODO : 정보 탈퇴 테이블에 옮기고 지우기
        });
        //TODO : REDIS 토큰 Invalid 처리하기
    }

    private void checkJoinParameter(UserJoinParam userJoinParam) {

    }
}
