package com.cloud.controller;

import com.cloud.service.IRefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于刷新路由
 * http://localhost:8080/refreshRoute
 */

@RestController
public class RefreshRouteController {
    @Autowired
    IRefreshRouteService refreshRouteService;

    @GetMapping("/refreshRoute")
    private void findById() {
        refreshRouteService.refresh();
    }
}
