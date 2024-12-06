package com.suhanlee.luckybikideffenceapiserver.currency.service;

import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import com.suhanlee.luckybikideffenceapiserver.currency.param.GoldModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRepository;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoldService {

    private final GoldRepository goldRepository;
    private final UserRepository userRepository;

    //gold 조회
    @Transactional(readOnly = true)
    public int getGold(long userId){
        Gold gold = goldRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Gold not found"));

        return gold.getAmount();
    }

    //gold 추가
    @Transactional(rollbackFor = Exception.class)
    public int goldDeposit(GoldModParam goldModParam){
        Users user = userRepository.findById(goldModParam.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                .orElseThrow(() -> new RuntimeException("Gold not found"));

        int resultAmount = gold.getAmount() + goldModParam.getChangeAmount();

        gold.setAmount(resultAmount);

        return resultAmount;
    }

    //gold 인출
    @Transactional(rollbackFor = Exception.class)
    public int goldWithDraw(GoldModParam goldModParam){
        Users user = userRepository.findById(goldModParam.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                .orElseThrow(() -> new RuntimeException("Gold not found"));

        if(gold.getAmount() < goldModParam.getChangeAmount()){
            throw new RuntimeException("Change amount not enough");
        }

        int resultAmount = gold.getAmount() - goldModParam.getChangeAmount();

        gold.setAmount(resultAmount);

        return resultAmount;
    }
}
