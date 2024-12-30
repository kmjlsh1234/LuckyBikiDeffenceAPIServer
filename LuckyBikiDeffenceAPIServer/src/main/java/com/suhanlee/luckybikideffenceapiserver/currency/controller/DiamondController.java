package com.suhanlee.luckybikideffenceapiserver.currency.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.currency.param.DiamondModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.service.DiamondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiamondController {

    private final DiamondService diamondService;

    //다이아몬드 조회
    @GetMapping("/v1/diamonds")
    public ResponseEntity<?> getDiamond(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(diamondService.getDiamond(userId));
    }

    //다이아몬드 입금
    @PostMapping("/v1/diamonds/deposit")
    public ResponseEntity<?> diamondDeposit(@RequestBody DiamondModParam diamondModParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        diamondModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(diamondService.diamondDeposit(diamondModParam));
    }

    //다이아몬드 인출
    @PostMapping("/v1/diamonds/withdraw")
    public ResponseEntity<?> diamondWithdraw(@RequestBody DiamondModParam diamondModParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        diamondModParam.setUserId(userPrincipal.getUserId());
        return ResponseEntity.ok(diamondService.diamondWithDraw(diamondModParam));
    }
}
