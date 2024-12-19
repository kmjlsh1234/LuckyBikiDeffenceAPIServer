package com.suhanlee.luckybikideffenceapiserver.shop.item.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.GoldHistoryDesc;
import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import com.suhanlee.luckybikideffenceapiserver.currency.param.GoldModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRepository;
import com.suhanlee.luckybikideffenceapiserver.currency.service.GoldService;
import com.suhanlee.luckybikideffenceapiserver.shop.item.model.Item;
import com.suhanlee.luckybikideffenceapiserver.shop.item.repository.ItemQueryRepository;
import com.suhanlee.luckybikideffenceapiserver.shop.item.repository.ItemRepository;
import com.suhanlee.luckybikideffenceapiserver.shop.item.vo.ItemVo;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import com.suhanlee.luckybikideffenceapiserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemQueryRepository itemQueryRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final GoldRepository goldRepository;
    private final GoldService goldService;

    //아이템 상세 조회

    //아이템 리스트 조회
    public List<ItemVo> getItemList(){
        return itemQueryRepository.getItemList();
    }

    //아이템 구매
    public void BuyItem(long userId, long itemId){
        //유저 존재하는지 체크 & 유저 정보 가져오기
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));

        //아이템 존재하는지 체크 & 아이템 정보 가져오기
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));

        //골드 정보 존재하는지 체크 & 골드 정보 가져오기
        Gold gold = goldRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

        //아이템 재고 체크
        if(item.getStockQuantity() != null && item.getStockQuantity() < 1){
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        //구매 타입에 따른 구매 금액 체크
        switch(item.getCurrencyType()){
            case GOLD -> {
                if(item.getAmount() > gold.getAmount()){
                    throw new RestException(ErrorCode.USER_NOT_FOUND);
                }
            }
            default -> throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        //유저 재화 사용 처리

        // 멱등키 생성
        String idempotentKey = UUID.randomUUID().toString();

        GoldModParam goldModParam = GoldModParam.builder()
                .userId(userId)
                .changeAmount(item.getAmount())
                .idempotentKey(idempotentKey)
                .build();
        goldService.goldWithDraw(goldModParam);

        //아이템 지급 처리
        if(item.getStockQuantity() != null){
            item.setStockQuantity(item.getStockQuantity() - 1);
            itemRepository.save(item);
        }

        //인벤토리에 아이템 넣기


        //
    }

    //아이템 추가(admin으로 이전 예정)
}
