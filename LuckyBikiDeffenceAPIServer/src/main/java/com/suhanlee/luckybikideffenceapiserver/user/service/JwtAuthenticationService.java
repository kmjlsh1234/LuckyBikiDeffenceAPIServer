package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtPropertes;
import com.suhanlee.luckybikideffenceapiserver.user.model.JwtRecord;
import com.suhanlee.luckybikideffenceapiserver.user.repository.JwtRecordRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    @Value("${jwt.secret}")
    private String secret;

    private final JwtRecordRepository jwtRecordRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createJwtToken(String email, long userId, String ip){
        String subject = email + JwtPropertes.SPLITTER + userId;
        String converted = DatatypeConverter.printHexBinary(subject.getBytes());
        LocalDateTime expiredDate = LocalDateTime.now().plusSeconds(JwtPropertes.EXPIRATION_TIME_1DAY_SECOND);

        JwtRecord record = jwtRecordRepository.save(JwtRecord.builder()
                .userId(userId)
                .ipAddress(ip)
                .expireDatetime(expiredDate)
                .build());

        JWTCreator.Builder builder = JWT.create()
                .withSubject(converted)
                .withJWTId(String.valueOf(record.getRecordNo()))
                .withAudience(JwtPropertes.AUDIENCE)
                .withExpiresAt(Timestamp.valueOf(expiredDate));

        //create JWT Token
        return builder.sign(HMAC512(secret.getBytes()));
    }
}
