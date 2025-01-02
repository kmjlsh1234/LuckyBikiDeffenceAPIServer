package com.suhanlee.luckybikideffenceapiserver.currency.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.currency.constants.ChargeType;
import com.suhanlee.luckybikideffenceapiserver.currency.model.Energy;
import com.suhanlee.luckybikideffenceapiserver.currency.param.EnergyAddParam;
import com.suhanlee.luckybikideffenceapiserver.currency.param.EnergyUseParam;
import com.suhanlee.luckybikideffenceapiserver.currency.repository.EnergyRepository;
import com.suhanlee.luckybikideffenceapiserver.currency.vo.EnergyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EnergyService {

    private final EnergyRepository energyRepository;
    public static final int MAX_ENERGY_AMOUNT = 200;
    public static final int CHARGE_TIMER_SEC = 480; //8분

    //에너지 조회
    @Transactional(readOnly = true)
    public EnergyVo getEnergy(long userId){
        Energy energy = retrieveEnergy(userId);
        Integer timer = null;
        //행동력이 가득 찼을 때
        if(energy.getAmount() >= MAX_ENERGY_AMOUNT){
            energy.updateChargeDate(LocalDateTime.now());
        }
        //행동력이 여유 있을 때
        else{
            Duration duration = Duration.between(energy.getLastChargeAt(), LocalDateTime.now());
            long minutesDifference = duration.toMinutes(); //마지막 업데이트 시간과 현재 시간 차이
            int addAmount = (int) (minutesDifference / 8);
            timer = CHARGE_TIMER_SEC - ((int)(minutesDifference % 8)) * 60;
            energy.addAmount(addAmount);
            energy.updateChargeDate(LocalDateTime.now());
        }

        energyRepository.save(energy);
        return EnergyVo.builder()
                .amount(energy.getAmount())
                .timer(timer)
                .build();
    }

    //에너지 추가
    @Transactional(rollbackFor = RestException.class)
    public int addEnergy(EnergyAddParam energyAddParam){
        Energy energy = retrieveEnergy(energyAddParam.getUserId());
        energy.addAmount(energyAddParam.getAmount());
        if(energyAddParam.getChargeType() == ChargeType.CHARGE){
            energy.setLastChargeAt(LocalDateTime.now());
        }
        energyRepository.save(energy);
        return energy.getAmount();
    }

    //에너지 소모
    @Transactional(rollbackFor = RestException.class)
    public int useEnergy(EnergyUseParam energyUseParam){
        Energy energy = retrieveEnergy(energyUseParam.getUserId());
        if(energy.getAmount() < energyUseParam.getAmount()){
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }
        energy.useAmount(energyUseParam.getAmount());
        energyRepository.save(energy);
        return energy.getAmount();
    }

    private Energy retrieveEnergy(long userId){
        return energyRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));
    }
}
