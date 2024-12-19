package com.suhanlee.luckybikideffenceapiserver.shop.item.controller;

import com.suhanlee.luckybikideffenceapiserver.config.security.UserPrincipal;
import com.suhanlee.luckybikideffenceapiserver.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {
    private ItemService itemService;

    //아이템 상세 조회

    //아이템 리스트 조회
    @GetMapping("/v1/items")
    public ResponseEntity<?> getItemList() {
        return ResponseEntity.ok(itemService.getItemList());
    }

    //아이템 구매
    @PostMapping("/v1/items/{itemId}")
    public ResponseEntity<?> buyItem(@PathVariable("itemId") long itemId, @AuthenticationPrincipal UserPrincipal userPrincipal){
        long userId = userPrincipal.getUserId();
        itemService.buyItem(userId, itemId);
        return ResponseEntity.ok().build();
    }

    //아이템 추가(admin으로 이전 예정)

}
