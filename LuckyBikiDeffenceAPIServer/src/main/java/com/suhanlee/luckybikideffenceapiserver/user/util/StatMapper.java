package com.suhanlee.luckybikideffenceapiserver.user.util;

import com.suhanlee.luckybikideffenceapiserver.user.model.Stat;
import com.suhanlee.luckybikideffenceapiserver.user.param.StatModParam;
import com.suhanlee.luckybikideffenceapiserver.user.vo.StatVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StatVo updateStatToVo(Stat stat);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStatFromModParam(StatModParam param, @MappingTarget Stat stat);
}
