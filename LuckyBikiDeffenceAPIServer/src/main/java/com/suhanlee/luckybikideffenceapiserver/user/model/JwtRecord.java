package com.suhanlee.luckybikideffenceapiserver.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "jwt_record")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class JwtRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordNo;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(columnDefinition = "DATETIME", name = "expire_datetime")
    private LocalDateTime expireDatetime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime logoutAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public JwtRecord(long userId, String ipAddress, LocalDateTime expireDatetime) {
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.expireDatetime = expireDatetime;
    }
}
