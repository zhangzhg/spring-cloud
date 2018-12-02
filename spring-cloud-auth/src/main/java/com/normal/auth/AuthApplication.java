package com.normal.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, PreserveHostHeaderGatewayFilterFactory factory) {
//		//默认就会调用preserveHostHeader，状态PRESERVE_HOST_HEADER_ATTRIBUTE为false不会保存状态。
//		return builder.routes()
//				.route("path_route", r -> r.path("/get")
//						.uri("http://httpbin.org"))
//				.route("path_route", r -> r.path("/baidu")
//						.uri("http://www.baidu.com/"))
//				.build();
//	}

	@Component
	@EnableOAuth2Sso // 实现基于OAuth2的单点登录，建议跟踪进代码阅读以下该注解的注释，很有用
	public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.
					antMatcher("/**")
					// 所有请求都得经过认证和授权
					.authorizeRequests().anyRequest().authenticated()
					.and().authorizeRequests().antMatchers("/","/anon").permitAll()
					.and()
					// 这里之所以要禁用csrf，是为了方便。
					// 否则，退出链接必须要发送一个post请求，请求还得带csrf token
					// 那样我还得写一个界面，发送post请求
					.csrf().disable()
					// 退出的URL是/logout
					.logout().logoutUrl("/logout").permitAll()
					// 退出成功后，跳转到/路径。
					.logoutSuccessUrl("/login");
		}
	}
}
