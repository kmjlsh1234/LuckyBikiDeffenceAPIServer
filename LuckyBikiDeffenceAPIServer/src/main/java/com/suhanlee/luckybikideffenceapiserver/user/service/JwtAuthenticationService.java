package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.suhanlee.luckybikideffenceapiserver.config.security.constants.JwtProperties;
import com.suhanlee.luckybikideffenceapiserver.user.model.JwtRecord;
import com.suhanlee.luckybikideffenceapiserver.user.model.RefreshToken;
import com.suhanlee.luckybikideffenceapiserver.user.repository.JwtRecordRepository;
import com.suhanlee.luckybikideffenceapiserver.user.repository.RefreshTokenRepository;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Service
public class JwtAuthenticationService {

    private final String secret;

    private final UserRepository userRepository;
    private final JwtRecordRepository jwtRecordRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTVerifier jwtVerifier;

    private static final Long DAY = 3600L * 24;

    public JwtAuthenticationService(UserRepository userRepository,
                                    JwtRecordRepository jwtRecordRepository,
                                    RefreshTokenRepository refreshTokenRepository,
                                    @Value("mysecret") String secret) {
        this.userRepository = userRepository;
        this.jwtRecordRepository = jwtRecordRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.secret = secret;
        jwtVerifier = JWT.require(HMAC512(this.secret.getBytes()))
                .acceptExpiresAt(DAY * 365)
                .build();
    }

    // 새로운 refresh token 발급(단일책임)
    @Transactional(rollbackFor = Exception.class)
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

    public Map<String, String> refreshToken(String token, String refreshToken, String ip){
        HashMap<String, String> resultMap = new HashMap<>();
        //parse the token and validate it
        DecodedJWT decodedJWT = verify(token);
        String converted = decodedJWT.getSubject();

        if(converted == null){
            return null;
        }
        if(refreshToken == null){
            return null;
        }

        String subject = new String(DatatypeConverter.parseHexBinary(converted));
        String[] subArray = subject.split(JwtProperties.SPLITTER);
        if(subArray[0] == null){
            return null;
        }
        long userId = Long.parseLong(subArray[1]);
        if(userId <1L){
            return null;
        }

        //가장 최근에 발급된 refresh token을 가져온다
        RefreshToken retrievedRefreshToken = refreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken)
                .orElseThrow(() -> {
                    log.error("failed refresh token : TOKEN_REFRESH_FAIL_INVALID_REFRESH_TOKEN : " + userId);
                    return null;
                });
        long refreshTokenId = retrievedRefreshToken.getId();
        LocalDateTime expiredDate = retrievedRefreshToken.getExpireDatetime();
        LocalDateTime currentDate = LocalDateTime.now();

        //refresh token 만료 검사
        if(expiredDate.isBefore(currentDate)){
            return null;
        }

        //토큰 만료 기간이 3일정도 남았으면 refresh 토큰을 새로 발급 해준다.
        if(expiredDate.isBefore(currentDate.plusDays(JwtProperties.REFRESH_TOKEN_NEED_REISSUE))){
            RefreshToken newRefreshToken = issuingRefreshToken(userId);
            refreshTokenId = newRefreshToken.getId();
            resultMap.put(JwtProperties.RESULT_MAP_REFRESH, newRefreshToken.getRefreshToken());
            //delete old refresh token
            refreshTokenRepository.delete(retrievedRefreshToken);
        }

        String email = userRepository.findByUserId(userId).orElseThrow(() ->{
            return null;
        }).getEmail();

        String authToken = createJwtToken(email, userId, ip, refreshTokenId);
        resultMap.put(JwtProperties.RESULT_MAP_AUTH, authToken);
        return resultMap;
    }

    private DecodedJWT verify(String token){
        DecodedJWT decodedJWT;
        try{
            decodedJWT = jwtVerifier.verify(token); //verification
        } catch(JWTVerificationException e){
            //refresh를 진행할 토큰이기 때문에 invalid 여부만 검사 (만료기간은 확인 x)
            return null;
        }
        return decodedJWT;
    }

    //jwt토큰 생성
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
