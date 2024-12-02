package com.suhanlee.luckybikideffenceapiserver.config.security.constants;

public class JwtProperties {
    public static final String AUDIENCE = "luckybikideffence_player";
    public static final String HEADER_AUTH = "Authorization";
    public static final String SPLITTER = ",";
    public static final int REFRESH_TOKEN_EXPIRATION_DATE = 30; // refreshToken 30 day
    public static final String REFRESH_TOKEN_Id_KEY = "refresh";
    public static final String REFRESH_HEADER_STRING = "ReAuthentication";
    public static final int EXPIRATION_TIME_1DAY_SECOND = 86_400; // 1
    public static final int REFRESH_TOKEN_NEED_REISSUE = 4;
    public static final String RESULT_MAP_AUTH = "auth";
    public static final String RESULT_MAP_REFRESH = "refresh";
}
