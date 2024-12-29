package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRepository;
import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import com.suhanlee.luckybikideffenceapiserver.user.model.Profile;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.param.UserJoinParam;
import com.suhanlee.luckybikideffenceapiserver.user.repository.ProfileRepository;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.redis.connection.RedisInvalidSubscriptionException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final GoldRepository goldRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    @Transactional(rollbackFor = Exception.class)
    public void joinUser(UserJoinParam userJoinParam) {
        checkJoinParameter(userJoinParam);

        Users user = userRepository.save(Users.builder()
                .status(UserStatus.LOGOUT)
                .email(userJoinParam.getEmail())
                .password(bCryptPasswordEncoder.encode(userJoinParam.getPassword()))
                .build());

        //회원가입 시 프로필 등록
        profileRepository.save(Profile.builder()
                .userId(user.getUserId())
                .level(1)
                .ex(0L)
                .image("profile_0")
                .nickname(null)
                .build());

        //회원가입 시 재화 테이블에 등록
        goldRepository.save(Gold.builder()
                .userId(user.getUserId())
                .amount(0)
                .build());
    }

    public User getUser(long userId) {
        return null;
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

        //동일한 이메일이 존재하는지 체크
        if(userRepository.existsByEmail(userJoinParam.getEmail())) {
            throw new RestException(ErrorCode.EMAIL_ALREADY_EXIST);
        }

        //비밀번호 길이 체크
        if(userJoinParam.getPassword().length() < 6 || userJoinParam.getPassword().length() > 12){
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        //TODO : 형식도 체크
    }
}
