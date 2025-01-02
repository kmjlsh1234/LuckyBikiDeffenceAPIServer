package com.suhanlee.luckybikideffenceapiserver.currency.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.currency.param.EnergyAddParam;
import com.suhanlee.luckybikideffenceapiserver.currency.param.EnergyUseParam;
import com.suhanlee.luckybikideffenceapiserver.currency.service.EnergyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EnergyController {

    private final EnergyService energyService;

    //에너지 조회
    @GetMapping("/v1/energies")
    public ResponseEntity<?> getEnergy(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(energyService.getEnergy(userId));
    }

    //에너지 추가
    @PostMapping("/v1/energies/add")
    public ResponseEntity<?> addEnergy(@RequestBody EnergyAddParam energyAddParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        energyAddParam.setUserId(userId);
        return ResponseEntity.ok(energyService.addEnergy(energyAddParam));
    }

    //에너지 소모
    @PostMapping("/v1/energies/use")
    public ResponseEntity<?> useEnergy(@RequestBody EnergyUseParam energyUseParam, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        long userId = userPrincipal.getUserId();
        energyUseParam.setUserId(userId);
        return ResponseEntity.ok(energyService.useEnergy(energyUseParam));
    }
}
