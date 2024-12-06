package com.suhanlee.luckybikideffenceapiserver.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class ProfileVo {
    private long id;
    private long userId;
    private int level;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
