package com.suhanlee.luckybikideffenceapiserver.user.util;

import com.suhanlee.luckybikideffenceapiserver.user.model.Stat;
import com.suhanlee.luckybikideffenceapiserver.user.vo.StatVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StatMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StatVo updateStatToVo(Stat stat);
}
