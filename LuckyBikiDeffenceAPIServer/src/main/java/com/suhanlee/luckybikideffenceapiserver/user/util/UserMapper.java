package com.suhanlee.luckybikideffenceapiserver.user.util;

import com.suhanlee.luckybikideffenceapiserver.user.model.Users;
import com.suhanlee.luckybikideffenceapiserver.user.param.UserModParam;
import com.suhanlee.luckybikideffenceapiserver.user.vo.UserVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserVo updateUsersToVo(Users users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsersFromModParam(UserModParam param, @MappingTarget Users users);
}
