package com.suhanlee.luckybikideffenceapiserver.config.security;

import com.suhanlee.luckybikideffenceapiserver.user.service.JwtAuthenticationService;
import com.suhanlee.luckybikideffenceapiserver.user.service.UserLoginAfterService;
import com.suhanlee.luckybikideffenceapiserver.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    private final UserDetailsService userDetailsService;
    private final UserLoginAfterService userLoginAfterService;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final WebUtil webUtil;

    public SecurityConfiguration(JwtAuthenticationService jwtAuthenticationService, WebUtil webUtil, UserDetailsService userDetailsService, UserLoginAfterService userLoginAfterService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.webUtil = webUtil;
        this.userDetailsService = userDetailsService;
        this.userLoginAfterService = userLoginAfterService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        http.authenticationManager(authenticationManagerBuilder.eraseCredentials(true).build());
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());

        http
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(Configurer -> Configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JwtAuthenticationFilter((authenticationManagerBuilder.getObject()), userLoginAfterService, jwtAuthenticationService,  webUtil))
                .addFilter(new JwtAuthorizationFilter((authenticationManagerBuilder.getObject()), secret, jwtAuthenticationService));
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(
                        "/","/test/**","/auth/login", "/api/v1/user/join"
                ).permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated())
                .exceptionHandling((exception) -> {
                    exception.accessDeniedHandler(accessDeniedHandler());
                });
        return http.build();
    }

    @Bean
    CustomUserDetailsAuthenticationProvider authenticationProvider() {
        CustomUserDetailsAuthenticationProvider customUserDetailsAuthenticationProvider = new CustomUserDetailsAuthenticationProvider();
        customUserDetailsAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        customUserDetailsAuthenticationProvider.setUserDetailsService(userDetailsService);
        return customUserDetailsAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
