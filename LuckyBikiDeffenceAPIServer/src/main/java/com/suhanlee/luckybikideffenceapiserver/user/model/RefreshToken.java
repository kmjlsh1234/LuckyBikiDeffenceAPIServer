package com.suhanlee.luckybikideffenceapiserver.user.model;

import com.suhanlee.luckybikideffenceapiserver.user.constants.Os;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private String refreshToken;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime expireDatetime;

    @Column(columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

}
