package com.suhanlee.luckybikideffenceapiserver.user.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.user.param.UserJoinParam;
import com.suhanlee.luckybikideffenceapiserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/v1/user/join")
    public ResponseEntity<?> joinUser(@RequestBody UserJoinParam userJoinParam) {
        userService.joinUser(userJoinParam);
        return ResponseEntity.ok().build();
    }

    //유저 정보 조회(프로필 포함)
    @GetMapping("/v1/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(userService.getUser(userId));
    }

    //로그아웃
    @PostMapping("/v1/user/logout")
    public ResponseEntity<?> logoutUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.logoutUser(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    //탈퇴
    @DeleteMapping("/v1/user/leave")
    public ResponseEntity<?> leaveUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.leaveUser(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }
}
