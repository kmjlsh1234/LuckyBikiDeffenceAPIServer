package com.suhanlee.luckybikideffenceapiserver.shop.item.repository;

import com.suhanlee.luckybikideffenceapiserver.shop.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
