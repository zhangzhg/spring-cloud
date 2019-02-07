package com.cloud.config;

import com.cloud.security.DomainUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 这个是spring security的类,用来判断用户是否登录
 * The WebSecurityConfigurerAdapter is used to authenticate the user via a session
 * (form login in the case of your given examples).
 */
@Configuration
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DomainUserDetailsService userDetailsService;

    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/**","/login","/login/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login/page")
                .loginProcessingUrl("/login")
                .failureUrl("/login/page?error=true")
                .permitAll()
                .and().logout().permitAll()
                .and().csrf().disable();
    }

    /**
     * 用户验证
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 客户端调用http://localhost:8080/uaa/oauth/token
     * 要client的username=webapp&password=secret才可以
     * 否则报错：401
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(userDetailsService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/js/**","/static/**","/favicon.ico");
    }

    /**
     * 这个是spring security
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
