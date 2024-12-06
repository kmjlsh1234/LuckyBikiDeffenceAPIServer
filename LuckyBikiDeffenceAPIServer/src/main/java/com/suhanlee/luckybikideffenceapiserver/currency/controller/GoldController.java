package com.suhanlee.luckybikideffenceapiserver.currency.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.currency.param.GoldModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.service.GoldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class GoldController {

    private final GoldService goldService;

    //gold 조회
    @GetMapping("/gold")
    public ResponseEntity<?> getGold(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(goldService.getGold(userId));
    }

    //gold 입금
    @PostMapping("/gold/deposit")
    public ResponseEntity<?> goldDeposit(@RequestBody GoldModParam goldModParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        goldModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(goldService.goldDeposit(goldModParam));
    }

    //gold 인출
    @PostMapping("/gold/withdraw")
    public ResponseEntity<?> goldWithdraw(@RequestBody GoldModParam goldModParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        goldModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(goldService.goldWithDraw(goldModParam));
    }
}