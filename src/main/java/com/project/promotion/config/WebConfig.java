package com.project.promotion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    /**
     *  카카오 통신을 위한 Bean 등록
     * @return
     */
    @Bean
    public RestTemplate gRestTemplate(){
        return new RestTemplate();
    }
}
