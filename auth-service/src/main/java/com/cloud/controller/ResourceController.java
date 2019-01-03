package com.cloud.controller;

import com.cloud.service.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
public class ResourceController {
    @Autowired
    IUserDetailService userDetailsService;

    @GetMapping("/resources")
    public Map<String, Collection<ConfigAttribute>> resources(){
        return userDetailsService.loadAllResource();
    }
}
