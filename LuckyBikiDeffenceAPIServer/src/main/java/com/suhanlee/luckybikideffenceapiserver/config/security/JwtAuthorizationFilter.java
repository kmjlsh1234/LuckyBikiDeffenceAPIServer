package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.service.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JWT 인증을 위한 필터
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final ArrayList<String> excludeURL; //jwt를 넣더라도 check하지 않는 API URL 리스트
    private final JWTVerifier jwtVerifier;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secretKey, JwtAuthenticationService jwtAuthenticationService) {
        super(authenticationManager);
        this.jwtAuthenticationService = jwtAuthenticationService;
        excludeURL = new ArrayList<>();
        excludeURL.add("/api/v1/token/refresh");
        excludeURL.add("/api/v1/user/join");
        excludeURL.add("/auth/login");
        jwtVerifier = JWT.require(Algorithm.HMAC512(secretKey.getBytes())).build();
    }

    private boolean isContainExcludeURL(String url) { return excludeURL.contains(url); }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        //권한 헤더(jwt) 취득
        String header = request.getHeader(JwtProperties.HEADER_AUTH);
        String requestUrl = request.getRequestURI();

        //JWT없거나 검사할 필요가 없으면 스킵
        if(header == null || isContainExcludeURL(requestUrl)) {
            chain.doFilter(request,response);
            return;
        }

        //유저 정보 취득
        Authentication authentication = getUserNamePasswordAuthentication(request, response);
        if(authentication == null) {
            log.info("no authentication");
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private Authentication getUserNamePasswordAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(JwtProperties.HEADER_AUTH);

        //parse token and check validate
        DecodedJWT decodedJWT;
        try{
            decodedJWT = jwtVerifier.verify(token);
        } catch(TokenExpiredException e) { //토큰 기간 만료
            OutErrorMessage(response, ErrorCode.JWT_TOKEN_EXPIRATION.getStatus(), ErrorCode.JWT_TOKEN_EXPIRATION.getCode(), ErrorCode.JWT_TOKEN_EXPIRATION.getMessage());
            return null;
        } catch(SignatureVerificationException | JWTDecodeException | InvalidClaimException e) { //토큰 오류
            OutErrorMessage(response, ErrorCode.INVALID_AUTH_TOKEN.getStatus(), ErrorCode.INVALID_AUTH_TOKEN.getCode(), ErrorCode.INVALID_AUTH_TOKEN.getMessage());
            return null;
        }
        catch(JWTVerificationException e) { //토큰 검증 오류
            OutErrorMessage(response, ErrorCode.JWT_TOKEN_AUTH_ERROR.getStatus(), ErrorCode.JWT_TOKEN_AUTH_ERROR.getCode(), ErrorCode.JWT_TOKEN_AUTH_ERROR.getMessage());
            return null;
        }
        String converted = decodedJWT.getSubject();
        String issueNo = decodedJWT.getId();
        log.debug("converted : {}", converted);
        log.debug("issueNo : {}", issueNo);

        //TODO : 토큰 발행번호로 블랙리스트 조회

        String subject = new String(DatatypeConverter.parseHexBinary(converted));
        String[] subArray = subject.split(JwtProperties.SPLITTER);

        log.debug("subject : {}", subject);
        for(int i=0; i<subArray.length; i++) {
            log.debug("subArray{} : {}", i, subArray[i]);
        }

        if(subArray[0] != null) {
            long userId = Long.parseLong(subArray[1]);
            String email = subArray[0];

            // TODO : 유저 user_Id로 블랙리스트에 포함 되어 있는지 확인한다.

            UserPrincipal principal = UserPrincipal.builder()
                    .userId(userId)
                    .email(email)
                    .build();
            return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }
        return null;
    }

    private void OutErrorMessage(HttpServletResponse response, int statusCode, int errorCode, String errorMessage) throws IOException {
        //verification error 처리
        //filter에서 생성된 에러의 경우 controller advice에서 핸들링 할 수 없다.
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("error_code", errorCode);
        resultMap.put("error_message", errorMessage);
        resultMap.put("error_timestamp", LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(resultMap));
        out.flush();
    }
}
