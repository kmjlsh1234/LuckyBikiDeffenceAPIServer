package com.suhanlee.luckybikideffenceapiserver.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "stat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "longest_play_time")
    private long longestPlayTime;

    @Column(name = "boss_kill_count")
    private long bossKillCount;

    @Column(name = "kill_count")
    private long killCount;

    @Column(name = "solo_play_time")
    private long soloPlayTime;

    @Column(name = "multi_play_time")
    private long multiPlayTime;

    @Column(name = "solo_play_count")
    private int soloPlayCount;

    @Column(name = "multi_play_count")
    private int multiPlayCount;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
