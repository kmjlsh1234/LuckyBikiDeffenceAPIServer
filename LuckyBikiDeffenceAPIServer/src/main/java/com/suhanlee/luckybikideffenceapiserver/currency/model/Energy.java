package com.suhanlee.luckybikideffenceapiserver.currency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.suhanlee.luckybikideffenceapiserver.currency.service.EnergyService.MAX_ENERGY_AMOUNT;

@Entity
@Table(name = "energy")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Energy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "last_charge_at")
    private LocalDateTime lastChargeAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateChargeDate(LocalDateTime date) {
        this.lastChargeAt = date;
    }

    public void addAmount(Integer amount) {
        this.amount += amount;
    }

    public void useAmount(Integer amount) {
        this.amount -= amount;
    }
}
