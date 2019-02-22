package com.cloud.config;

import com.cloud.handler.DomainAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 用来验证token
 * that the ResourceServerConfigurerAdapter uses a special filter
 * that checks for the bearer token in the request to authenticate the request via OAuth2.
 */
@Configuration
@EnableResourceServer
@Order(6)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    DomainAccessDeniedHandler domainAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**","/login/**", "/logout")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.accessDeniedHandler(domainAccessDeniedHandler);
    }

}
