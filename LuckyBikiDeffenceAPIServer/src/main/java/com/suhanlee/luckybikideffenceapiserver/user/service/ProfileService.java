package com.suhanlee.luckybikideffenceapiserver.user.service;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import com.suhanlee.luckybikideffenceapiserver.user.model.Profile;
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
    private final UserRepository userRepository;

    private final ProfileMapper profileMapper;

    //프로필 조회
    public Profile getProfile(long userId){
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.USER_NOT_FOUND));

        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.PROFILE_NOT_FOUND));
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

    public Profile retrieveProfile(long userId){
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RestException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
