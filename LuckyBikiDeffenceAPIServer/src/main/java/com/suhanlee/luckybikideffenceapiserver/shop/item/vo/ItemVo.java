package com.suhanlee.luckybikideffenceapiserver.shop.item.vo;

import com.suhanlee.luckybikideffenceapiserver.currency.constants.CurrencyType;
import com.suhanlee.luckybikideffenceapiserver.shop.item.constants.ItemStatus;
import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class ItemVo {
    private Long id;                    // 아이템 고유번호
    private Long categoryId;            // 카테고리 고유번호
    private String name;                // 아이템 이름
    private String description;         // 아이템 설명
    private Integer stockQuantity;      // 아이템 재고(NULL -> 무제한)
    private ItemStatus status;          //아이템 상태(READY, SALE, STOP)
    private String image;               //아이템 이미지 경로
    private CurrencyType currencyType;  //아이템 구입 재화 타입(GOLD, ..)
    private Integer amount;             //아이템 구입 재화 수량
    private LocalDateTime createdAt;    // 생성시각
    private LocalDateTime updatedAt;    // 수정시각
}
