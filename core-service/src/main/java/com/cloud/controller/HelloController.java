package com.cloud.controller;

import com.cloud.feign.UserService;
import com.cloud.model.SysUser;
import com.cloud.service.IHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    IHelloService helloService;
    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        logger.info("request one  name is "+name);
        return helloService.hello(name);
    }

    @RequestMapping("/user")
    @ResponseBody
    public List<SysUser> user() {
        return helloService.listUser();
    }

    @RequestMapping("/auth")
    @PreAuthorize("hasAuthority('user')")
    public String auth(@RequestParam String name) {
        logger.info("request one  name is "+name);
        return helloService.hello(name);
    }

}