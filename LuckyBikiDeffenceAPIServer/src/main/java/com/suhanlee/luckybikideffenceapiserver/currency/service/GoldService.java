package com.suhanlee.luckybikideffenceapiserver.currency.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
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
                .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

        return gold.getAmount();
    }

    //gold 추가
    @Transactional(rollbackFor = Exception.class)
    public int goldDeposit(GoldModParam goldModParam){
        Users user = userRepository.findById(goldModParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND);

        Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

        int resultAmount = gold.getAmount() + goldModParam.getChangeAmount();

        gold.setAmount(resultAmount);

        return resultAmount;
    }

    //gold 인출
    @Transactional(rollbackFor = Exception.class)
    public int goldWithDraw(GoldModParam goldModParam){
        Users user = userRepository.findById(goldModParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));

        Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

        if(gold.getAmount() < goldModParam.getChangeAmount()){
            throw new RestException(ErrorCode.CHANGE_AMOUNT_NOT_ENOUGH);
        }

        int resultAmount = gold.getAmount() - goldModParam.getChangeAmount();

        gold.setAmount(resultAmount);

        return resultAmount;
    }
}
