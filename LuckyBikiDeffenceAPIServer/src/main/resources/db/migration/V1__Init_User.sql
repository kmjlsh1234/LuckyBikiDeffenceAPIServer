CREATE TABLE users
(
    user_id    BIGINT AUTO_INCREMENT   NOT NULL COMMENT '유저 고유번호',
    status     TINYINT NOT NULL COMMENT '상태',
    email      VARCHAR(50) NULL COMMENT '이메일',
    password   VARCHAR(100) NULL COMMENT '패스워드',
    nickname   VARCHAR(50) NULL COMMENT '닉네임',
    login_at   timestamp NULL COMMENT '로그인 시간',
    logout_at  timestamp NULL COMMENT '로그아웃 시간',
    login_ip   VARCHAR(50) NULL COMMENT '로그인 아이피',
    created_at timestamp DEFAULT NOW() NULL COMMENT '생성시각',
    updated_at timestamp DEFAULT NOW() NULL COMMENT '변경시각',
    CONSTRAINT `PRIMARY` PRIMARY KEY (user_id)
) COMMENT ='유저';

CREATE TABLE `jwt_record`
(
    `record_no`       bigint    NOT NULL AUTO_INCREMENT COMMENT '기록번호',
    `user_id`        bigint    NOT NULL COMMENT '유저 고유번호',
    `refresh_token_id` bigint    NOT NULL COMMENT '리프레쉬토큰고유번호',
    `ip_address`      varchar(20)        DEFAULT NULL COMMENT '아이피주소',
    `expire_datetime` datetime  NOT NULL COMMENT '만료일시',
    `logout_at`       timestamp NULL     DEFAULT NULL COMMENT '로그아웃시각',
    `created_at`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`record_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='jwt 기록';

CREATE TABLE `refresh_token`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '리프레쉬토큰 고유번호',
    `user_id`         bigint       NOT NULL COMMENT '유저고유번호',
    `refresh_token`   varchar(100) NOT NULL COMMENT '리프레쉬토큰',
    `expire_datetime` datetime     NOT NULL COMMENT '만료일시',
    `created_at`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='리프레시 토큰';