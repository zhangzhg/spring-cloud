package com.cloud.config;

import com.cloud.domain.SysZuulRoute;
import com.cloud.repository.SysZuulRouteRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.*;

/**
 * 加载动态路由配置
 * 存储在数据库中
 */
public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
    private static Logger log = LoggerFactory.getLogger(DynamicRouteLocator.class);
    private ZuulProperties properties;
    private SysZuulRouteRepository sysZuulRouteRepository;

    public DynamicRouteLocator(String servletPath, ZuulProperties properties, SysZuulRouteRepository zuulRepository) {
        super(servletPath, properties);
        this.properties = properties;
        this.sysZuulRouteRepository = zuulRepository;
    }

    /**
     * 重写路由配置
     * <p>
     * 1. properties 配置。
     * 2. eureka 默认配置。
     * 3. DB数据库配置。
     *
     * @return 路由表
     */
    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //读取properties配置、eureka默认配置
        routesMap.putAll(super.locateRoutes());
        log.debug("初始默认的路由配置完成");
        routesMap.putAll(locateRoutesFromDb());
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.isNotBlank(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDb() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        List<SysZuulRoute> results = sysZuulRouteRepository.findAll();
        if (results.size() == 0) {
            return routes;
        }

        for (SysZuulRoute result : results) {
            if (StringUtils.isBlank(result.getPath()) && StringUtils.isBlank(result.getUrl())) {
                continue;
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                zuulRoute.setId(result.getServiceId());
                zuulRoute.setPath(result.getPath());
                zuulRoute.setServiceId(result.getServiceId());
                zuulRoute.setRetryable(StringUtils.equals(result.getRetryable(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setStripPrefix(StringUtils.equals(result.getStripPrefix(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setUrl(result.getUrl());
                String[] sensitiveHeadersList = StringUtils.split(result.getSensitiveheadersList(), ",");
                if (sensitiveHeadersList != null) {
                    HashSet<String> sensitiveHeaderSet = new HashSet<>();
                    Collections.addAll(sensitiveHeaderSet, sensitiveHeadersList);
                    zuulRoute.setSensitiveHeaders(sensitiveHeaderSet);
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
            } catch (Exception e) {
                log.error("从数据库加载路由配置异常", e);
            }
            log.debug("添加数据库自定义的路由配置,path：{}，serviceId:{}", zuulRoute.getPath(), zuulRoute.getServiceId());
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

    @Override
    public void refresh() {
        //doRefresh();
    }
}