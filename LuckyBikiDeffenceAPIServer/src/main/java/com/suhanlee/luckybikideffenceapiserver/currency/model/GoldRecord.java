package com.suhanlee.luckybikideffenceapiserver.currency.model;

import com.suhanlee.luckybikideffenceapiserver.currency.constants.ChangeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "gold_record")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class GoldRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "change_type")
    private ChangeType changeType;

    @Column(name = "change_gold")
    private int changeGold;

    @Column(name = "result_gold")
    private int resultGold;

    @Column(name = "change_desc")
    private String changeDesc;

    @Column(name = "idempotent_key")
    private String idempotentKey;

    @CreatedDate
    private LocalDateTime createdAt;
}
