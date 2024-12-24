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
@Table(name = "diamond_record")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DiamondRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "change_type")
    private ChangeType changeType;

    @Column(name = "change_diamond")
    private int changeDiamond;

    @Column(name = "result_diamond")
    private int resultDiamond;

    @Column(name = "change_desc")
    private String changeDesc;

    @Column(name = "idempotent_key")
    private String idempotentKey;

    @CreatedDate
    private LocalDateTime createdAt;
}
