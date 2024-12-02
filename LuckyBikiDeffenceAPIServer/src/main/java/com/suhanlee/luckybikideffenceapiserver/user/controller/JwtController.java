package com.suhanlee.luckybikideffenceapiserver.user.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.param.RefreshTokenParam;
import com.suhanlee.luckybikideffenceapiserver.user.service.JwtAuthenticationService;
import com.suhanlee.luckybikideffenceapiserver.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class JwtController {

    private final WebUtil webUtil;
    private final JwtAuthenticationService jwtAuthenticationService;

    //refresh token 발급
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenParam refreshTokenParam,
                                          @RequestHeader(name = "Authorization") String authToken,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        //토큰 refresh 발급
        String ip = webUtil.getClientIp(request);
        Map<String, String> newTokens = jwtAuthenticationService.refreshToken(authToken, refreshTokenParam.getRefreshToken(), ip);
        //auth 토큰
        response.addHeader(JwtProperties.HEADER_AUTH, newTokens.get(JwtProperties.RESULT_MAP_AUTH));
        //refresh 토큰
        if(newTokens.containsKey(JwtProperties.RESULT_MAP_REFRESH)) {
            response.addHeader(JwtProperties.REFRESH_HEADER_STRING, newTokens.get(JwtProperties.RESULT_MAP_REFRESH));
        }

        return ResponseEntity.ok().build();
    }
}
