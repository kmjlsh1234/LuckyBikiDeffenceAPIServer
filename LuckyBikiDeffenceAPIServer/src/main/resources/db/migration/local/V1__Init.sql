CREATE TABLE `product`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '상품 고유번호',
    `name`        varchar(20) NOT NULL COMMENT '상품 이름',
    `description` varchar(50) NOT NULL COMMENT '상품 설명',
    `status`      tinyint     NOT NULL COMMENT '상품 상태',
    `type`        tinyint     NOT NULL COMMENT '상품 타입',
    `quantity`    int         NOT NULL COMMENT '상품 개수',
    `price`       int         NOT NULL COMMENT '상품 가격',
    `created_at`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='상품';

CREATE TABLE `gold`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '골드고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `amount`     int       NOT NULL DEFAULT 0 COMMENT '금액',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='골드';

CREATE TABLE `gold_record`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '골드기록 고유번호',
    `user_id`        bigint       NOT NULL COMMENT '유저고유번호',
    `change_type`    tinyint      NOT NULL COMMENT '변경타입(add, use)',
    `change_gold`    int          NOT NULL COMMENT '변경골드',
    `result_gold`    int          NOT NULL COMMENT '결과골드',
    `change_desc`    varchar(255) NULL COMMENT '변경설명',
    `idempotent_key` varchar(255) NOT NULL COMMENT '멱등키',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='골드기록';

CREATE TABLE `diamond`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '다이아몬드 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `amount`     int       NOT NULL DEFAULT 0 COMMENT '금액',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='다이아몬드';

CREATE TABLE `diamond_record`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '다이아몬드 기록 고유번호',
    `user_id`        bigint       NOT NULL COMMENT '유저고유번호',
    `change_type`    tinyint      NOT NULL COMMENT '변경타입(add, use)',
    `change_diamond` int          NOT NULL COMMENT '변경 다이아몬드',
    `result_diamond` int          NOT NULL COMMENT '결과 다이아몬드',
    `change_desc`    varchar(255) NULL COMMENT '변경설명',
    `idempotent_key` varchar(255) NOT NULL COMMENT '멱등키',
    `created_at`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='다이아몬드 기록';

CREATE TABLE `jwt_record`
(
    `record_no`        bigint    NOT NULL AUTO_INCREMENT COMMENT '발급번호',
    `user_id`          bigint    NOT NULL COMMENT '고유번호',
    `refresh_token_id` bigint    NOT NULL COMMENT '리프레쉬토큰고유번호',
    `ip_address`       varchar(20)        DEFAULT NULL COMMENT '아이피주소',
    `expire_datetime`  datetime  NOT NULL COMMENT '만료일시',
    `logout_at`        timestamp NULL     DEFAULT NULL COMMENT '로그아웃시각',
    `created_at`       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    PRIMARY KEY (`record_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='jwt토큰발급기록';

CREATE TABLE `profile`
(
    `id`         bigint    NOT NULL AUTO_INCREMENT COMMENT '프로필 고유번호',
    `user_id`    bigint    NOT NULL COMMENT '유저고유번호',
    `nickname`   varchar(100)       DEFAULT NULL COMMENT '닉네임',
    `level`      integer   NOT NULL DEFAULT 1 COMMENT '레벨',
    `ex`         bigint    NOT NULL DEFAULT 0 COMMENT '경험치',
    `image`      varchar(100)       DEFAULT NULL COMMENT '이미지 경로',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='프로필';

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

CREATE TABLE `users`
(
    `user_id`    bigint    NOT NULL AUTO_INCREMENT COMMENT '유저고유번호',
    `status`     tinyint   NOT NULL DEFAULT '0' COMMENT '유저상태',
    `password`   varchar(60)        DEFAULT NULL COMMENT '패스워드',
    `email`      varchar(100)       DEFAULT NULL COMMENT '이메일',
    `user_type`  tinyint   NOT NULL DEFAULT '0' COMMENT '유저타입 0: 멤버 1: 홀덤',
    `login_at`   timestamp NULL     DEFAULT NULL COMMENT '로그인시각',
    `logout_at`  timestamp NULL     DEFAULT NULL COMMENT '로그아웃시각',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시각',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '변경시각',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='유저';