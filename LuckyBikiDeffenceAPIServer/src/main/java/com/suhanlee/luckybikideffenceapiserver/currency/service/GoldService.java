package com.suhanlee.luckybikideffenceapiserver.currency.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.ChangeType;
import com.suhanlee.luckybikideffenceapiserver.currency.model.Gold;
import com.suhanlee.luckybikideffenceapiserver.currency.model.GoldRecord;
import com.suhanlee.luckybikideffenceapiserver.currency.param.GoldModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRecordRepository;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.GoldRepository;
import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import com.suhanlee.luckybikideffenceapiserver.util.RedisLockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoldService {

    private final GoldRepository goldRepository;
    private final GoldRecordRepository goldRecordRepository;
    private final RedisLockUtil redisLockUtil;

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
        int totalAmount = addGoldWithLock(goldModParam);
        return totalAmount;
    }

    //gold 인출
    @Transactional(rollbackFor = Exception.class)
    public int goldWithDraw(GoldModParam goldModParam){

        int totalAmount = spendGoldWithLock(goldModParam);
        return totalAmount;
    }

    private int addGoldWithLock(GoldModParam goldModParam){

        //멱등키 중복 체크
        checkDuplicateIdempotentKey(goldModParam.getIdempotentKey());

        RLock lock = null;
        int totalAmount;
        try{
            lock = redisLockUtil.getLock(goldModParam.getUserId());

            //유저 골드 가져오고 없으면 생성
            Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                    .orElse(Gold.builder()
                            .userId(goldModParam.getUserId())
                            .amount(0)
                            .build());

            //골드 추가
            gold.setAmount(gold.getAmount() + goldModParam.getChangeAmount());
            totalAmount = gold.getAmount();

            //골드 기록 추가
            goldRecordRepository.save(GoldRecord.builder()
                            .userId(goldModParam.getUserId())
                            .changeType(ChangeType.DEPOSIT)
                            .changeGold(goldModParam.getChangeAmount())
                            .resultGold(totalAmount)
                            .changeDesc(goldModParam.getGoldHistoryDesc().getDesc())
                            .idempotentKey(goldModParam.getIdempotentKey())
                            .build());

        } catch(Exception e){
            throw new RestException(ErrorCode.Lock_FAILED);
        } finally{
            redisLockUtil.unLock(lock);
        }
        return totalAmount;
    }

    private int spendGoldWithLock(GoldModParam goldModParam){

        //멱등키 중복 체크
        checkDuplicateIdempotentKey(goldModParam.getIdempotentKey());

        RLock lock = null;
        int totalAmount;

        try{
            lock = redisLockUtil.getLock(goldModParam.getUserId());

            //유저 골드 가져오고 없으면 생성
            Gold gold = goldRepository.findByUserId(goldModParam.getUserId())
                    .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

            //골드 개수 체크
            if(gold.getAmount() < goldModParam.getChangeAmount()){
                throw new RestException(ErrorCode.CHANGE_AMOUNT_NOT_ENOUGH);
            }

            gold.setAmount(gold.getAmount() - goldModParam.getChangeAmount());
            totalAmount = gold.getAmount();

            //골드 기록 추가
            goldRecordRepository.save(GoldRecord.builder()
                    .userId(goldModParam.getUserId())
                    .changeType(ChangeType.WITHDRAW)
                    .changeGold(goldModParam.getChangeAmount())
                    .resultGold(totalAmount)
                    .changeDesc(goldModParam.getGoldHistoryDesc().toString())
                    .idempotentKey(goldModParam.getIdempotentKey())
                    .build());

        } catch(Exception e){
            throw new RestException(ErrorCode.Lock_FAILED);
        } finally{
            redisLockUtil.unLock(lock);
        }
        return totalAmount;
    }

    //멱등키 검사
    public void checkDuplicateIdempotentKey(String idempotentKey){
        goldRecordRepository.findGoldRecordByIdempotentKey(idempotentKey).ifPresent(record -> {
            throw new RestException(ErrorCode.DUPLICATE_GOLD_IDEMPOTENT_KEY);
        });
    }
}
