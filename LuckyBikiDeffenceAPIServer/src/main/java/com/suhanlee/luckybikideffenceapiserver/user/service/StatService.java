package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.user.model.Stat;
import com.suhanlee.luckybikideffenceapiserver.user.param.StatUpdateParam;
import com.suhanlee.luckybikideffenceapiserver.user.repository.StatRepository;
import com.suhanlee.luckybikideffenceapiserver.user.util.StatMapper;
import com.suhanlee.luckybikideffenceapiserver.user.vo.StatVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Transactional(readOnly = true)
    public StatVo getStat(long userId){
        Stat stat = retrieveStat(userId);
        return statMapper.updateStatToVo(stat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStat(StatUpdateParam statUpdateParam){
        Stat stat = retrieveStat(statUpdateParam.getUserId());

        stat.updateLongestPlayTime(statUpdateParam.getUserId());
        stat.updateWaveCount(statUpdateParam.getWaveCount());
        stat.addBossKillCount(statUpdateParam.getBossKillCount());
        stat.addKillCount(statUpdateParam.getKillCount());
        stat.addPlayTime(statUpdateParam.getGameType(), statUpdateParam.getPlayTime());
        stat.addPlayCount(statUpdateParam.getGameType());

        statRepository.save(stat);
    }

    public Stat retrieveStat(long userId){
        return statRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));
    }
}
