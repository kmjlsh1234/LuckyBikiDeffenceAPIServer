package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.user.model.Profile;
import com.suhanlee.luckybikideffenceapiserver.user.param.ExAddParam;
import com.suhanlee.luckybikideffenceapiserver.user.param.ProfileModParam;
import com.suhanlee.luckybikideffenceapiserver.user.repository.ProfileRepository;
import com.suhanlee.luckybikideffenceapiserver.user.repository.UserRepository;
import com.suhanlee.luckybikideffenceapiserver.user.util.ProfileMapper;
import com.suhanlee.luckybikideffenceapiserver.user.vo.ProfileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    //프로필 조회
    public ProfileVo getProfile(long userId){
        Profile profile = retrieveProfile(userId);
        return profileMapper.updateProfileToVo(profile);
    }

    //프로필 수정
    public ProfileVo modProfile(ProfileModParam profileModParam){

        //닉네임 길이 검사
        if(profileModParam.getNickname().length() < 2 || profileModParam.getNickname().length() > 15){
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        Profile profile = retrieveProfile(profileModParam.getUserId());
        profileMapper.updateProfileFromModParam(profileModParam, profile);
        profile = profileRepository.save(profile);
        return profileMapper.updateProfileToVo(profile);
    }

    public ProfileVo addEx(ExAddParam exAddParam){
        //핵 및 파라미터 체크(너무 많은 경험치를 추가하려는 시도)
        if(exAddParam.getEx() > 100L || exAddParam.getEx() < 0){
            throw new RestException(ErrorCode.GOLD_NOT_FOUND);
        }

        Profile profile = retrieveProfile(exAddParam.getUserId());
        profile.setEx(exAddParam.getEx() + profile.getEx());
        return profileMapper.updateProfileToVo(profile);
    }

    public Profile retrieveProfile(long userId){
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
