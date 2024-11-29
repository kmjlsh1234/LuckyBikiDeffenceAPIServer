package com.suhanlee.luckybikideffenceapiserver.user.model;

import com.suhanlee.luckybikideffenceapiserver.user.constants.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "status")
    private UserStatus status;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "login_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime loginAt;

    @Column(name = "logout_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logoutAt;

    @Column(name = "login_ip")
    private String loginIp;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
