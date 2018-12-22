package com.cloud.controller;

import com.cloud.feign.UserService;
import com.cloud.service.IHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    IHelloService helloService;
    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    //@PreAuthorize("hasAuthority('hello')")
    public String index(@RequestParam String name) {
        logger.info("request one  name is "+name);
        return helloService.hello(name);
    }

    @RequestMapping("/user")
    public String user() {
        String name = userService.getHello("测试client");
        return name;
    }
}