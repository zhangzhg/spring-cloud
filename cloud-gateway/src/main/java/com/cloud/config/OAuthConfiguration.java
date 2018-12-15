package com.cloud.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 注释掉这个类会自动跳转
@Configuration
@EnableOAuth2Sso
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/uaa/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

}
