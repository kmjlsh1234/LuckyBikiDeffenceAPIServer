package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.constants.Os;
import com.suhanlee.luckybikideffenceapiserver.user.model.JwtRecord;
import com.suhanlee.luckybikideffenceapiserver.user.model.RefreshToken;
import com.suhanlee.luckybikideffenceapiserver.user.repository.JwtRecordRepository;
import com.suhanlee.luckybikideffenceapiserver.user.repository.RefreshTokenRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    @Value("${jwt.secret}")
    private String secret;

    private final JwtRecordRepository jwtRecordRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // issuing refresh token
    public RefreshToken issuingRefreshToken(long userId) {
        // make uuid as refresh Token
        String refreshTokenString = UUID.randomUUID().toString();
        // refresh token expire date
        LocalDateTime expireDate = LocalDateTime.now().plusDays(JwtProperties.REFRESH_TOKEN_EXPIRATION_DATE);
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshTokenString)
                .expireDatetime(expireDate)
                .createdAt(LocalDateTime.now())
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createJwtToken(String email, long userId, String ip, long refreshTokenId){
        String subject = email + JwtProperties.SPLITTER + userId;
        String converted = DatatypeConverter.printHexBinary(subject.getBytes());
        LocalDateTime expiredDate = LocalDateTime.now().plusSeconds(JwtProperties.EXPIRATION_TIME_1DAY_SECOND);

        JwtRecord record = jwtRecordRepository.save(JwtRecord.builder()
                .userId(userId)
                .refreshTokenId(refreshTokenId)
                .ipAddress(ip)
                .expireDatetime(expiredDate)
                .build());

        JWTCreator.Builder builder = JWT.create()
                .withSubject(converted)
                .withJWTId(String.valueOf(record.getRecordNo()))
                .withClaim(JwtProperties.REFRESH_TOKEN_Id_KEY, refreshTokenId)
                .withAudience(JwtProperties.AUDIENCE)
                .withExpiresAt(Timestamp.valueOf(expiredDate));

        //create JWT Token
        return builder.sign(HMAC512(secret.getBytes()));
    }
}
