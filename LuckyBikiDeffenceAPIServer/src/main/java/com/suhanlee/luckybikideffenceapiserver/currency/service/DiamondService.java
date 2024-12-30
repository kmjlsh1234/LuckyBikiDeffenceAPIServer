package com.suhanlee.luckybikideffenceapiserver.currency.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.ChangeType;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.DiamondHistoryDesc;
import com.suhanlee.luckybikideffenceapiserver.currency.model.Diamond;
import com.suhanlee.luckybikideffenceapiserver.currency.model.DiamondRecord;
import com.suhanlee.luckybikideffenceapiserver.currency.param.DiamondModParam;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.DiamondRecordRepository;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.DiamondRepository;
import com.suhanlee.luckybikideffenceapiserver.util.RedisLockUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiamondService {

    private final RedisLockUtil redisLockUtil;
    private final DiamondRepository diamondRepository;
    private final DiamondRecordRepository diamondRecordRepository;

    //diamond 조회
    @Transactional(readOnly = true)
    public int getDiamond(long userId){
        Diamond diamond = diamondRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

        return diamond.getAmount();
    }

    //diamond 추가
    @Transactional(rollbackFor = Exception.class)
    public int diamondDeposit(DiamondModParam diamondModParam){
        int totalAmount = addDiamondWithLock(diamondModParam, DiamondHistoryDesc.DEPOSIT_BY_ITEM_BUY);
        return totalAmount;
    }

    //diamond 인출
    @Transactional(rollbackFor = Exception.class)
    public int diamondWithDraw(DiamondModParam diamondModParam){

        int totalAmount = spendDiamondWithLock(diamondModParam, DiamondHistoryDesc.DEPOSIT_BY_ITEM_BUY);
        return totalAmount;
    }

    private int addDiamondWithLock(DiamondModParam diamondModParam, String diamondHistoryDesc){

        //멱등키 중복 체크
        checkDuplicateIdempotentKey(diamondModParam.getIdempotentKey());

        RLock lock = null;
        int totalAmount;
        try{
            lock = redisLockUtil.getLock(diamondModParam.getUserId());

            //유저 골드 가져오고 없으면 생성
            Diamond diamond = diamondRepository.findByUserId(diamondModParam.getUserId())
                    .orElse(Diamond.builder()
                            .userId(diamondModParam.getUserId())
                            .amount(0)
                            .build());

            //골드 추가
            diamond.setAmount(diamond.getAmount() + diamondModParam.getChangeAmount());
            totalAmount = diamond.getAmount();

            //골드 기록 추가
            diamondRecordRepository.save(DiamondRecord.builder()
                    .userId(diamondModParam.getUserId())
                    .changeType(ChangeType.DEPOSIT)
                    .changeDiamond(diamondModParam.getChangeAmount())
                    .resultDiamond(totalAmount)
                    .changeDesc(diamondHistoryDesc)
                    .idempotentKey(diamondModParam.getIdempotentKey())
                    .build());

        } catch(Exception e){
            throw new RestException(ErrorCode.Lock_FAILED);
        } finally{
            redisLockUtil.unLock(lock);
        }
        return totalAmount;
    }

    private int spendDiamondWithLock(DiamondModParam diamondModParam, String diamondHistoryDesc){

        //멱등키 중복 체크
        checkDuplicateIdempotentKey(diamondModParam.getIdempotentKey());

        RLock lock = null;
        int totalAmount;

        try{
            lock = redisLockUtil.getLock(diamondModParam.getUserId());

            //유저 골드 가져오고 없으면 생성
            Diamond diamond = diamondRepository.findByUserId(diamondModParam.getUserId())
                    .orElseThrow(() -> new RestException(ErrorCode.GOLD_NOT_FOUND));

            //골드 개수 체크
            if(diamond.getAmount() < diamondModParam.getChangeAmount()){
                throw new RestException(ErrorCode.CHANGE_AMOUNT_NOT_ENOUGH);
            }

            diamond.setAmount(diamond.getAmount() - diamondModParam.getChangeAmount());
            totalAmount = diamond.getAmount();

            //골드 기록 추가
            diamondRecordRepository.save(DiamondRecord.builder()
                    .userId(diamondModParam.getUserId())
                    .changeType(ChangeType.WITHDRAW)
                    .changeDiamond(diamondModParam.getChangeAmount())
                    .resultDiamond(totalAmount)
                    .changeDesc(diamondHistoryDesc)
                    .idempotentKey(diamondModParam.getIdempotentKey())
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
        diamondRecordRepository.findDiamondRecordByIdempotentKey(idempotentKey).ifPresent(record -> {
            throw new RestException(ErrorCode.DUPLICATE_GOLD_IDEMPOTENT_KEY);
        });
    }
}
