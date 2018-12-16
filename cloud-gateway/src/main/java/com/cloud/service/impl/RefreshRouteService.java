package com.cloud.service.impl;

import com.cloud.service.IRefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 刷新路由方法
 * 页面调用： refreshRoute/refresh
 */
@Service
public class RefreshRouteService implements IRefreshRouteService {
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    RouteLocator routeLocator;

    public void refresh() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);

    }
}
