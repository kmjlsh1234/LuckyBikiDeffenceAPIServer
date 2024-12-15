package com.suhanlee.luckybikideffenceapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LuckyBikiDeffenceApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyBikiDeffenceApiServerApplication.class, args);
    }

}
