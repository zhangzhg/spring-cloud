package com.normal.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@RestController
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
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

}
