package com.cloud.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 1、gateway 是用来做请求转发，当我们参数后面加access_token 的时候要disable掉csrd否则会被拦截
     * 导致的结果是这里过不去，会被重定向到/login
     * 2、这个文件配置的作用是将gateway作为oauth2的client端。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 注释掉这个类会自动跳转
        http.csrf().disable();
    }

}
