CREATE TABLE gold
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT NULL,
    amount     INT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    CONSTRAINT pk_gold PRIMARY KEY (id)
);

CREATE TABLE gold_record
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT NULL,
    change_type    SMALLINT NULL,
    change_gold    INT NULL,
    result_gold    INT NULL,
    change_desc    VARCHAR(255) NULL,
    idempotent_key VARCHAR(255) NULL,
    created_at     datetime NULL,
    CONSTRAINT pk_gold_record PRIMARY KEY (id)
);

CREATE TABLE jwt_record
(
    record_no        BIGINT NOT NULL,
    user_id          BIGINT NULL,
    refresh_token_id BIGINT NULL,
    ip_address       VARCHAR(255) NULL,
    expire_datetime  datetime NULL,
    logout_at        timestamp NULL,
    created_at       datetime NULL,
    CONSTRAINT pk_jwt_record PRIMARY KEY (record_no)
);

CREATE TABLE profile
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT NULL,
    image      VARCHAR(255) NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    CONSTRAINT pk_profile PRIMARY KEY (id)
);

CREATE TABLE refresh_token
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    user_id         BIGINT NOT NULL,
    refresh_token   VARCHAR(255) NULL,
    expire_datetime datetime NULL,
    created_at      timestamp NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE TABLE users
(
    user_id    BIGINT AUTO_INCREMENT NOT NULL,
    status     SMALLINT NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    nickname   VARCHAR(255) NULL,
    login_at   datetime NULL,
    logout_at  datetime NULL,
    login_ip   VARCHAR(255) NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);