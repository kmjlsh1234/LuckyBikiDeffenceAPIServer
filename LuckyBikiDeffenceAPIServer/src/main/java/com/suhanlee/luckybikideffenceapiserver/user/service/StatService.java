package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.user.model.Stat;
import com.suhanlee.luckybikideffenceapiserver.user.repository.StatRepository;
import com.suhanlee.luckybikideffenceapiserver.user.util.StatMapper;
import com.suhanlee.luckybikideffenceapiserver.user.vo.StatVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;
    private final StatMapper statMapper;

    public StatVo getStat(long userId){
        Stat stat = retrieveStat(userId);
        return statMapper.updateStatToVo(stat);
    }

    public Stat retrieveStat(long userId){
        return statRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));
    }
}
