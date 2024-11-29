package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.service.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

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
        Authentication authentication = getUserAuthentication(request, response);
        if(authentication == null) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private Authentication getUserAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(JwtProperties.HEADER_AUTH);

        //parse token and check validate
        DecodedJWT decodedJWT;
        try{
            decodedJWT = jwtVerifier.verify(token);
        } catch(TokenExpiredException e) {
            //OutErrorMessage(response, );
            return null;
        } catch(JWTVerificationException e) {
            return null;
        }
        String converted = decodedJWT.getSubject();
        String issueNo = decodedJWT.getId();

        //토큰 발행번호로 블랙리스트 조회

        String subject = new String(DatatypeConverter.parseHexBinary(converted));
        String[] subArray = subject.split(JwtProperties.SPLITTER);

        if(subArray[0] != null) {
            long userId = Long.parseLong(subArray[1]);
            String email = subArray[0];
            // 유저 user_Id로 블랙리스트에 포함 되어 있는지 확인한다.

            UserPrincipal principal = UserPrincipal.builder()
                    .userId(userId)
                    .email(email)
                    .build();
            return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }
        return null;
    }

    private void OutErrorMessage(HttpServletResponse response, int statusCode, int errorCode, String errorMessage) throws IOException {
        //verification error처리
    }
}
