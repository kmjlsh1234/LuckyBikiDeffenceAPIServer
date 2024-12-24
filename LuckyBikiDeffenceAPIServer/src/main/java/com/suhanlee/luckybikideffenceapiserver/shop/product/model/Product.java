package com.suhanlee.luckybikideffenceapiserver.shop.product.model;

import com.suhanlee.luckybikideffenceapiserver.shop.product.constants.ProductStatus;
import com.suhanlee.luckybikideffenceapiserver.shop.product.constants.ProductType;
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
@Table(name = "product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                    //상품 고유 번호
    private String name;                //상품 이름
    private String description;         //상품 설명
    private ProductStatus status;       //상품 상태
    private ProductType type;           //상품 타입(CURRENCY, ITEM, NON-CONSUMABLE, SUBSCRIPTION)
    private int quantity;               //상품 개수
    private int price;                  //상품 가격
    @CreatedDate
    private LocalDateTime createdAt;    //생성 시각
    @LastModifiedDate
    private LocalDateTime updatedAt;    //변경 시각
}
