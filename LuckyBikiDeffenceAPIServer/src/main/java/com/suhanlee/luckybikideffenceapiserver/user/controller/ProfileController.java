package com.suhanlee.luckybikideffenceapiserver.user.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.user.param.ProfileModParam;
import com.suhanlee.luckybikideffenceapiserver.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    //프로필 추가
    @PostMapping("/profile")
    public ResponseEntity<?> addProfile(@AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(profileService.addProfile(userId));
    }

    //프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<?> modProfile(@RequestBody ProfileModParam profileModParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        profileModParam.setUserId(userId);
        return ResponseEntity.ok(profileService.modProfile(profileModParam));
    }
}
