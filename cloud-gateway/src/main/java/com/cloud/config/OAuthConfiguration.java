package com.cloud.config;

import com.cloud.security.CustomAccessDecisionManager;
import com.cloud.security.CustomMetadataSourceService;
import com.cloud.security.CustomSecurityInterceptor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableOAuth2Sso
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 1、gateway 是用来做请求转发，当我们参数后面加access_token 的时候要disable掉csrd否则会被拦截
     * 导致的结果是这里过不去，会被重定向到/login
     * 2、这个文件配置的作用是将gateway作为oauth2的client端。
     * 3、此处作为security资源权限拦截（例如菜单）
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 注释掉这个类会自动跳转
        http.
                csrf()
                .disable();
//                .authorizeRequests()
//                .antMatchers("/uaa")
//                .permitAll()
//                .antMatchers("/")
//                .authenticated()
//                .and()
//                .addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class);;

 //       String[] paths = new String[]{"/"};
          //白名单
//        if (propertySource.getProperty("security.ignoring") != null) {
//            paths = propertySource.getProperty("security.ignoring").toString().split(",");
//            paths = StringUtil.clearSpace(paths);
//        }
    }


    /**
     * 过滤器
     * @return
     */
//    @Bean
//    public CustomSecurityInterceptor getCustomSecurityInterceptor() {
//        CustomSecurityInterceptor interceptor = new CustomSecurityInterceptor();
//        interceptor.setAccessDecisionManager(getCustomAccessDecisionManager());
//        interceptor.setSecurityMetadataSource(getCustomMetadataSourceService());
//        try {
//            interceptor.setAuthenticationManager(this.authenticationManagerBean());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return interceptor;
//    }
//
//    @Bean
//    public CustomAccessDecisionManager getCustomAccessDecisionManager() {
//        return new CustomAccessDecisionManager();
//    }
//
//    @Bean
//    public CustomMetadataSourceService getCustomMetadataSourceService() {
//        return new CustomMetadataSourceService();
//    }

}
