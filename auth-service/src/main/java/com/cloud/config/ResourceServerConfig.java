package com.cloud.config;

import com.cloud.handler.DomainAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 这个是oauth2的配置，会在security之后
 * 优先级高的http配置是可以覆盖优先级低的配置的。所以会覆盖SecurityConfig配置
 * 配置了这里token就不会验证了。如果不想要token验证一定要在这里才有效果
 */
@Configuration
@EnableResourceServer
//@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    DomainAccessDeniedHandler domainAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/resources")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.accessDeniedHandler(domainAccessDeniedHandler);
    }

}
