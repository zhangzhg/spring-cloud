package com.cloud.config;

import com.cloud.repository.SysZuulRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 */
@Configuration
public class ZuulCustomConfiguration {
    @Autowired
    private SysZuulRouteRepository sysZuulRouteRepository;

    @Bean
    public RouteLocator dynamicRouteLocator(ServerProperties server, ZuulProperties properties) {
        return new DynamicRouteLocator(server.getServlet().getPath(), properties, sysZuulRouteRepository);
    }
}