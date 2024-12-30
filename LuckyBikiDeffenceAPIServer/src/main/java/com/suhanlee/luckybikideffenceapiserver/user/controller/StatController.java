package com.suhanlee.luckybikideffenceapiserver.user.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.game.param.GameInfoParam;
import com.suhanlee.luckybikideffenceapiserver.user.param.StatUpdateParam;
import com.suhanlee.luckybikideffenceapiserver.user.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatController {

    private final StatService statService;

    //스탯 조회
    @GetMapping("/v1/stats")
    public ResponseEntity<?> getStat(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(statService.getStat(userId));
    }

    //스탯 업데이트
    @PostMapping("/v1/stats")
    public ResponseEntity<?> updateStat(@RequestBody StatUpdateParam statUpdateParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        statUpdateParam.setUserId(userId);
        statService.updateStat(statUpdateParam);
        return ResponseEntity.ok().build();
    }
}
