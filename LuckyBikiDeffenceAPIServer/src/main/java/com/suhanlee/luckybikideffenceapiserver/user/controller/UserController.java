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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/join")
    public ResponseEntity<?> joinUser(@RequestBody UserJoinParam userJoinParam) {
        userService.joinUser(userJoinParam);
        return ResponseEntity.ok().build();
    }

    //로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<?> logoutUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.logoutUser(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    //탈퇴
    @DeleteMapping("/user/leave")
    public ResponseEntity<?> leaveUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.leaveUser(userPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }
}
