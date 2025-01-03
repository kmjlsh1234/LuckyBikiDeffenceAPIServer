package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.model.LoginViewModel;
import com.suhanlee.luckybikideffenceapiserver.user.model.RefreshToken;
import com.suhanlee.luckybikideffenceapiserver.user.model.UserSimple;
import com.suhanlee.luckybikideffenceapiserver.user.service.JwtAuthenticationService;
import com.suhanlee.luckybikideffenceapiserver.user.service.UserLoginAfterService;
import com.suhanlee.luckybikideffenceapiserver.util.WebUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;


/**
 * 로그인 후 인증 토큰을 발급하는 필터
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserLoginAfterService userLoginAfterService;
    private final WebUtil webUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserLoginAfterService userLoginAfterService, JwtAuthenticationService jwtAuthenticationService, WebUtil webUtil) {
        this.authenticationManager = authenticationManager;
        this.userLoginAfterService = userLoginAfterService;
        this.webUtil = webUtil;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.setFilterProcessesUrl("/auth/login");
    }

    //로그인 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //로그인 입력 정보
        LoginViewModel credentials;
        try{
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        } catch (IOException e) { // 입력정보를 파싱하지 못함
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        // 필수 입력정보가 없음
        if(credentials == null || !StringUtils.hasText(credentials.getEmail()) || !StringUtils.hasText(credentials.getPassword())){
            throw new RestException(ErrorCode.CHANGE_AMOUNT_NOT_ENOUGH);
        }

        //Create Login Token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }
        return authentication;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) {
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        long userId = principal.getUserId();
        userLoginAfterService.updateUserLoginStatus(userId);
        String realIp = webUtil.getClientIp(request);

        RefreshToken refreshToken = jwtAuthenticationService.issuingRefreshToken(userId);

        //creat JWT Token
        response.addHeader(JwtProperties.HEADER_AUTH,
                jwtAuthenticationService.createJwtToken(principal.getEmail(), userId, realIp, refreshToken.getId()));

        if (refreshToken.getRefreshToken() != null) {
            response.addHeader(JwtProperties.REFRESH_HEADER_STRING, refreshToken.getRefreshToken());  // refreshToken
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(UserSimple.builder()
                    .userId(userId)
                    .email(principal.getEmail())
                    .build());
            okResultResponse(response, result);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void okResultResponse(HttpServletResponse response, String result) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
        } catch (Exception e) {
            log.error("sign up vo fail");
        }
    }
}
