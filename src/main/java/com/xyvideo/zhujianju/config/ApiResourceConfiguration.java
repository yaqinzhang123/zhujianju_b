package com.xyvideo.zhujianju.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

//@Configuration
public class ApiResourceConfiguration  {


   // @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers()
                .antMatchers("/demo")
                .and()
                .authorizeRequests()
                .antMatchers("/demo")
                .authenticated();
    }
}
