package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.suhanlee.luckybikideffenceapiserver.config.error.ErrorCode;
import com.suhanlee.luckybikideffenceapiserver.config.error.exception.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.annotation.REntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

//사용자 로그인 과정에서 사용자 정보를 확인하고 비밀번호를 검증
//비밀번호 인코딩 방식이 업데이트가 필요하다면 비밀번호를 새로 저장하는 기능도 제공
@Slf4j
public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    //비밀번호를 인코딩하거나 검증하는 데 사용, 비밀번호를 비교할 때 passwordEncoder.matches() 메서드를 사용하여 평문 비밀번호와 암호화된 비밀번호를 비교
    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;

    //사용자가 입력한 비밀번호가 저장된 비밀번호와 일치하는지 확인
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if(authentication.getCredentials() == null) {
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }

        String presentedPassword = authentication.getCredentials().toString();

        if(!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }
    }

    //인증 성공 시 처리 및 비밀번호 업데이트를 수행
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        boolean upgradeEncoding = this.userDetailsService != null && this.passwordEncoder.upgradeEncoding(user.getPassword());
        if(upgradeEncoding) {
            String presentedPassword = authentication.getCredentials().toString();
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            //user = this.userDetailsService.updatePassword(user, newPassword);
        }
        return super.createSuccessAuthentication(principal, authentication, user);
    }

    //사용자 정보를 조회
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails loadedUser = null;
        try{
            loadedUser = this.userDetailsService.loadUserByUsername(auth.getPrincipal().toString());
        } catch(UsernameNotFoundException e){

        } catch(Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }

        if(loadedUser == null) {
            throw new RestException(ErrorCode.USER_NOT_FOUND);
        }

        passwordEncoder.matches(authentication.getCredentials().toString(), loadedUser.getPassword());
        return loadedUser;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
