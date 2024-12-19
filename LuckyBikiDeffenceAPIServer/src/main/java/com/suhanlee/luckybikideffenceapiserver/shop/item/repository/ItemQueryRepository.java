package com.suhanlee.luckybikideffenceapiserver.shop.item.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.suhanlee.luckybikideffenceapiserver.shop.item.constants.ItemStatus;
import com.suhanlee.luckybikideffenceapiserver.shop.item.vo.ItemVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.suhanlee.luckybikideffenceapiserver.shop.item.model.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ItemVo> getItemList(){
        return jpaQueryFactory.select(Projections.fields(ItemVo.class,
                item.id,
                item.categoryId,
                item.name,
                item.description,
                item.stockQuantity,
                item.status,
                item.image,
                item.currencyType,
                item.amount,
                item.createdAt,
                item.updatedAt))
                .from(item)
                .where(item.status.eq(ItemStatus.SALE))
                .orderBy(item.id.desc())
                .fetch();

    }
}
