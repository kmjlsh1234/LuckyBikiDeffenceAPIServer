package com.suhanlee.luckybikideffenceapiserver.user.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.user.param.StatModParam;
import com.suhanlee.luckybikideffenceapiserver.user.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    @GetMapping("/v1/stats")
    public ResponseEntity<?> getStat(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(statService.getStat(userId));
    }
}
